package pt.ulisboa.tecnico.sise.insure.ws.server;

import javax.jws.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import pt.ulisboa.tecnico.sise.insure.ws.server.domain.ClaimItem;
import pt.ulisboa.tecnico.sise.insure.ws.server.domain.ClaimRecord;
import pt.ulisboa.tecnico.sise.insure.ws.server.domain.CustomerItem;

@WebService(portName = "MobInsureWSPort",
			serviceName = "MobInsureWS",
			targetNamespace = "http://pt.ulisboa.tecnico.sise.insure.ws.server/",
			endpointInterface = "pt.ulisboa.tecnico.sise.insure.ws.server.InSureWS")
public class InSureWSImpl implements InSureWS {

	@Override
	public String hello(String name) {
		Logger.logRequest("hello (" + "name:"+ name + ")");
		String ret = "Hello '" + name + "'!";
		Logger.logResponse(ret);
		return ret;
	}

	@Override
	public String login(String username, String password) {
		Logger.logRequest("login (" + "username:"+ username + ", password: " + password + ")");
		int sessionId = DataManager.getInstance().login(username, password);
		String ret = "" + sessionId;
		Logger.logResponse(ret);
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String listCustomerInfo(String sessionId) {
		Logger.logRequest("listCustomerInfo (" + "sessionId:"+ sessionId + ")");

		CustomerItem item = DataManager.getInstance().listCustomerInfo(
				Integer.parseInt(sessionId));

		String ret = "{}";
		if (item != null) {
			JSONObject response = new JSONObject();
			response.put("customerName", 	item.getCustomerName());
			response.put("fiscalNumber", 	item.getFiscalNumber());
			response.put("address", 		item.getAddress());
			response.put("dateOfBirth", 	item.getDateOfBirth());
			response.put("policyNumber", 	item.getPolicyNumber());
			ret = response.toJSONString();
		}
		Logger.logResponse(ret);
		return ret;
	}

	@Override
	public String submitNewClaim(String sessionId, String claimTitle, String claimDescription) {
		Logger.logRequest("submitNewClaim (" + "sessionId:"+ sessionId + 
				", claimTitle: " + claimTitle + ", claimDescription:" + claimDescription + ")");

		boolean status = DataManager.getInstance().submitNewClaim(Integer.parseInt(sessionId), 
				claimTitle, claimDescription);
		
		String res = (status)?"true":"false";
		Logger.logResponse(res);
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String listClaimHistory(String sessionId) {
		Logger.logRequest("listClaimHistory (" + "sessionId:"+ sessionId + ")");
		
		ClaimItem [] claims = DataManager.getInstance().listClaimHistory(
				Integer.parseInt(sessionId));

		JSONArray list = new JSONArray();
		for (int i = 0; i < claims.length; i++) {
			ClaimItem c = claims[i];
			JSONObject claim = new JSONObject();
			claim.put("claimId", 	c.getClaimId());
			claim.put("claimTitle", c.getClaimTitle());
			list.add(claim);
		}
		String ret = list.toJSONString();
		Logger.logResponse(ret);
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String readClaim(String sessionId, String claimId) {
		Logger.logRequest("readClaim (" + "sessionId:"+ sessionId + ", claimId: " + claimId + ")");
		
		ClaimRecord record = DataManager.getInstance().readClaim(Integer.parseInt(sessionId),
				Integer.parseInt(claimId));
		String ret = "{}";
		if (record != null) {
			JSONObject response = new JSONObject();
			response.put("claimId", 		record.getClaimId());
			response.put("submissionDate", 	record.getSubmissionDate());
			response.put("claimTitle", 		record.getClaimTitle());
			response.put("description", 	record.getDescription());
			response.put("status", 			record.getStatus());
			ret = response.toJSONString();
		}
		Logger.logResponse(ret);
		return ret;
	}

	@Override
	public String authorizeDataAccess(String sessionId, String claimId, String decision) {
		Logger.logRequest("authorizeDataAccess (" + "sessionId:"+ sessionId + ", claimId: " + claimId + 
				", decision: " + decision + ")");

		boolean stat = DataManager.getInstance().authorizeDataAccess(Integer.parseInt(sessionId),
				Integer.parseInt(claimId), decision.equals("true"));
		String ret = "" + stat;
		Logger.logResponse(ret);
		return ret;
	}
	
	@Override
	public boolean returnAuthorizationInfo(String sessionId, String claimId){
		boolean stat = DataManager.getInstance().getAuthorizationInformation(sessionId, claimId);
		return stat;
	}

	@Override
	public String logout(String sessionId) {
		Logger.logRequest("logout (" + "sessionId:"+ sessionId + ")");

		boolean stat = DataManager.getInstance().logout(Integer.parseInt(sessionId));
		String ret = "" + stat;
		Logger.logResponse(ret);
		return ret;
	}
}
