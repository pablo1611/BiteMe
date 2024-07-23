package controllers;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class BiteMeServer extends AbstractServer {

	public BiteMeServer(int port) {
		super(port);
		// TODO Auto-generated constructor stub
	}

	
	/**
	   * This method handles any messages received from the client.
	   *
	   * @param msg The message received from the client.
	   * @param client The connection from which the message originated.
	   */
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		// TODO Auto-generated method stub
		
	}

}
