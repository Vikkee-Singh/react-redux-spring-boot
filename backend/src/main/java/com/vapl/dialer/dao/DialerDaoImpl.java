package com.vapl.dialer.dao;

import java.io.IOException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.GenericStoredProcedure;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.vapl.dialer.exception.UserNotFoundException;
import com.vapl.dialer.mapper.AgentDetailsMapper;
import com.vapl.dialer.mapper.AgentLeadsMapper;
import com.vapl.dialer.mapper.DIDMapper;
import com.vapl.dialer.mapper.DID_WidgetMapper;
import com.vapl.dialer.mapper.DashboardDetailsMapper;
import com.vapl.dialer.mapper.DomainDetailsMapper;
import com.vapl.dialer.mapper.FollowUpDetailsMapper;
import com.vapl.dialer.mapper.HistoryDetailsMapper;
import com.vapl.dialer.mapper.ReportDetailsMapper;
import com.vapl.dialer.mapper.UserDetailsMapper;
import com.vapl.dialer.mapper.UserInfoMapper;
import com.vapl.dialer.mapper.UserMapper;
import com.vapl.dialer.mapper.UserProfileMapper;
import com.vapl.dialer.mapper.dncMapper;
import com.vapl.dialer.mapper.packsMapper;
import com.vapl.dialer.mapper.tasksMapper;
import com.vapl.dialer.pojo.AgentLeads;
import com.vapl.dialer.pojo.Agents;
import com.vapl.dialer.pojo.Base;
import com.vapl.dialer.pojo.ClientHistory;
import com.vapl.dialer.pojo.DID;
import com.vapl.dialer.pojo.DNC;
import com.vapl.dialer.pojo.Dashboard;
import com.vapl.dialer.pojo.FollowUp;
import com.vapl.dialer.pojo.Packs;
import com.vapl.dialer.pojo.Reports;
import com.vapl.dialer.pojo.Tasks;
import com.vapl.dialer.pojo.UserDetails;
import com.vapl.dialer.pojo.UserInfo;
import com.vapl.dialer.util.Util;

@Service
public class DialerDaoImpl implements DialerDao {

