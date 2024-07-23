package BMclient;

import BMgui.ClientConnectionController;
import javafx.application.Application;
import javafx.stage.Stage;

public class BMClientUI extends Application {
    public static BMClientController client;

    public static void main(String args[]) throws Exception {
        System.out.println("Launching client side");
        launch(args);
    }

    /**
     * Runs the client-side connection screen
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        ClientConnectionController clientConnectionController = new ClientConnectionController();
        clientConnectionController.start(primaryStage);
    }

    /**
     * @param host - host address of the server
     * @param port - port number of the server
     *
     * The method creates a client instance, only once
     */
    public static void createClient(String host, int port) {
        if (client == null) {
            client = new BMClientController(host, port);
        }
    }
}

