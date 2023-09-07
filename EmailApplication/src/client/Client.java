package client;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String serverHost = "127.0.0.1";  // Replace with the server's IP address
        int serverPort = 12345;          // Replace with the server's port

        try (Socket socket = new Socket(serverHost, serverPort);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to the server.");

            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;

            while (true) {
                System.out.print("Enter a message (QUIT to exit): ");
                userInput = consoleIn.readLine();

                if (userInput == null || userInput.equalsIgnoreCase("QUIT")) {
                    break;
                }

                out.println(userInput);
                String response = in.readLine();
                System.out.println("Server response: " + response);
            }

            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
