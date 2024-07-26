package client;
import javafx.application.Application;
import javafx.stage.Stage;
import controllers.ClientVerificationController;

public class ClientUI extends Application {
	
	public static ClientController chat; //only one instance
	public static BiteMeClient client;
	
	
	
//	receive orderID from startController and forward it to the server
	public static void requestOrderDetails(String orderNum) {
		chat.accept(orderNum);
	}
	
	public static void requestOrderUpdate(String newOrderData) {
		chat.accept(newOrderData);
	}
	
	public static void main( String args[] ) throws Exception
	   { 
		//launch start method 
		    launch(args);  
	   } 

	@Override
	public void start(Stage primaryStage) throws Exception {
		 //new GUI - start page
		ClientVerificationController aFrame = new ClientVerficiationController(); 
		aFrame.start(primaryStage);
	}
	
	public static void newConnection(String ip) {
		//create connection to server.
		 chat= new ClientController(ip, 5555);
	}


	
}