	private static final Logger logger = LoggerFactory.getLogger(DialerDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	private JavaMailSender javaMailSender;

	/*********************************
	 * User Management
	 **********************************/

	@Override
	public String register(UserInfo user) {

		String retString = "Cannot Create User!! Please try again";
		String procName = "proc_createUser";
		String resp = "";
		StoredProcedure procedure = new GenericStoredProcedure();
		procedure.setJdbcTemplate(jdbcTemplateObject);
		procedure.setSql(procName);
		procedure.setFunction(false);
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("in_username", user.getUsername());
		inParamMap.put("in_password", bcryptEncoder.encode(user.getPassword()));
		inParamMap.put("in_name", user.getName());
		inParamMap.put("in_number", user.getNumber());
		inParamMap.put("in_parent", user.getParent());

		SqlParameter[] parameters = { new SqlParameter("in_username", Types.VARCHAR),
				new SqlParameter("in_password", Types.VARCHAR), new SqlParameter("in_name", Types.VARCHAR),
				new SqlParameter("in_number", Types.VARCHAR), new SqlParameter("in_parent", Types.INTEGER),
				new SqlOutParameter("out_str", Types.VARCHAR) };

		procedure.setParameters(parameters);
		procedure.compile();

		Map<String, Object> result = procedure.execute(inParamMap);

		retString = result.get("out_str").toString();
		logger.info(retString);
		if (retString.equals("User Created Successfully")) {
			String selectCount = "select max(user_id) from users";
			String userId = jdbcTemplateObject.queryForObject(selectCount, Long.class).toString();

			if (userId != null) {
				resp = "{\"userId\":\"" + userId + "\",\"message\":\"" + retString + "\"}";
			} else {
				resp = "{\"message\":\"" + retString + "\"}";
			}
		} else {
			resp = "{\"message\":\"" + retString + "\"}";
		}
		return retString;
	}

	public UserInfo getUserInfo(String email) {

		String query = "select count(*) from users where username='" + email + "';";
		int count = jdbcTemplateObject.queryForObject(query, Integer.class);
		System.out.println("count +++++ " + count);
		if (count == 0) {
			throw new UserNotFoundException("Bad credentials");
		} else {
			String SQL = "SELECT u.username NAME, u.password pass, u.user_id userid, a.role role from "
					+ "users u INNER JOIN user_roles a on u.user_id=a.user_id WHERE " + "u.enabled =1 and u.username = '" + email
					+ "'";

			UserInfo user = jdbcTemplateObject.queryForObject(SQL, new Object[] {}, new UserMapper());
			return user;
		}
	}

	@Override
	public List<UserDetails> userList(String userId) {

		String query = "select user_id,username,name,mobile,email,address,pincode,company_name,credits_available,credits_used,type,parent_company from user_details where parent ="
				+ userId + ";";

		List<UserDetails> users = jdbcTemplateObject.query(query, new UserDetailsMapper());
		return users;
	}

	@Override
	public UserInfo userProfile(String userId) {
		logger.info("VirtualDaoImpl: Inside userProfile");
		String query = "select user_id,username,name,mobile,email,address,pincode,company_name,credits_available,credits_used,type,parent_company from user_details where user_id ="
				+ userId + ";";
		logger.info("query is=" + query);
		UserInfo user = jdbcTemplateObject.queryForObject(query, new UserInfoMapper());
		return user;
	}

	@Override
	public Dashboard dashboardDetails(String userId) {
		logger.info("VirtualDaoImpl: Inside dashboardDetails");
		String SQL = "call proc_dashboard(" + userId + ");";
		logger.info("query is=" + SQL);
		Dashboard db = jdbcTemplateObject.queryForObject(SQL, new DashboardDetailsMapper());
		return db;
	}

	@Override
	public String deleteUser(UserInfo user, String userId) {
		logger.info("VirtualDaoImpl: Inside deleteUser");
		String retString = "Request Failed!! Please try again";
		String resp = "";
		String procName = "proc_deleteUser";
		StoredProcedure procedure = new GenericStoredProcedure();
		procedure.setJdbcTemplate(jdbcTemplateObject);
		procedure.setSql(procName);
		procedure.setFunction(false);
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("in_userid", userId);
		inParamMap.put("in_parent", user.getParent());

		SqlParameter[] parameters = { new SqlParameter("in_userid", Types.BIGINT),
				new SqlParameter("in_parent", Types.BIGINT), new SqlOutParameter("out_code", Types.INTEGER),
				new SqlOutParameter("out_str", Types.VARCHAR) };

		procedure.setParameters(parameters);
		procedure.compile();

		Map<String, Object> result = procedure.execute(inParamMap);

		retString = result.get("out_str").toString();
		logger.info(retString);

		resp = "{\"message\":\"" + retString + "\"}";
		return resp;
	}

	@Override
	public String updateProfile(UserInfo user) {
		logger.info("VirtualDaoImpl: Inside updateProfile");
		String retString = "Request Failed!! Please try again";
		String resp = "";
		String dateStr = Util.getDate();
		String query = "select count(1) from user_details where user_id = " + user.getUserId() + ";";
		int selectCount = jdbcTemplateObject.queryForObject(query, Integer.class);

		if (selectCount > 0) {
			String updateQuery = "update user_details set name=?, mobile=?, email=?, update_date=? where user_id=?;";
			Object[] params = { user.getName(), user.getNumber(), user.getUsername(), dateStr, user.getUserId() };

			int rows = jdbcTemplateObject.update(updateQuery, params);
			logger.info(rows + " row(s) updated.");

			if (rows > 0) {
				retString = "Profile Updated Successfully";
			}
			logger.info(retString);

			resp = "{\"message\":\"" + retString + "\"}";

		}
		return resp;
	}

	@Override
	public String addCredits(UserInfo user) {
		logger.info("VirtualDaoImpl: Inside addCredits");
		String retString = "Request Failed!! Please try again";
		String resp = "";
		String query = "select credits_available from user_details where user_id = " + user.getParent() + ";";
		float credits = jdbcTemplateObject.queryForObject(query, Float.class);

		// if (credits > user.getCredits()) {
		// String updateQuery = "update user_details set credits_available =
		// credits_available - " + user.getCredits()
		// + " where user_id=?;";
		// Object[] params = { user.getParent() };
		//
		// int rows = jdbcTemplateObject.update(updateQuery, params);
		// logger.info(rows + " row(s) updated.");
		//
		// updateQuery = "update user_details set credits_available = credits_available
		// + " + user.getCredits()
		// + " where user_id=? and parent=?;";
		// Object[] params1 = { user.getUserId(), user.getParent() };
		//
		// int rows1 = jdbcTemplateObject.update(updateQuery, params1);
		// logger.info(rows1 + " row(s) updated.");
		//
		// if (rows > 0 && rows1 > 0) {
		// retString = "Credits Added Successfully";
		// }
		// logger.info(retString);
		// } else {
		// retString = "Enough Credits Not Available!! Please Recharge";
		// }

		resp = "{\"message\":\"" + retString + "\"}";
		return resp;
	}

	/*
	 * @Override public List<Reports> getReports(String campaignId) {
	 * logger.info("VirtualDaoImpl: Inside getReports"); String
	 * query="select call_sid,cli,bni,answer_status,start_time,answer_time,end_time,billing_duration,disposition,url_status from reports where camp_id ="
	 * +campaignId+";"; logger.info("query is="+query); List<Reports> reports=
	 * jdbcTemplateObject.query(query, new ReportDetailsMapper()); return reports; }
	 */

	public List<UserInfo> userDetails(String userId) {
		String SQL = "select user_id,username,name,mobile,email,address,pincode,company_name,credits_available,credits_used,type,parent_company from user_details";
		System.out.println("query is=" + SQL);
		List<UserInfo> user = null;// jdbcTemplateObject.query(SQL, new UserDetailsMapper());

		return user;
	}

	public List<UserInfo> userProfile(HttpSession session) {
		String SQL = "select user_id,username,name,mobile,email,address,company_name from user_details where user_id="
				+ session.getAttribute("user");
		System.out.println("query is=" + SQL);
		List<UserInfo> user = jdbcTemplateObject.query(SQL, new UserProfileMapper());

		return user;
	}

	@Override
	public String editUser(UserInfo user) {
		String retString = "Request Failed!! Please try again";
		String resp = "";
		String selectCount = "select count(1) from user_details where user_id=" + user.getUserId() + ";";
		System.out.println(selectCount);
		// RowCountCallbackHandler countCallback = new RowCountCallbackHandler(); // not
		// reusable
		int rowCount = jdbcTemplateObject.queryForObject(selectCount, Integer.class);
		System.out.println(rowCount);
		if (rowCount == 1) {
			// define query arguments
			String updateQuery = "update user_details set name=?,mobile=?,email=? where user_id=?;";
			Object[] params = { user.getName(), user.getNumber(), user.getUsername(), user.getUserId() };
			// define SQL types of the argument
			// int[] types = {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DATE,
			// Types.BIGINT};
			int rows = jdbcTemplateObject.update(updateQuery, params);
			System.out.println(rows + " row(s) updated.");
			if (rows > 0) {
				retString = "Profile Updated Successfully";
			}
		}
		resp = "{\"message\":\"" + retString + "\"}";
		return resp;
	}

	/*********************************
	 * User Management Ends
	 ************************************/

	public List<Reports> reportDetails(String userId) {
		String SQL = "";
		if (userId.equals("50012") || userId.equals("50011")) {
			SQL = "select attempt_id,call_sid,uuid,lead_ans_time,lead_end_time,lead_talk_duration,lead_no,agent_no,remarks from attempts order by attempt_id desc;";
		} else {
			SQL = "select attempt_id,call_sid,uuid,lead_ans_time,lead_end_time,lead_talk_duration,lead_no,agent_no,remarks from attempts where user_id="
					+ userId + " order by attempt_id asc;";
		}

		System.out.println("query is=" + SQL);
		List<Reports> reports = jdbcTemplateObject.query(SQL, new ReportDetailsMapper());
		return reports;
	}

	public List<ClientHistory> clientHistory(String userId, String leadNo) {

		String query = "SELECT EVENT, DATA, mobile, email_id, req_date, STATUS FROM client_history WHERE user_id = "
				+ userId + " AND mobile = " + leadNo + ";";
		System.out.println("query is=" + query);
		List<ClientHistory> ch = jdbcTemplateObject.query(query, new HistoryDetailsMapper());
		return ch;
	}

	public AgentLeads leadsDetails(String userId) {
		// String SQL="SELECT base.id, base.mobile, call_disposition.remarks FROM base
		// LEFT JOIN call_disposition ON base.mobile=call_disposition.mobile and
		// base.user_id=call_disposition.user_id WHERE base.STATUS = 0 AND
		// base.user_id="+userId+" order by id limit 1;";
		String SQL = "SELECT base.id, base.mobile, call_disposition.remarks, contacts.company_name, contacts.city, contacts.company_website FROM base LEFT JOIN call_disposition ON base.mobile=call_disposition.mobile AND base.user_id=call_disposition.user_id LEFT JOIN contacts ON base.mobile=contacts.mobile WHERE base.status = 0 AND base.user_id="
				+ userId + " order by id limit 1;";
		System.out.println("query is=" + SQL);
		AgentLeads leads = (AgentLeads) jdbcTemplateObject.queryForObject(SQL, new AgentLeadsMapper());
		return leads;
	}

	/*
	 * public List<AgentLeads> leadsDetails(String userId) { //String
	 * SQL="SELECT base.id, base.mobile, call_disposition.remarks FROM base LEFT JOIN call_disposition ON base.mobile=call_disposition.mobile and base.user_id=call_disposition.user_id WHERE base.STATUS = 0 AND base.user_id="
	 * +userId+" order by id limit 1;"; String
	 * SQL="SELECT base.id, base.mobile, call_disposition.remarks, contacts.company_name, contacts.city, contacts.company_website FROM base LEFT JOIN call_disposition ON base.mobile=call_disposition.mobile AND base.user_id=call_disposition.user_id LEFT JOIN contacts ON base.mobile=contacts.mobile WHERE base.status = 0 AND base.user_id="
	 * +userId+" order by id limit 1;"; System.out.println("query is="+SQL); List
	 * <AgentLeads> leads= jdbcTemplateObject.query(SQL, new AgentLeadsMapper());
	 * return leads; }
	 */

	public List<Agents> agentDetails() {
		// String SQL="select
		// widgets.widget_name,agents.agent_id,agents.agent_name,agents.phone_no,agents.status
		// FROM widgets LEFT JOIN agents ON widgets.widget_id=agents.widget_id where
		// agents.phone_no IS NOT NULL AND widgets.user_id="+userId+";";
		String SQL = "select agent_id,agent_name,phone_no,status FROM agents where phone_no IS NOT NULL;";
		// String SQL="select agent_id,widget_id,agent_name,phone_no,status from agents
		// where user_id="+session.getAttribute("user")+";";
		System.out.println("query is=" + SQL);
		List<Agents> agents = jdbcTemplateObject.query(SQL, new AgentDetailsMapper());
		return agents;
	}

	public String createNewAgent(Agents agent) throws IOException {
		String retString = "Request Failed!! Please try again";
		String resp = "";

		String strDate = Util.getDate();

		String selquery = "select count(*) from agents where phone_no=" + agent.getAgentNumber() + ";";
		int cnt = jdbcTemplateObject.queryForObject(selquery, Integer.class);
		if (cnt == 0) {
			String query = "insert into agents (user_id,agent_name,phone_no, agent_email,language_code,status,last_allocated) values (?,?,?,?,?,?,?);";
			int rows = jdbcTemplateObject.update(query, new Object[] { agent.getUserId(), agent.getAgentName(),
					agent.getAgentNumber(), agent.getAgentEmail(), agent.getLanguage(), 1, strDate });
			if (rows > 0) {
				retString = "Agent Configured Successfully";
				resp = "{\"message\":\"" + retString + "\"}";
			}
		} else {
			retString = "Agent Already Exists";
			resp = retString;
		}
		System.out.println("Response: " + retString);

		return resp;
	}

	@Override
	public String deleteAgent(String agentId) {

		String resp = "";
		String retString = "Request Failed!! Please try again";
		String selectCount = "select count(1) from agents where agent_id=" + agentId;
		int rowCount = jdbcTemplateObject.queryForObject(selectCount, Integer.class);

		System.out.println(rowCount);
		if (rowCount == 1) {
			// define query arguments
			String deleteQuery = "delete from agents where agent_id=?;";
			Object[] params = { agentId };
			// define SQL types of the argument
			int[] types = { Types.BIGINT };
			int rows = jdbcTemplateObject.update(deleteQuery, params, types);
			System.out.println(rows + " row(s) deleted.");
			retString = "Agent Deleted Successfully";
		}

		resp = "{\"message\":\"" + retString + "\"}";
		return resp;
	}

	@Override
	public String updateAgentStatus(Agents agent) {

		String resp = "";
		String retString = "Request Failed!! Please try again";
		String selectCount = "select count(1) from agents where agent_id=" + agent.getAgentId();
		int rowCount = jdbcTemplateObject.queryForObject(selectCount, Integer.class);

		System.out.println(rowCount);
		if (rowCount == 1) {
			// define query arguments
			String updateQuery = "update agents set status = ? where agent_id=?;";
			// String updateQuery = "update agents set status ="+agent.getAgentStatus()+"
			// where user_id="+session.getAttribute("user")+" and
			// agent_id="+agent.getAgentId()+";";
			Object[] params = { agent.getAgentStatus(), agent.getAgentId() };
			// define SQL types of the argument
			// int[] types = {Types.VARCHAR, Types.INTEGER, Types.BIGINT};
			System.out.println(updateQuery);
			int rows = jdbcTemplateObject.update(updateQuery, params);
			System.out.println(rows + " row(s) updated.");
			if (agent.getAgentStatus() == 0) {
				retString = "Agent Logout Successful";
			} else {
				retString = "Agent Login Successful";
			}
		}
		resp = "{\"message\":\"" + retString + "\"}";
		return resp;

	}

	@Override
	public String editAgent(Agents agent) {

		String resp = "";
		String retString = "Request Failed!! Please try again";
		String selectCount = "select count(1) from agents where agent_id=" + agent.getAgentId();
		int rowCount = jdbcTemplateObject.queryForObject(selectCount, Integer.class);

		System.out.println(rowCount);
		if (rowCount == 1) {
			// define query arguments
			String updateQuery = "update agents set agent_name = ?, phone_no = ? where agent_id=?;";
			// String updateQuery = "update agents set status ="+agent.getAgentStatus()+"
			// where user_id="+session.getAttribute("user")+" and
			// agent_id="+agent.getAgentId()+";";
			Object[] params = { agent.getAgentName(), agent.getAgentNumber(), agent.getAgentId() };
			// define SQL types of the argument
			// int[] types = {Types.VARCHAR, Types.INTEGER, Types.BIGINT};
			System.out.println(updateQuery);
			int rows = jdbcTemplateObject.update(updateQuery, params);
			System.out.println(rows + " row(s) updated.");

			retString = "Agent Updated Successfully";
		}
		resp = "{\"message\":\"" + retString + "\"}";
		return resp;

	}

	/* Showing Distinct DID list */
	public List<DID> didList(String type) {
		String SQL = "select id,did from dids where status = 0 and type = '" + type + "';";
		System.out.println("query is=" + SQL);
		List<DID> did = jdbcTemplateObject.query(SQL, new DIDMapper());

		return did;
	}

	/* Showing Distinct DID list of a widget */
	public List<DID> did() {
		String SQL = "select id,did from widget_did;";
		System.out.println("query is=" + SQL);
		List<DID> did = jdbcTemplateObject.query(SQL, new DID_WidgetMapper());

		return did;
	}

	@Override
	public String buyDID(DID did) {

		String resp = "";
		String retString = "Request Failed!! Please try again";
		String selectCount = "select count(1) from dids where id=" + did.getId();
		int rowCount = jdbcTemplateObject.queryForObject(selectCount, Integer.class);

		System.out.println(rowCount);
		if (rowCount == 1) {
			// define query arguments
			String updateQuery = "update dids set status = ? where id=?;";
			Object[] params = { 1, did.getId() };
			System.out.println(updateQuery);
			int rows = jdbcTemplateObject.update(updateQuery, params);
			System.out.println(rows + " row(s) updated.");

			String strDate = Util.getDate();
			String query = "insert into widget_did (did,req_date) values (?,?);";
			int rows1 = jdbcTemplateObject.update(query, new Object[] { did.getDid(), strDate });
			if (rows > 0 && rows1 > 0) {
				retString = "DID Bought Successfully";
			}
		}
		resp = "{\"message\":\"" + retString + "\"}";
		return resp;
	}

	public void sendEmail(String emailId, String name, String username, String pwd) {

		System.out.println("Inside sendemail " + emailId);
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("info@viol8.com");
		msg.setTo(emailId);

		msg.setSubject("Tapper Registration");
		msg.setText("Hello " + name
				+ ",  \n Greetings from Violet Automation Private Ltd. \n Thanks for Registering \n Your login details are: \n Username : "
				+ username + " \n Password : " + pwd + " \n Please Login using : mytapper.com");

		javaMailSender.send(msg);

	}

	public void sendEmailWithAttachment(String emailId) throws MessagingException, IOException {

		logger.info("Inside sendEmailWithAttachment " + emailId);
		String[] bcc = { "rajan@viol8.com", "aman@viol8.com", "vageesh@viol8.com", "vikram@viol8.com",
				"lines4raj@gmail.com", "aman.vashist@gmail.com", "thetechnoking@gmail.com", "vikram.arora1985@gmail.com" };
		MimeMessage msg = javaMailSender.createMimeMessage();

		// true = multipart message
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setFrom("info@viol8.com");
		helper.setTo(emailId);
		helper.setBcc(bcc);
		helper.setSubject("Proposal : Website's Callback Bot - Tapper");

		helper.setText("<html>" + "<body>" + "<div>Dear Sir/Mam,</div>" + "<br/>"
				+ "<div>It was nice talking to you. Kindly find below the pricing & pack wise features of our product <strong style='font-color: red'>Website’s Callback bot – Tapper.</strong></div>"
				+ "<br/>"
				+ "<div>We are also offering <strong style='font-color: red'>Free Virtual No. Service </strong>as complementary on all packs above regular pack.</div>"
				+ "<br/>"
				+ "<div>To learn more about working functionalities of this service..pls watch the video given below.</div>"
				+ "<br/>"
				+ "<div><a href=\"https://www.youtube.com/watch?v=PGX0R8VXgfI&feature=youtu.be\" target=\"_blank\">https://www.youtube.com/watch?v=PGX0R8VXgfI&feature=youtu.be</a></div>"
				+ "<br/>"
				+ "<div>Visit Website for more details : <a href=\"https://viol8.com/tapper/\" target=\"_blank\">https://viol8.com/tapper/</a></div>"
				+ "<br/>" + "<div><img src='https://virtualapi.viol8.com/img/pricing.jpg'/></div>" + "<br/>"
				+ "<div>Kindly let us know the pack which suits for your requirement. We would be happy to assist & ensure you the best in class service.</div>"
				+ "<br/>" + "<div>Thanks & Regards,</div>" + "<div>Sales Team</div>" + "<div>Violet automation pvt ltd</div>"
				+ "<div><a href=\"https://viol8.com/tapper/\" target=\"_blank\">www.viol8.com</a></div>"
				+ "<div>Phone : 0172-3500 500</div>" + "</div></body>" + "</html>", true);

		javaMailSender.send(msg);

	}

	@Override
	public UserInfo getDomain(String domain) {
		String SQL = "select logo,company_name from user_details where domain = '" + domain + "';";
		System.out.println("query is=" + SQL);
		UserInfo users = jdbcTemplateObject.queryForObject(SQL, new DomainDetailsMapper());
		return users;
	}

	public String saveLead(AgentLeads lead) {

		logger.info("Save Lead");
		String retString = "";
		String resp = "";
		lead.setAttemptCount(0);
		lead.setReqTime(Util.getDate());
		if (lead.getScheduleTime() == null || lead.getScheduleTime().equals("")) {
			lead.setScheduleTime(Util.getDate());
		}
		lead.setStatus(0);
		lead.setPickId(0);
		lead.setType("direct");

		retString = "Request Failed!! Please try again";

		String selectCount = "select phone_no from agents where user_id=" + lead.getUserId();
		Long agentNo = jdbcTemplateObject.queryForObject(selectCount, Long.class);

		String query = "insert into leads (user_id,country_code,lead_no,agent_no,req_time,schedule_time,attempt_time,cli,status,attempt_count,pick_id,type,retry_status,template_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		int rows = jdbcTemplateObject.update(query,
				new Object[] { lead.getUserId(), 91, lead.getLeadNo(), agentNo, lead.getReqTime(), lead.getScheduleTime(),
						lead.getScheduleTime(), 1723500447, lead.getStatus(), lead.getAttemptCount(), lead.getPickId(),
						lead.getType(), "processing", 0 });

		String updateQuery = "update base set status=1 where mobile=? and user_id=? and id=?;";
		Object[] params = { lead.getLeadNo(), lead.getUserId(), lead.getId() };

		int rows1 = jdbcTemplateObject.update(updateQuery, params);
		logger.info(rows + " row(s) updated.");

		if (rows > 0 && rows1 > 0) {
			retString = "Mobile No. Entered Successfully";
		}

		logger.info("Response: " + retString);
		resp = "{\"message\":\"" + retString + "\"}";
		return resp;
	}

