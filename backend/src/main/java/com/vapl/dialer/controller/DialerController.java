package com.vapl.dialer.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vapl.dialer.dao.DialerDao;
import com.vapl.dialer.exception.IncorrectParametersException;
import com.vapl.dialer.exception.NoDataException;
import com.vapl.dialer.exception.UserNotFoundException;
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
import com.vapl.dialer.service.TaskService;
//import com.vapl.dialer.pojo.UserListCredits;

@RestController
@RequestMapping("/app")
public class DialerController {

	private static final Logger logger = LoggerFactory.getLogger(DialerController.class);

	@Autowired
	private DialerDao dialerDao;

	@Autowired
	private TaskService taskservice;

	/************************* User Management *************************/

	// User Registration
	@CrossOrigin
	// @PreAuthorize("hasAnyRole('ROLE_SADMIN','ROLE_ADMIN','ROLE_USER')")
	@PostMapping(value = "/register", produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public ResponseEntity<String> register(@RequestBody UserInfo user) throws IOException, IncorrectParametersException {
		String resp = "Parameters Incorrect";

		if (user.getUsername() == null) {
			throw new IncorrectParametersException("username required");
		}
		if (user.getName() == null) {
			throw new IncorrectParametersException("name required");
		}
		if (user.getPassword() == null) {
			throw new IncorrectParametersException("password required");
		}
		if (user.getNumber() == null) {
			throw new IncorrectParametersException("contact required");
		}

		resp = dialerDao.register(user);

		if (resp.equals("User Already Exists! Please choose another Username")) {
			return new ResponseEntity<String>(resp, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>(resp, HttpStatus.OK);
		}

	}

	// User Profile Info
	@CrossOrigin
	@PreAuthorize("hasAnyRole('ROLE_SADMIN','ROLE_ADMIN','ROLE_USER')")
	@GetMapping(value = "/user/profile/{userId}", produces = "application/json", headers = "Accept=application/json")
	public ResponseEntity<UserInfo> getUserProfile(@PathVariable("userId") String userId)
			throws UserNotFoundException, NoDataException {

		logger.info("UserInfo " + userId);
		UserInfo user = null;

		if (userId == null || userId.equals("")) {
			throw new UserNotFoundException("User is null");
		} else {
			user = dialerDao.userProfile(userId);
			logger.info("Response " + user);
			if (user == null) {
				return new ResponseEntity<UserInfo>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<UserInfo>(user, HttpStatus.OK);
		}
	}

	// User List
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ROLE_SADMIN','ROLE_ADMIN','ROLE_USER')")
	@GetMapping(value = "/user/{userId}", produces = "application/json", headers = "Accept=application/json")
	public ResponseEntity<List<UserDetails>> getUsers(@PathVariable("userId") String userId)
			throws UserNotFoundException, NoDataException {

		logger.info("UsersList " + userId);
		List<UserDetails> userDetails = null;
		if (userId == null || userId.equals("")) {
			throw new UserNotFoundException("User is null");
		} else {
			userDetails = dialerDao.userList(userId);
			logger.info("Response " + userDetails);
			if (userDetails == null) {
				return new ResponseEntity<List<UserDetails>>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<UserDetails>>(userDetails, HttpStatus.OK);
		}
	}

	// User Update
	@CrossOrigin
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@PostMapping(value = "/user/update", produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public ResponseEntity<String> updateProfile(@RequestBody UserInfo user)
			throws IOException, IncorrectParametersException {

		String resp = "Parameters Incorrect";
		logger.info("Register " + user.getName() + "," + user.getNumber() + ",");
		if (user.getName() == null || user.getNumber() == null) {
			throw new IncorrectParametersException("Parameters Incorrect");
		} else {
			resp = dialerDao.updateProfile(user);
		}
		// logger.info("Response " + resp);

		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// Delete User
	@CrossOrigin
	@PreAuthorize("hasAnyRole('ROLE_SADMIN','ROLE_ADMIN','ROLE_USER')")
	@DeleteMapping(value = "/user/{userId}", produces = "application/json", headers = "Accept=application/json")
	public ResponseEntity<String> deleteUser(@RequestBody UserInfo user, @PathVariable("userId") String userId)
			throws UserNotFoundException, NoDataException {

		String resp = "Parameters Incorrect";
		logger.info("Delete " + userId);
		if (userId == null) {
			throw new IncorrectParametersException("Parameters Incorrect");
		} else {
			resp = dialerDao.deleteUser(user, userId);
		}

		logger.info("Response " + resp);

		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// // Add Credits
	// @CrossOrigin
	// @PreAuthorize("hasAnyRole('ROLE_SADMIN','ROLE_ADMIN')")
	// @PostMapping(value = "/credits", produces = "application/json", consumes =
	// "application/json", headers = "Accept=application/json")
	// public ResponseEntity<String> addCredits(@RequestBody UserInfo user)
	// throws IOException, IncorrectParametersException {
	//
	// String resp = "Parameters Incorrect";
	// logger.info("Register " + user.getUserId() + "," + user.getParent() + "," +
	// user.getCredits());
	// if (user.getUserId() == null || user.getParent() == null || user.getCredits()
	// == null) {
	// throw new IncorrectParametersException("Parameters Incorrect");
	// } else {
	// resp = dialerDao.addCredits(user);
	// }
	// logger.info("Response " + resp);
	//
	// return new ResponseEntity<String>(resp, HttpStatus.OK);
	// }

	// Dashboard
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ROLE_SADMIN','ROLE_ADMIN','ROLE_USER')")
	@GetMapping(value = "/dashboard/{userId}", produces = "application/json", headers = "Accept=application/json")
	public ResponseEntity<Dashboard> dashboard(@PathVariable("userId") String userId, Authentication authentication)
			throws UserNotFoundException, NoDataException {

		logger.info("Dashboard " + userId);
		Dashboard dashboard = null;
		logger.info("Token Info " + authentication.getName());
		String[] userArr = authentication.getName().split(";");
		if (userId == null || userId.equals("")) {
			throw new UserNotFoundException("User is null");
		} else if (!userArr[0].equals(userId)) {
			logger.info("User Id and token User id donot match");
			throw new UserNotFoundException("Unauthorized");
		} else {
			dashboard = dialerDao.dashboardDetails(userId);
			logger.info("Response " + dashboard);
			if (dashboard == null) {
				return new ResponseEntity<Dashboard>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<Dashboard>(dashboard, HttpStatus.OK);
		}
	}

	// Branding
	@CrossOrigin

	@GetMapping(value = "/branding/{domain:.+}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfo> getDomain(@PathVariable("domain") String domain) throws NoDataException {
		// public ResponseEntity<UserInfo> getDomain(@RequestParam("domain") String
		// domain) throws NoDataException{

		logger.info("Branding " + domain);

		UserInfo users = null;
		if (domain == null || domain.equals("")) {
			throw new IncorrectParametersException("Parameters Incorrect");
		} else {
			users = dialerDao.getDomain(domain);
			logger.info("Response " + users);
			if (users == null) {
				return new ResponseEntity<UserInfo>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<UserInfo>(users, HttpStatus.OK);
		}
	}

	/*************************
	 * User Management Ends
	 ***********************************/

	/********************** Agent Management ***********************/

	// Agent List
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ROLE_SADMIN','ROLE_ADMIN','ROLE_USER')")
	@GetMapping(value = "/agent/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Agents>> agentDetails() {

		List<Agents> resp = dialerDao.agentDetails();
		logger.info("Response @@@@@@@@@@@ " + resp);
		if (resp == null) {
			return new ResponseEntity<List<Agents>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Agents>>(resp, HttpStatus.OK);
		}
	}

	// Create New Agent
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ROLE_SADMIN','ROLE_ADMIN','ROLE_USER')")
	@PostMapping(value = "/agent", produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public ResponseEntity<String> createAgent(@RequestBody Agents agent, Authentication authentication)
			throws IOException {
		String user_id = authentication.getName().split(";")[0];
		agent.setUserId(Integer.parseInt(user_id));
		// TODO: process saveWidget
		String resp = "Parameters Incorrect";
		System.out.println("AgentCreation ########### " + agent.getUserId() + "," + agent.getAgentName() + ","
				+ agent.getAgentNumber() + "," + agent.getAgentEmail());
		if (agent.getUserId() == null || agent.getAgentName() == null || agent.getAgentNumber() == null
				|| agent.getAgentEmail() == null) {
			throw new IncorrectParametersException("Parameters Incorrect");
		}

		resp = dialerDao.createNewAgent(agent);

		System.out.println("Response @#####" + resp);
		if (resp.equals("Agent Already Exists")) {
			return new ResponseEntity<String>(resp, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// Edit Agent
	@CrossOrigin
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PostMapping(value = "/agent/update", produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public ResponseEntity<String> editAgent(@RequestBody Agents agent, Authentication authentication) throws IOException {
		String user_id = authentication.getName().split(";")[0];
		String resp = "Parameters Incorrect";
		logger.info("AgentEdit " + agent.getAgentId() + "," + agent.getAgentName() + "," + agent.getAgentNumber());
		if (agent.getAgentId() == null || agent.getAgentName() == null || agent.getAgentNumber() == null) {
			throw new IncorrectParametersException("Parameters Incorrect");
		} else {
			resp = dialerDao.editAgent(agent);
		}
		logger.info("Response Edit ##### " + resp);
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// Delete Agent
	@CrossOrigin
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@DeleteMapping(value = "/agent/{agentId}", produces = "application/json", headers = "Accept=application/json")
	public ResponseEntity<String> deleteAgent(@PathVariable("agentId") String agentId)
			throws UserNotFoundException, NoDataException {

		String resp = "Parameters Incorrect";
		logger.info("Delete " + agentId);
		resp = dialerDao.deleteAgent(agentId);
		logger.info("Response " + resp);

		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// Login/Logout Agent
	@CrossOrigin
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PostMapping(value = "/agent/edit", produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public ResponseEntity<String> updateAgentStatus(@RequestBody Agents agent)
			throws UserNotFoundException, NoDataException {

		String resp = "Parameters Incorrect";
		if (agent.getAgentId() == null || agent.getAgentStatus() == null) {
			throw new IncorrectParametersException("Parameters Incorrect");
		} else {
			resp = dialerDao.updateAgentStatus(agent);
		}
		logger.info("Response " + resp);
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// Reports Detail
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@GetMapping(value = "/report/{userId}", produces = "application/json", headers = "Accept=application/json")
	public ResponseEntity<List<Reports>> reportDetails(@PathVariable("userId") String userId)
			throws UserNotFoundException, NoDataException {
		List<Reports> resp = dialerDao.reportDetails(userId);
		// logger.info("Response " + resp);
		if (resp == null) {
			return new ResponseEntity<List<Reports>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Reports>>(resp, HttpStatus.OK);
		}
	}

	// Get Agent Wise Leads
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@GetMapping(value = "/leads/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AgentLeads> leadsDetails(@PathVariable("userId") String userId) {

		logger.info("Fetching Leads for Agent Id " + userId);
		AgentLeads resp = dialerDao.leadsDetails(userId);
		logger.info("Response " + resp);
		if (resp == null) {
			return new ResponseEntity<AgentLeads>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<AgentLeads>(resp, HttpStatus.OK);
		}
	}

	// Get Agent Wise Client History
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@GetMapping(value = "/agent/clienthistory/{userId}/{leadNo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClientHistory>> clientHistory(@PathVariable("userId") String userId,
			@PathVariable("leadNo") String leadNo) {
		logger.info("Fetching Client History with User Id " + userId + "and Mobile No. " + leadNo);
		List<ClientHistory> resp = dialerDao.clientHistory(userId, leadNo);
		logger.info("Response " + resp);
		if (resp == null) {
			return new ResponseEntity<List<ClientHistory>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<ClientHistory>>(resp, HttpStatus.OK);
		}
	}

	// Base Upload
	@CrossOrigin
	// @PreAuthorize("hasAnyRole('ROLE_SADMIN','ROLE_ADMIN')")
	@PostMapping(value = "/base", produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public ResponseEntity<String> addBase(@RequestBody Base base) throws IOException, IncorrectParametersException {

		String resp = "Parameters Incorrect";
		logger.info("Base Upload " + base.getLeadNo() + "," + base.getUserId());
		if (base.getLeadNo() == null || base.getUserId() == null) {
			throw new IncorrectParametersException("Parameters Incorrect");
		} else {
			resp = dialerDao.addBase(base);
		}
		logger.info("Response " + resp);

		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// Save Lead
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@PostMapping(value = "/lead", produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public ResponseEntity<String> saveLead(@RequestBody AgentLeads lead) {

		logger.info("Lead Creation " + lead.getId() + "," + lead.getLeadNo() + "," + lead.getUserId());
		String resp = "Parameters Incorrect";

		if (lead.getId() == null || lead.getLeadNo() == null || lead.getUserId() == null) {
			throw new IncorrectParametersException("Parameters Incorrect");
		} else {
			resp = dialerDao.saveLead(lead);
		}
		logger.info("Response " + resp);
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// Skip Lead
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@PostMapping(value = "/skip", produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public ResponseEntity<AgentLeads> skipLead(@RequestBody AgentLeads lead) {

		logger.info("Skip Lead " + lead.getId() + "," + lead.getLeadNo() + "," + lead.getUserId());

		AgentLeads resp = dialerDao.skipLead(lead);
		logger.info("Response " + resp);
		if (resp == null) {
			return new ResponseEntity<AgentLeads>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<AgentLeads>(resp, HttpStatus.OK);
		}
	}

	// Call Dispose
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@PostMapping(value = "/calldispose", produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public ResponseEntity<String> callDisposition(@RequestBody AgentLeads lead) {

		logger.info(
				"Call Disposition " + lead.getLeadNo() + "," + lead.getUserId() + "," + lead.getRemarks() + "," + lead.getId());
		String resp = "Parameters Incorrect";

		if (lead.getId() == null || lead.getLeadNo() == null || lead.getUserId() == null || lead.getRemarks() == null) {
			throw new IncorrectParametersException("Parameters Incorrect");
		} else {
			resp = dialerDao.callDisposition(lead);
		}
		logger.info("Response " + resp);
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// DID List
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/did/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DID>> didList(@PathVariable("type") String type) {

		logger.info("Fetching DIDs with " + type);
		List<DID> resp = dialerDao.didList(type);
		logger.info("Response " + resp);
		if (resp == null) {
			return new ResponseEntity<List<DID>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<DID>>(resp, HttpStatus.OK);
		}
	}

	// DID List
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/did/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DID>> did() {

		logger.info("Fetching DIDs");
		List<DID> resp = dialerDao.did();
		logger.info("Response " + resp);
		if (resp == null) {
			return new ResponseEntity<List<DID>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<DID>>(resp, HttpStatus.OK);
		}
	}

	// Buy DID
	@CrossOrigin
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PostMapping(value = "/did/buy", produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public ResponseEntity<String> buyDID(@RequestBody DID did) throws UserNotFoundException, NoDataException {

		String resp = "Parameters Incorrect";
		if (did.getId() == null || did.getDid() == null) {
			throw new IncorrectParametersException("Parameters Incorrect");
		} else {
			resp = dialerDao.buyDID(did);
		}
		logger.info("Response " + resp);
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	/************************ Agent Management Ends ***************************/

	// Send SMS
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@PostMapping(value = "/sendsms", produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public ResponseEntity<String> sendSMS(@RequestBody ClientHistory sms) {

		logger.info("Send SMS " + sms.getLeadNo() + "," + sms.getUserId());
		String resp = "Parameters Incorrect";

		if (sms.getLeadNo() == null || sms.getUserId() == null) {
			throw new IncorrectParametersException("Parameters Incorrect");
		} else {
			resp = dialerDao.sendSMS(sms);
		}
		logger.info("Response " + resp);
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// Send Email
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@PostMapping(value = "/sendemail", produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public ResponseEntity<String> sendTapperEmail(@RequestBody ClientHistory email) {

		logger.info("Send Email " + email.getEmailId() + "," + email.getUserId());
		String resp = "Parameters Incorrect";

		if (email.getEmailId() == null || email.getUserId() == null) {
			throw new IncorrectParametersException("Parameters Incorrect");
		} else {
			resp = dialerDao.sendTapperEmail(email);
		}
		logger.info("Response " + resp);
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// Follow Up
	@CrossOrigin
	@PostMapping(value = "/followup", produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public ResponseEntity<String> followUp(@RequestBody FollowUp flead) {

		logger.info("Followup " + flead.getCallUID());
		String resp = "Parameters Incorrect";

		if (flead.getCallUID() == null) {
			throw new IncorrectParametersException("Parameters Incorrect");
		} else {
			resp = dialerDao.followUp(flead);
		}
		logger.info("Response " + resp);
		return new ResponseEntity<String>(resp, HttpStatus.OK);
	}

	// DNC list
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ROLE_SADMIN', 'ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/dnclist/{userId}", produces = "application/json", headers = "Accept=application/json")
	public ResponseEntity<List<DNC>> dncList(@PathVariable("userId") String userId) {
		List<DNC> dnc = null;
		if (userId == null) {
			return new ResponseEntity<List<DNC>>(dnc, HttpStatus.NO_CONTENT);
		}
		dnc = dialerDao.getDNCs(userId);
		return new ResponseEntity<List<DNC>>(dnc, HttpStatus.OK);
	}

	// create DNC
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ROLE_SADMIN', 'ROLE_ADMIN', 'ROLE_USER')")
	@PostMapping(value = "/dnc", produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public ResponseEntity<String> createDNC(@RequestBody DNC dnc) throws IOException, IncorrectParametersException {
		System.out.println("Email" + dnc.getEmail() + " Mobile " + dnc.getMobile() + "Users_id " + dnc.getUser_id());
		String resp = "Parameter Incorrect";
		if (dnc.getUser_id() == null && (dnc.getEmail() == null || dnc.getMobile() == null)) {
			resp = "Incorrect Parameter";
			return new ResponseEntity<String>(resp, HttpStatus.BAD_REQUEST);
		} else {
			resp = dialerDao.createNewDNC(dnc);
			return new ResponseEntity<String>(resp, HttpStatus.OK);
		}
	}

	// Delete DNC
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ROLE_SADMIN', 'ROLE_ADMIN', 'ROLE_USER')")
	@DeleteMapping(value = "/dnc/{dnc_id}", produces = "application/json", headers = "Accept=application/json")
	public ResponseEntity<String> deleteDNC(@PathVariable("dnc_id") String dnc_id) {

		// String user_id = authentication.getName().split(";")[0];//Authentication
		// authentication
		if (dnc_id == null) {
			return new ResponseEntity<String>("dnc_id required!", HttpStatus.BAD_REQUEST);
		}
		int delete_id = dialerDao.deleteDNCById(dnc_id);
		if (delete_id != 0) {
			return new ResponseEntity<String>("deleted Successfully!", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("DNC not exist with id: " + dnc_id + " !", HttpStatus.BAD_REQUEST);
		}
	}

	// get Tasks list
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ROLE_SADMIN', 'ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/tasks/{userId}", produces = "application/json", headers = "Accept=application/json")
	public ResponseEntity<List<Tasks>> taskList(@PathVariable("userId") String userId) {
		List<Tasks> tasks = null;
		if (userId == null) {
			return new ResponseEntity<List<Tasks>>(tasks, HttpStatus.NO_CONTENT);
		}
		tasks = dialerDao.getTasks(userId);
		return new ResponseEntity<List<Tasks>>(tasks, HttpStatus.OK);
	}

	// Delete Task
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ROLE_SADMIN', 'ROLE_ADMIN', 'ROLE_USER')")
	@DeleteMapping(value = "/tasks/{task_id}", produces = "application/json", headers = "Accept=application/json")
	public ResponseEntity<String> deleteTask(@PathVariable("task_id") String task_id) {

		if (task_id == null) {
			return new ResponseEntity<String>("task_id required!", HttpStatus.BAD_REQUEST);
		}
		int numberOfTaskDelete = dialerDao.deleteTaskById(task_id);
		if (numberOfTaskDelete != 0) {
			return new ResponseEntity<String>("deleted Successfully!", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Task not exist with id: " + task_id + "!", HttpStatus.BAD_REQUEST);
		}
	}

	// Upload file with Task
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ROLE_SADMIN', 'ROLE_ADMIN', 'ROLE_USER')")
	@PostMapping(value = "/tasks", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<String> createTask(@RequestPart("file") MultipartFile file, @RequestPart("task") String task,
			Authentication authentication) throws IOException {
		Tasks taskAsJson = taskservice.getJson(task);
		try {
			String[] extention = file.getOriginalFilename().split("\\.");

			if (taskAsJson.getName() == null && taskAsJson.getUser_id() == null) {
				return new ResponseEntity<String>("Task name and user_id require!", HttpStatus.BAD_REQUEST);
			}

			if (extention.length == 2 && !(extention[1].equals("csv") || extention[1].equals("txt"))) {
				return new ResponseEntity<String>("File should be txt or csv!", HttpStatus.BAD_REQUEST);
			}
			if (file.isEmpty()) {
				return new ResponseEntity<String>("File require to add task base!", HttpStatus.BAD_REQUEST);
			}

			List<Long> list = new ArrayList<>();
			if (!file.isEmpty()) {
				String line;
				InputStream is = file.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					if (line.matches("\\d+")) {
						list.add(Long.parseLong(line));
					}
				}
			}
			if (list.size() == 0) {
				return new ResponseEntity<String>("File should be contain at lest a number!", HttpStatus.BAD_REQUEST);
			}
			String user_id = authentication.getName().split(";")[0];

			int task_id = dialerDao.createNewTask(taskAsJson);

			for (int i = 0; i < list.size(); i++) {
				dialerDao.addBaseTask(String.valueOf(task_id), String.valueOf(list.get(i)), user_id);
			}

			return new ResponseEntity<String>("User have created!", HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("User have not created!", HttpStatus.BAD_REQUEST);
		}

	}

	// Change Task Status
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ROLE_SADMIN', 'ROLE_ADMIN', 'ROLE_USER')")
	@PutMapping(value = "/tasks/{task_id}", produces = "application/json", consumes = "application/json", headers = "Accept=application/json")
	public ResponseEntity<String> updateTasksStatus(@PathVariable("task_id") String task_id, @RequestBody String status,
			Authentication authentication) throws IOException, IncorrectParametersException {

		String user_id = authentication.getName().split(";")[0];

		String resp = "Updated successfully!";
		try {

			resp = dialerDao.updateTaskStatus(status, task_id, user_id);
			return new ResponseEntity<String>(resp, HttpStatus.OK);

		} catch (Exception err) {
			// err.printStackTrace();
			return new ResponseEntity<String>(err.getMessage().toString(), HttpStatus.NOT_ACCEPTABLE);
		}

	}

	// get Pack list
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyRole('ROLE_SADMIN', 'ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/packs", produces = "application/json", headers = "Accept=application/json")
	public ResponseEntity<List<Packs>> packsList() {
		List<Packs> packs = null;
		packs = dialerDao.getPacks();
		return new ResponseEntity<List<Packs>>(packs, HttpStatus.OK);
	}

}
