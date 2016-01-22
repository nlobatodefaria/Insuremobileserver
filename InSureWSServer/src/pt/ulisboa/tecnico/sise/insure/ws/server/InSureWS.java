package pt.ulisboa.tecnico.sise.insure.ws.server;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(
		name = "InSureWS",
		targetNamespace = "http://pt.ulisboa.tecnico.sise.insure.ws.server/")
public interface InSureWS {
	
	@WebMethod(operationName = "hello")
	public String hello(String name);
	
	@WebMethod(operationName = "login")
	public String login(String username, String password);
	
	@WebMethod(operationName = "listCustomerInfo")
	public String listCustomerInfo(String sessionId);

	@WebMethod(operationName = "submitNewClaim")
	public String submitNewClaim(String sessionId, String claimTitle, String claimDescription);
	
	@WebMethod(operationName = "listClaimHistory")
	public String listClaimHistory(String sessionId);
	
	@WebMethod(operationName = "readClaim")
	public String readClaim(String sessionId, String claimId);

	@WebMethod(operationName = "authorizeDataAccess")
	public String authorizeDataAccess(String sessionId, String claimId, String decision);

	@WebMethod(operationName = "logout")
	public String logout(String sessionId);

	@WebMethod(operationName = "getAuthorizationInfo")
	boolean returnAuthorizationInfo(String sessionId, String claimId);
}