	public AgentLeads skipLead(AgentLeads lead) {

		logger.info("Skip Lead");
		String retString = "";

		retString = "Request Failed!! Please try again";

		String updateQuery = "update base set status=2 where mobile=? and user_id=? and id=?;";
		Object[] params = { lead.getLeadNo(), lead.getUserId(), lead.getId() };

		int rows = jdbcTemplateObject.update(updateQuery, params);
		logger.info(rows + " row(s) updated.");

		if (rows > 0) {
			retString = "Lead Skipped Successfully";
		}
		logger.info(retString);

		String SQL = "SELECT base.id, base.mobile, call_disposition.remarks, contacts.company_name, contacts.city, contacts.company_website FROM base LEFT JOIN call_disposition ON base.mobile=call_disposition.mobile AND base.user_id=call_disposition.user_id LEFT JOIN contacts ON base.mobile=contacts.mobile WHERE base.status = 0 AND base.user_id="
				+ lead.getUserId() + " order by id limit 1;";
		// String SQL="SELECT base.id, base.mobile, call_disposition.remarks FROM base
		// LEFT JOIN call_disposition ON base.mobile=call_disposition.mobile and
		// base.user_id=call_disposition.user_id WHERE STATUS = 0 AND
		// base.user_id="+lead.getUserId()+" order by id limit 1;";
		System.out.println("query is=" + SQL);
		AgentLeads leads = jdbcTemplateObject.queryForObject(SQL, new AgentLeadsMapper());
		return leads;
	}

