package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class EchoAndEmailServer {
    public static void main(String[] args) {
        int port = 12345;  // Choose an available port

        // Initialize a list to keep track of connected clients
        List<String> connectedClients = new ArrayList<>();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running and listening on port " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    // Get client information and add it to the list of connected clients
                    String clientInfo = clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort();
                    connectedClients.add(clientInfo);

                    System.out.println("Client connected from: " + clientInfo);

                    // Create input and output streams for the client
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    String line;
                    while ((line = in.readLine()) != null) {
                        if (line.equalsIgnoreCase("QUIT")) {
                            out.println("Server: Goodbye!");
                            break;
                        } else if (line.startsWith("HELLO")) {
                            out.println("Server: Hello, " + line.substring(5) + "!");
                        } else if (line.equalsIgnoreCase("LIST")) {
                            String clientList = getClientList(connectedClients);
                            //out.println("Server: Connected Clients:\n" + clientList);
                            out.println(clientList);
                        } else {
                            out.println("Server: Error - Unrecognized command");
                        }
                    }

                    // Remove the client from the list when disconnected
                    connectedClients.remove(clientInfo);

                    System.out.println("Client disconnected.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to generate the client list
    public static String getClientList(List<String> clients) {
        StringBuilder list = new StringBuilder();
        for (String client : clients) {
            list.append(client).append("\n");
        }
        return list.toString();
    }
}