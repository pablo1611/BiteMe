package BMServer;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import serverGui.ServerGUIController;

public class EchoServer extends AbstractServer {

    private static DBController database;

    public EchoServer(int port) {
        super(port);
        database = new DBController(ServerGUIController.hostName, ServerGUIController.schemaName, ServerGUIController.userName, ServerGUIController.password);
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        System.out.println("Message received: " + msg + " from " + client);
        try {
            client.sendToClient(msg);  // Echo the message back to the client
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void serverStarted() {
        System.out.println("Server started and listening on port " + getPort());
    }

    @Override
    protected void serverStopped() {
        System.out.println("Server stopped listening for connections.");
    }

    @Override
    protected void clientConnected(ConnectionToClient client) {
        String clientHost = client.getInetAddress().getHostName();
        String clientIP = client.getInetAddress().getHostAddress();
        System.out.println("Client connected: " + clientHost + " (" + clientIP + ")");
    }
}