	public String callDisposition(AgentLeads lead) {

		logger.info("Call Disposition");
		String retString = "";
		String resp = "";

		lead.setReqTime(Util.getDate());
		retString = "Request Failed!! Please try again";

		if (lead.getRemarks().contains("1")) {
			lead.getRemarks().replaceAll("1", "Highly Interested");
		} else if (lead.getRemarks().contains("2")) {
			lead.getRemarks().replaceAll("2", "Medium Interest");
		} else if (lead.getRemarks().contains("3")) {
			lead.getRemarks().replaceAll("3", "Low Interest");
		} else if (lead.getRemarks().contains("4")) {
			lead.getRemarks().replaceAll("4", "Highly Priced");
		} else if (lead.getRemarks().contains("5")) {
			lead.getRemarks().replaceAll("5", "Call not picked");
		} else if (lead.getRemarks().contains("6")) {
			lead.getRemarks().replaceAll("6", "Call Later");
		}

		String query = "insert into call_disposition (user_id,mobile,remarks,req_date) values (?,?,?,?);";
		int rows = jdbcTemplateObject.update(query, new Object[] { lead.getUserId(), lead.getLeadNo(),
				lead.getRemarks() + "," + lead.getAdditionalRemarks(), lead.getReqTime() });

		String updateQuery = "update attempts set remarks=? where lead_no=? order by attempt_id desc limit 1;";
		Object[] params = { lead.getRemarks() + "," + lead.getAdditionalRemarks(), "91" + lead.getLeadNo() };

		int rows1 = jdbcTemplateObject.update(updateQuery, params);
		logger.info(rows1 + " row(s) updated.");

		query = "insert into client_history (event,data,mobile,req_date,status,agent_id,user_id) values (?,?,?,?,?,?,?);";
		jdbcTemplateObject.update(query, new Object[] { "call", lead.getRemarks() + "" + lead.getLeadNo(), Util.getDate(),
				"success", lead.getUserId(), lead.getUserId() });

		if (rows > 0 && rows1 > 0) {
			retString = "Remarks Submitted Successfully";
		}

		logger.info("Response: " + retString);
		resp = "{\"message\":\"" + retString + "\"}";
		return resp;
	}

