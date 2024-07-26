package BMServer;

import javafx.application.Application;
import javafx.stage.Stage;
import serverGui.ServerGUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ServerUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/serverGui/ServerGUI.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/serverGui/ServerGUI.css").toExternalForm());
        primaryStage.setTitle("ServerUI");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void runServer(String port) {
        int portNumber = 5555; // Default port
        try {
            portNumber = Integer.parseInt(port);
        } catch (Throwable t) {
            System.out.println("ERROR - Could not connect!");
        }

        EchoServer sv = new EchoServer(portNumber);

        try {
            sv.listen(); // Start listening for connections
        } catch (Exception ex) {
            System.out.println("ERROR - Could not listen for clients!");
        }
    }
}
