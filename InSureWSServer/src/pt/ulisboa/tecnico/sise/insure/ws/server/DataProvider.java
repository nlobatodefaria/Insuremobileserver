package pt.ulisboa.tecnico.sise.insure.ws.server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import pt.ulisboa.tecnico.sise.insure.ws.server.domain.ClaimRecord;
import pt.ulisboa.tecnico.sise.insure.ws.server.domain.Customer;
import pt.ulisboa.tecnico.sise.insure.ws.server.domain.CustomerItem;

public class DataProvider {

	private static final String DATAFILE = 
			"/Users/nunfaria/Documents/SISE/ECLIPSE WORKSPACE/InSureWS/InSureWSServer/data/insuredata.txt";

	/*
	 * The DATAFILE constant must be updated with the correct location of the
	 * file where the server keeps the JSON data persistent.
	 * 
	 * In Windows computers, to represent directory separators in file paths,
	 * use two back slashes, e.g.:
	 * 
	 *  DATAFILE = "c:\\Documents\\Test\\InSureWS\\InSureWSServer\\data\\insuredata.txt"
	 */

	private static final boolean MEMORYSOURCE = false;

	@SuppressWarnings("serial")
	public static ArrayList<Customer> initializeDummyData() {
		
		return new ArrayList<Customer>() {{
			
			// customer 1
			add(new Customer(
					"j",									// login account is j
					"j",									// account password is j
					new CustomerItem(
							"Joao Silva",
							1112222333,
							"Rua das Flores 21, Lisboa",
							"1-1-1960",
							1234),
					new ArrayList<ClaimRecord>() {{
						add(new ClaimRecord(
								1,
								"17-01-2016",
								"Flu",
								"Sudden fever, cough, headache, muscle pain, sore throat, and runny nose",
								"pending",
								false));
					}}));
			
			// customer 2
			add(new Customer(
					"m",									// login account is m
					"m",									// account password is m
					new CustomerItem(
							"Maria Albertina",
							202020202,
							"Av. da Liberdade 1, Lisboa",
							"1-1-1916",
							211),
					new ArrayList<ClaimRecord>() {{
						add(new ClaimRecord(
								1,
								"1-1-1996",
								"Back",
								"Back pain",
								"accepted",
								true));
						add(new ClaimRecord(
								2,
								"1-1-2006",
								"Nausea",
								"Stomach ache",
								"denied",
								true));
						add(new ClaimRecord(
								3,
								"1-1-2016",
								"Teeth",
								"Tooth pain",
								"pending",
								false));
					}}));
		}};
	}

	private static ArrayList<Customer> _customers = null;
	
	public static ArrayList<Customer> loadCustomersFromMemory () {
		if (_customers == null) {
			_customers = initializeDummyData();
		}
		return _customers;
	}

	public static void storeCustomersInMemory(ArrayList<Customer> customers) {
		_customers = customers;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Customer> loadCustomersFromFile() {
		
		JSONParser parser = new JSONParser();

		ArrayList<Customer> ret = null;
		try {
			
			JSONArray jsonCustomers = (JSONArray) parser.parse(new FileReader(DATAFILE));
			ArrayList<Customer> customers = new ArrayList<Customer>();
			for (Iterator<JSONObject> i = jsonCustomers.iterator(); i.hasNext(); ) {
				JSONObject jsonCustomer = i.next();
				
				String username = (String) jsonCustomer.get("username");
				String password = (String) jsonCustomer.get("password");
				
				JSONObject jsonCustomerItem = (JSONObject) jsonCustomer.get("customerItem");
				String customerName = (String) jsonCustomerItem.get("customerName");
				int fiscalNumber = (int) (long) jsonCustomerItem.get("fiscalNumber");
				String address = (String) jsonCustomerItem.get("address");
				String dateOfBirth = (String) jsonCustomerItem.get("dateOfBirth");
				int policyNumber = (int) (long) jsonCustomerItem.get("policyNumber");
				CustomerItem customerItem = new CustomerItem(customerName, fiscalNumber, address,
						dateOfBirth, policyNumber);

				JSONArray jsonClaimList = (JSONArray) jsonCustomer.get("claimList");
				ArrayList<ClaimRecord> claims = new ArrayList<ClaimRecord>();
				for (Iterator<JSONObject> j = jsonClaimList.iterator(); j.hasNext(); ) {
					JSONObject jsonClaimRecord = j.next();
					int claimId = (int) (long) jsonClaimRecord.get("claimId");
					String submissionDate = (String) jsonClaimRecord.get("submissionDate");
					String claimTitle = (String) jsonClaimRecord.get("claimTitle");
					String description = (String) jsonClaimRecord.get("description");
					String status = (String) jsonClaimRecord.get("status");
					boolean authorized = (boolean) jsonClaimRecord.get("authorized");
					ClaimRecord record = new ClaimRecord(claimId, submissionDate, claimTitle,
							description, status, authorized);
					claims.add(record);
				}
				
				Customer customer = new Customer(username, password, customerItem, claims);
				customers.add(customer);
			}

			ret = customers;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static void storeCustomersInFile(ArrayList<Customer> customers) {

		if (customers == null) return;
		
		JSONArray jsonCustomers = new JSONArray();
		for(Iterator<Customer> i = customers.iterator(); i.hasNext();) {
			Customer c = i.next();
			
			JSONArray jsonClaimList = new JSONArray();
			ArrayList<ClaimRecord> claims = c.getClaimList();
			for (Iterator<ClaimRecord> j = claims.iterator(); j.hasNext();) {
				ClaimRecord record = j.next();
				JSONObject jsonClaimRecord = new JSONObject();
				jsonClaimRecord.put("claimId", record.getClaimId());
				jsonClaimRecord.put("submissionDate", record.getSubmissionDate());
				jsonClaimRecord.put("claimTitle", record.getClaimTitle());
				jsonClaimRecord.put("description", record.getDescription());
				jsonClaimRecord.put("status", record.getStatus());
				jsonClaimRecord.put("authorized", record.getAuthorized());
				jsonClaimList.add(jsonClaimRecord);
			}			

			CustomerItem item = c.getCustomerItem();
			JSONObject jsonCustomerItem = new JSONObject();
			jsonCustomerItem.put("customerName", item.getCustomerName());
			jsonCustomerItem.put("fiscalNumber", item.getFiscalNumber());
			jsonCustomerItem.put("address", item.getAddress());
			jsonCustomerItem.put("dateOfBirth", item.getDateOfBirth());
			jsonCustomerItem.put("policyNumber", item.getPolicyNumber());

			JSONObject jsonCustomer = new JSONObject();
			jsonCustomer.put("username", c.getUsername());
			jsonCustomer.put("password", c.getPassword());
			jsonCustomer.put("customerItem", jsonCustomerItem);
			jsonCustomer.put("claimList", jsonClaimList);
			jsonCustomers.add(jsonCustomer);
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(jsonCustomers.toJSONString());
		String prettyJsonString = gson.toJson(je);

		try {			
			FileWriter file = new FileWriter(DATAFILE);
			file.write(prettyJsonString);
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Customer> loadCustomers () {
		if (MEMORYSOURCE) {
			return loadCustomersFromMemory();
		} else {
			return loadCustomersFromFile();
		}
	}
	
	public static void storeCustomers(ArrayList<Customer> customers) {
		if (MEMORYSOURCE) {
			storeCustomersInMemory(customers);
		} else {
			storeCustomersInFile(customers);
		}
	}
}