	public String addBase(Base base) {

		logger.info("Inside Base Upload");
		String retString = "";
		String resp = "";

		retString = "Request Failed!! Please try again";

		String selectCount = "select count(1) from base where user_id=" + base.getUserId() + " and mobile="
				+ base.getLeadNo();
		int cnt = jdbcTemplateObject.queryForObject(selectCount, Integer.class);

		if (cnt == 0) {
			String query = "insert into base (mobile,user_id,agent_id,status) values (?,?,?,?);";
			int rows = jdbcTemplateObject.update(query,
					new Object[] { base.getLeadNo(), base.getUserId(), base.getUserId(), 0 });

			if (rows > 0) {
				retString = "Base Uploaded Successfully";
			}
		}

		logger.info("Response: " + retString);
		resp = "{\"message\":\"" + retString + "\"}";
		return resp;
	}

	@Override
	public String sendSMS(ClientHistory sms) {
		logger.info("Inside Send SMS");
		String retString = "";
		String resp = "";
		String smsStatus = "fail";

		retString = "Request Failed!! Please try again";
		String selectText = "select smstext from sms_master where id=1";
		String smstext = jdbcTemplateObject.queryForObject(selectText, String.class);
		String query = "insert into mt_sms (msisdn,sms_text,req_date,shortcode,status,sms_type,user_id) values (?,?,?,?,?,?,?);";
		int rows = jdbcTemplateObject.update(query,
				new Object[] { sms.getLeadNo(), smstext, Util.getDate(), "TAPPER", 0, 0, sms.getUserId() });

		if (rows > 0) {
			String smsQuery = "select sms_status from mt_sms where mobile=" + sms.getLeadNo()
					+ " order by req_date desc limit 1;";
			smsStatus = jdbcTemplateObject.queryForObject(smsQuery, String.class);
			retString = "SMS Submitted Successfully";
			if (smsStatus == null) {
				smsStatus = "fail";
			}
		}

		query = "insert into client_history (event,mobile,email_id,req_date,status,agent_id,user_id) values (?,?,?,?,?,?,?);";
		jdbcTemplateObject.update(query, new Object[] { "sms", sms.getLeadNo(), sms.getEmailId(), Util.getDate(), smsStatus,
				sms.getUserId(), sms.getUserId() });

		logger.info("Response: " + retString);
		resp = "{\"message\":\"" + retString + "\"}";
		return resp;
	}

