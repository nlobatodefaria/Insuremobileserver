package pt.ulisboa.tecnico.sise.insure.ws.server.domain;

import java.util.ArrayList;

public class Customer {

	private String _username;
	private String _password;
	private CustomerItem _customerItem;
	private ArrayList<ClaimRecord> _claimList;
	
	public Customer(String login, String password, 
			CustomerItem customerItem, ArrayList<ClaimRecord> claimList) {
		_username = login;
		_password = password;
		_customerItem = customerItem;
		_claimList = claimList;
	}
	
	public String getUsername() {
		return _username;
	}
	
	public String getPassword() {
		return _password;
	}
	
	public CustomerItem getCustomerItem() {
		return _customerItem;
	}
	
	public ArrayList<ClaimRecord> getClaimList() {
		return _claimList;
	}
}
