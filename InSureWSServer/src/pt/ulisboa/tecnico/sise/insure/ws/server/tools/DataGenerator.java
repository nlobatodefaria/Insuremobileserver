package pt.ulisboa.tecnico.sise.insure.ws.server.tools;

import java.util.ArrayList;
import pt.ulisboa.tecnico.sise.insure.ws.server.domain.Customer;
import pt.ulisboa.tecnico.sise.insure.ws.server.DataProvider;

public class DataGenerator {

	public static void main(String[] args) {

		DataProvider.storeCustomersInFile(DataProvider.initializeDummyData());
		ArrayList<Customer> customers = DataProvider.loadCustomersFromFile();
		DataProvider.storeCustomersInFile(customers);
	}
}
