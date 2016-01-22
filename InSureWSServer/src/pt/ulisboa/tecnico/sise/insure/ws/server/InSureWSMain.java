package pt.ulisboa.tecnico.sise.insure.ws.server;

import javax.xml.ws.Endpoint;

public class InSureWSMain {
	
	public static void main(String[] args) {

		Logger.logStartMessage();
		Endpoint.publish("http://194.210.232.115:7001/InSureWS", new InSureWSImpl());
		//Endpoint.publish("http://localhost:7001/InSureWS", new InSureWSImpl());
	}
}
