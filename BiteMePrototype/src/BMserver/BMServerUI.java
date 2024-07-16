package BMserver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import BMlogic.Order;
import java.util.Vector;

public class BMServerUI extends Application {
    final public static int DEFAULT_PORT = 5555;
    public static Vector<Order> orders = new Vector<>();

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BMgui/serverConnection.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Server Port Configuration");
        primaryStage.show();
    }

    public static void runServer(String portStr) {
        int port = 0; // Port to listen on

        try {
            port = Integer.parseInt(portStr); // Set port
        } catch (Throwable t) {
            System.out.println("ERROR - Could not parse port number!");
            return;
        }

        BMServer server = new BMServer(port);

        try {
            server.listen(); // Start listening for connections
            System.out.println("Server started on port " + port);
        } catch (Exception ex) {
            System.out.println("ERROR - Could not listen for clients!");
        }
    }
}
