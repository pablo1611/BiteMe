package controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//import common.MyFile;
import ocsf.client.AbstractClient;

public class BiteMeClient extends AbstractClient {

	//constructor
	public BiteMeClient(String host, int port) {
		super(host, port);
		// TODO Auto-generated constructor stub
	}

	/**
	 * uses the openConnection method of AbstractClient that tries to connect a client,
	 * will success if server is listening
	 * @param host is the server's host address
	 * @param port is the server's port number
	 * @return true if the connection succeed and false if not
	 */
	public boolean connectClientToServer(String host, int port) {
		setPort(port);
		setHost(host);
		try {
			openConnection(); // trying to connect to server
		} catch (IOException e) { // if connection failed
			return false;
		}
		return true; // if connection succeed
	}
	
	 /**
	   * This method handles all data that comes in from the server.
	   *
	   * @param msg The message from the server.
	   */
	@Override
	protected void handleMessageFromServer(Object msg) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	 /**
	   * This method handles all data coming from the UI            
	   *
	   * @param message The message from the UI.    
	   */
	  public void handleMessageFromClientUI(String message)
	  {  
		  
	  }
	
	

}
