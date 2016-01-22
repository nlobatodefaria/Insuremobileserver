package pt.ulisboa.tecnico.sise.insure.ws.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import pt.ulisboa.tecnico.sise.insure.ws.server.domain.ClaimRecord;
import pt.ulisboa.tecnico.sise.insure.ws.server.domain.ClaimItem;
import pt.ulisboa.tecnico.sise.insure.ws.server.domain.Customer;
import pt.ulisboa.tecnico.sise.insure.ws.server.domain.CustomerItem;

public class DataManager {

	private static DataManager _instance = null;

	private HashMap<Integer,String> _sessions;
	private int _sessionCounter;
	
	protected DataManager() {
		_sessions = new HashMap<Integer, String>();
		_sessionCounter = 1;
	}

	public static DataManager getInstance() {
		if(_instance == null) {
			_instance = new DataManager();
		}
		return _instance;
	}
	
	public int login(String username, String password) {
		
		ArrayList<Customer> customers = DataProvider.loadCustomers();
		
		int sessionId = 0;
		for(Iterator<Customer> i = customers.iterator(); i.hasNext();) {
			Customer c = i.next();
			if (c.getUsername().equals(username)) {
				if (c.getPassword().equals(password)) {
					sessionId = _sessionCounter;
					_sessionCounter++;
					_sessions.put(new Integer(sessionId), username);
				}
				break;
			}
		}
		return sessionId;
	}
	
	public CustomerItem listCustomerInfo(int sessionId) {
		
		String username = _sessions.get(new Integer(sessionId));
		if (username == null) {
			return null;
		}

		ArrayList<Customer> customers = DataProvider.loadCustomers();

		for(Iterator<Customer> i = customers.iterator(); i.hasNext();) {
			Customer c = i.next();
			if (c.getUsername().equals(username)) {
				return c.getCustomerItem();
			}
		}
		return null;
	}
	
	public boolean submitNewClaim(int sessionId, String claimTitle, String claimDescription) {
		
		String username = _sessions.get(new Integer(sessionId));
		if (username == null) {
			return false;
		}

		ArrayList<Customer> customers = DataProvider.loadCustomers();
		for(Iterator<Customer> i = customers.iterator(); i.hasNext();) {
			Customer c = i.next();
			if (c.getUsername().equals(username)) {
				
				ArrayList<ClaimRecord> claims = c.getClaimList();

                int claimId = 0;
				for (Iterator<ClaimRecord> j = claims.iterator(); j.hasNext();) {
					ClaimRecord r = j.next();
					if (r.getClaimId() > claimId) {
						claimId = r.getClaimId();
					}
				}
				claimId++;

                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date = new Date();
                String submissionDate = dateFormat.format(date);

                String status = "pending";
                
				claims.add(new ClaimRecord(claimId, submissionDate, claimTitle,
						claimDescription, status, false));
				DataProvider.storeCustomers(customers);
				return true;
			}
		}
		return false;
	}
	
	public ClaimItem [] listClaimHistory(int sessionId) {
		String username = _sessions.get(new Integer(sessionId));
		if (username == null) {
			return null;
		}

		ArrayList<Customer> customers = DataProvider.loadCustomers();
		for(Iterator<Customer> i = customers.iterator(); i.hasNext();) {
			Customer c = i.next();
			if (c.getUsername().equals(username)) {
				ArrayList<ClaimRecord> claims = c.getClaimList();
				ClaimItem[] items = new ClaimItem[claims.size()];
				for (int j = 0; j < items.length; j++) {
					ClaimRecord claim = claims.get(j);
					items[j] = new ClaimItem(claim.getClaimTitle(), claim.getClaimId());
				}
				return items;
			}
		}
		return null;
	}
	
	public ClaimRecord readClaim(int sessionId, int claimId) {
		String username = _sessions.get(new Integer(sessionId));
		if (username == null) {
			return null;
		}

		ArrayList<Customer> customers = DataProvider.loadCustomers();
		for(Iterator<Customer> i = customers.iterator(); i.hasNext();) {
			Customer c = i.next();
			if (c.getUsername().equals(username)) {
				ArrayList<ClaimRecord> claims = c.getClaimList();
				for (Iterator<ClaimRecord> j = claims.iterator(); j.hasNext();) {
					ClaimRecord r = j.next();
					if (r.getClaimId() == claimId) {
						return r;
					}
				}
				return null;
			}
		}
		return null;
	}
	
	public boolean authorizeDataAccess(int sessionId, int claimId, boolean decision) {
		String username = _sessions.get(sessionId);
		if (username == null) {
			return false;
		}

		ArrayList<Customer> customers = DataProvider.loadCustomers();
		for(Iterator<Customer> i = customers.iterator(); i.hasNext();) {
			Customer c = i.next();
			if (c.getUsername().equals(username)) {
				ArrayList<ClaimRecord> claims = c.getClaimList();
				for (Iterator<ClaimRecord> j = claims.iterator(); j.hasNext();) {
					ClaimRecord r = j.next();
					if (r.getClaimId() == claimId) {
						r.setAuthorized(decision);
						DataProvider.storeCustomers(customers);
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}
	
	public boolean logout(int sessionId) {
		if (_sessions.containsKey(sessionId)) {
			_sessions.remove(sessionId);
			return true;
		}		
		return false;
	}
	


	public boolean getAuthorizationInformation(String sessionId, String claimId) {
		String username = _sessions.get(Integer.parseInt(sessionId));
		if (username == null){
			return false;
		}
		ArrayList<Customer> customers = DataProvider.loadCustomers();
		for (Iterator<Customer> i = customers.iterator(); i.hasNext();){
			Customer c = i.next();
			if(c.getUsername().equals(username)){
				ArrayList<ClaimRecord> claims = c.getClaimList();
				for (Iterator<ClaimRecord> j = claims.iterator(); j.hasNext();){
					ClaimRecord r = j.next();
					if(r.getClaimId()== Integer.parseInt(claimId)){
						return r.getAuthorized();
					}
				}
				return false;
			}
		}
		return false;
	}
}
