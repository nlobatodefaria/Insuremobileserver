package pt.ulisboa.tecnico.sise.insure.ws.server;

import java.sql.Timestamp;

public class Logger {

	public static String getTimestamp() {
		java.util.Date date= new java.util.Date();
		return (new Timestamp(date.getTime())).toString();
	}
	
	public static void print(String msg) {
		System.out.println(msg);
	}
	
	public static void logRequest(String desc) {
		System.out.println("[" + getTimestamp() + "] Incoming request => " + desc + ".");
	}

	public static void logResponse(String desc) {
		System.out.println("[" + getTimestamp() + "] Outgoing response => " + desc + ".");
	}
	
	public static void logStartMessage() {
		System.out.println("[" + getTimestamp() + "] InSure Web Service started.");
	}
}