	@Override
	public String sendTapperEmail(ClientHistory email) {
		logger.info("Inside Send Email");
		String retString = "";
		String resp = "";
		String emailStatus = "fail";

		retString = "Request Failed!! Please try again";

		logger.info("Sending Email...");

		try {
			sendEmailWithAttachment(email.getEmailId());
			retString = "Email Sent Successfully";
			emailStatus = "success";
		} catch (Exception e) {
			logger.error("Error in sending Email " + e);
			e.printStackTrace();
			retString = "Error in sending Email";
			emailStatus = "fail";
		}

		String query = "insert into client_history (event,mobile,email_id,req_date,status,agent_id,user_id) values (?,?,?,?,?,?,?);";
		jdbcTemplateObject.update(query, new Object[] { "email", email.getLeadNo(), email.getEmailId(), Util.getDate(),
				emailStatus, email.getUserId(), email.getUserId() });

		logger.info("Response: " + retString);
		resp = "{\"message\":\"" + retString + "\"}";
		return resp;
	}

	@Override
	public String followUp(FollowUp flead) {

		String query = "";
		String resp = "";
		String retString = "Request Failed!! Please try again";

		String SQL = "select user_id,country_code,lead_no,agent_no,cli from leads where call_uid='" + flead.getCallUID()
				+ "';";
		flead = jdbcTemplateObject.queryForObject(SQL, new FollowUpDetailsMapper());
		flead.setAttemptCount(0);
		flead.setReqTime(Util.getDate());
		flead.setScheduleTime(Util.getDate());
		flead.setStatus(0);
		flead.setPickId(0);
		flead.setType("followup");

		query = "insert into leads (user_id,country_code,lead_no,agent_no,req_time,schedule_time,attempt_time,status,attempt_count,pick_id,type,cli,template_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?);";
		int rows = jdbcTemplateObject.update(query,
				new Object[] { flead.getUserId(), flead.getCountryCode(), flead.getLeadNo(), flead.getAgentNo(), Util.getDate(),
						Util.getDate(), Util.getDate(), flead.getStatus(), flead.getAttemptCount(), flead.getPickId(),
						flead.getType(), flead.getCli(), 0 });
		if (rows > 0) {
			retString = "Mobile No. Entered Successfully";
		}

		logger.info("Response: " + retString);
		resp = "{\"message\":\"" + retString + "\"}";
		return resp;
	}

