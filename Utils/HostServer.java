/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class HostServer {
    public static void main(String[] args) {
        // TODO code application logic here
        HostServer.createServer();
    }
    private static int PORT = 12345;
    private static List<Socket> clientSockets = new ArrayList<>();

    public static void createServer() {
        System.out.println("Server is running...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientSockets.add(clientSocket);
                System.out.println("New client connected!");
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String message;
            while ((message = reader.readLine()) != null) {
                broadcastMessage(message, clientSocket);
            }
        } catch (IOException e) {
            System.out.println("A client disconnected.");
        } finally {
            clientSockets.remove(clientSocket);
        }
    }

    private static void broadcastMessage(String message, Socket senderSocket) {
        for (Socket socket : clientSockets) {
            if (socket != senderSocket) {
                try {
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                    writer.println(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error sending message.");
                }
            }
        }
    } 
}
