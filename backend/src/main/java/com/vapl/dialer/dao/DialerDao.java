package com.vapl.dialer.dao;

import java.io.IOException;
import java.util.List;

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
// import com.vapl.dialer.pojo.UserListCredits;

public interface DialerDao {

	public String register(UserInfo user);

	public UserInfo getUserInfo(String userid);

	public UserInfo userProfile(String userId);

	public String updateProfile(UserInfo user);

	public String deleteUser(UserInfo user, String userid);

	public String addCredits(UserInfo user);

	public List<UserDetails> userList(String userId);

	public List<UserInfo> userDetails(String userId);

	public String editUser(UserInfo info);

	public UserInfo getDomain(String domain);

	public List<Agents> agentDetails();

	public String createNewAgent(Agents agent) throws IOException;

	public String deleteAgent(String agentId);

	public String updateAgentStatus(Agents agent);

	public String editAgent(Agents agent);

	public String saveLead(AgentLeads lead);

	public AgentLeads skipLead(AgentLeads lead);

	public String followUp(FollowUp flead);

	public String callDisposition(AgentLeads lead);

	public String addBase(Base base);

	public List<Reports> reportDetails(String userId);

	public Dashboard dashboardDetails(String userId);

	public List<DID> didList(String type);

	public List<DID> did();

	public String buyDID(DID did);

	public AgentLeads leadsDetails(String userId);

	public String sendSMS(ClientHistory sms);

	public String sendTapperEmail(ClientHistory email);

	public List<ClientHistory> clientHistory(String userId, String leadNo);

	public List<DNC> getDNCs(String userId);

	public String createNewDNC(DNC dnc);

	public int deleteDNCById(String dnc_id);

	public List<Tasks> getTasks(String userId);

	public int deleteTaskById(String task_id);

	public int createNewTask(Tasks task);

	public void addBaseTask(String task_id, String mobile, String user_id);

	public String updateTaskStatus(String status, String task_id, String user_id);

	public List<Packs> getPacks();
}
