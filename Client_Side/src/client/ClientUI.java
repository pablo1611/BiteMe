package client;

import common.ChatIF;
import controllers.ServerIpController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class ClientUI extends Application implements ChatIF {

	//public static ClientController chat; //only one instance
	public static ChatClient client;
	public static String host;

	
	public static void main( String args[] ) throws Exception
	   { 
		//launch start method 
		    launch(args);  
	   }

	@Override
	public void start(Stage primaryStage) throws Exception {
		ServerIpController aFrame = new ServerIpController();
		aFrame.start(primaryStage);
	}

	public static void initializeClient(String ip, int port) throws IOException {
        try {
            client = new ChatClient(ip, port, new ClientUI());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	public static ChatClient getClient() {
		return client;
	}

	@Override
	public void display(String message) {
		System.out.println("> " + message);
	}
}
