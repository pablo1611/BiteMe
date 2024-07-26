package client;

import controllers.ServerIpController;
import common.ChatIF;
import javafx.application.Application;
import javafx.stage.Stage;



public class ClientUI extends Application implements ChatIF {

	//public static ClientController chat; //only one instance
	public static ChatClient chat;
	public static String host;

	
	public static void main( String args[] ) throws Exception
	   { 
		//launch start method 
		    launch(args);  
	   } 

	@Override
	public void start(Stage primaryStage) throws Exception {
		 //new GUI - start page
		ServerIpController aFrame = new ServerIpController();
		aFrame.start(primaryStage);
	}

	@Override
	public void display(String message) {
		System.out.println("> " + message);
	}
}