	@Override
	public List<DNC> getDNCs(String userId) {
		String SQL = "select * from dnc where user_id IN (select user_id from users where parent = " + userId
				+ " OR user_id = " + userId + " ) ";
		System.out.println("query is=" + SQL);
		List<DNC> dnc = jdbcTemplateObject.query(SQL, new dncMapper());

		return dnc;
	}

	@Override
	public int deleteDNCById(String dnc_id) {
		String SQL = "delete from dnc where dnc_id = ?";
		int delete_id = jdbcTemplateObject.update(SQL, dnc_id);
		return delete_id;
	}

	@Override
	public String createNewDNC(DNC dnc) {
		String retString = "Cannot Create DNC!! Please try again!";

		String query = "insert into dnc (email,mobile,user_id,flag,remark,company_name) values (?,?,?,?,?,?);";

		int rows = jdbcTemplateObject.update(query,
				new Object[] { dnc.getEmail().toLowerCase(), dnc.getMobile(), dnc.getUser_id(),
						dnc.getFlag() != null ? dnc.getFlag() : 0, dnc.getRemark() != null ? dnc.getRemark() : "",
						dnc.getCompany_name() != null ? dnc.getCompany_name() : "" });

		if (rows > 0) {
			retString = "DNC Added Successfully!";
		} else {
			retString = "Error while added!";
		}
		return retString;
	}

