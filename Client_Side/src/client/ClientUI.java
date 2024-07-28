package client;

import controllers.ServerIpController;

import java.io.IOException;

import common.ChatIF;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI extends Application implements ChatIF {

    public static ChatClient chat; //only one instance
    public static String host;

    public static void main(String[] args) throws Exception {
        launch(args);  
    } 

    @Override
    public void start(Stage primaryStage) throws Exception {
        ServerIpController aFrame = new ServerIpController();
        aFrame.start(primaryStage);
    }

    @Override
    public void display(String message) {
        System.out.println("> " + message);
    }

    public static void initializeClient(String host, int port) throws IOException {
        chat = new ChatClient(host, port, new ClientUI());
    }
}

