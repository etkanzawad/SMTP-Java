package server;

import java.io.*;
import java.net.*;

    public class EchoAndEmailServer {
        public static void main(String[] args) {
            int port = 12345;  // Choose an available port

            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Server is running and listening on port " + port);

                while (true) {
                    try (Socket clientSocket = serverSocket.accept()) {
                        System.out.println("Client connected from: " + clientSocket.getInetAddress());

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
                            } else {
                                out.println("Server: Error - Unrecognized command");
                            }
                        }

                        System.out.println("Client disconnected.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