	@Override
	public List<Tasks> getTasks(String userId) {
		String SQL = "select * from tasks where user_id IN (select user_id from users where parent = " + userId
				+ " OR user_id = " + userId + " ) ";
		System.out.println("query is=" + SQL);
		List<Tasks> tasks = jdbcTemplateObject.query(SQL, new tasksMapper());

		return tasks;
	}

	@Override
	public int deleteTaskById(String task_id) {
		String SQL = "delete from tasks where task_id = ?";
		int delete_id = jdbcTemplateObject.update(SQL, task_id);
		return delete_id;
	}

	@Override
	public int createNewTask(Tasks task) {
		String query = "insert into tasks (name, description, user_id) values (?,?,?);";
		int rows = jdbcTemplateObject.update(query,
				new Object[] { task.getName(), task.getDescription(), task.getUser_id() });
		System.out.println("Row @@@@@@@@@@ " + rows);
		int task_id = jdbcTemplateObject.queryForObject("SELECT MAX(task_id) FROM tasks;", Integer.class);
		return task_id;
	}

	@Override
	public void addBaseTask(String task_id, String mobile, String user_id) {
		String query = "INSERT INTO base (mobile, user_id, task_id) VALUES(?, ?, ?);";
		int rows = jdbcTemplateObject.update(query, new Object[] { mobile, user_id, task_id });
		System.out.println("Inserted Data" + rows);
	}

	@Override
	public String updateTaskStatus(String status, String task_id, String user_id) {
		String resultString = "";
		if (Integer.parseInt(status) == 3 || Integer.parseInt(status) == 2) {
			String SQL = "update tasks set status = ? where task_id = ? ;";
			jdbcTemplateObject.update(SQL, new Object[] { status, task_id });
			resultString = "Task with task_id " + task_id + " Updated successfully with status " + status + " ;";
		} else {
			String SQL = "select COUNT(*) AS runing_tasks from tasks where user_id = " + user_id + " AND status = 1;";
			int count = jdbcTemplateObject.queryForObject(SQL, Integer.class);
			if (count == 0) {
				SQL = "update tasks set status = ? where task_id = ? ;";
				jdbcTemplateObject.update(SQL, new Object[] { status, task_id });
				resultString = "Task with task_id " + task_id + " Updated successfully with status " + status + " ;";
			} else {
				throw new RuntimeException("A Task is allready running");
			}
		}

		return resultString;
	}

	// get packs list from database
	@Override
	public List<Packs> getPacks() {
		String SQL = "select * from packs";
		System.out.println("query is=" + SQL);
		List<Packs> packs = jdbcTemplateObject.query(SQL, new packsMapper());
		return packs;
	}
}
