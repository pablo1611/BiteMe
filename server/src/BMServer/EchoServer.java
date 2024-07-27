package BMServer;

import common.RequestType;
import entities.Request;
import entities.User;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import serverGui.ServerGUIController;

import java.io.IOException;

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
        Request message;
        Request request = new Request();
        if (msg == null) {
            try {
                client.sendToClient(null);
            } catch (Exception e) {
            }
        }
        try {
            message = Request.fromBytesToObject((byte[]) msg);
            System.out.println("Received Request: " + message.toString());
            System.out.println("Request Type: " + message.getType().toString());

            switch (message.getType()) {
                case USER_LOGIN:
                    try {
                        User user = (User) message.getRequest();
                        System.out.println(user.toString());
                        request.setRequest(database.userLogin(user));
                        request.setType(RequestType.USER_LOGIN);
                        byte[] arr = request.getBytes();
                        System.out.println("Serialized bytes: " + arr.length);
                        client.sendToClient(arr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case USER_LOGOUT:
                    try {
                        User user = (User) message.getRequest();
                        System.out.println("Processing logout for user: " + user);
                        database.updateUserStatus(user.getUserName(), "Disconnected");

                        // Send confirmation back to the client
                        request.setType(RequestType.USER_LOGOUT);
                        request.setRequest(user);
                        byte[] arr = request.getBytes();
                        System.out.println("Serialized bytes: " + arr.length);
                        client.sendToClient(arr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
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
