package com.apogee_research.chat_client;

import java.io.*;
import java.net.*;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("test");

        boolean isHost = false;
        String portString = null;
        String username = null;

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "--host":
                    isHost = true;
                    break;
                case "-p":
                    portString = args[i + 1];
                    i++;
                    break;
                case "-u":
                    username = args[i + 1];
                    i++;
                    break;
                default:
                    printUsageAndExit();
            }
        }

        if (portString == null || username == null) {
            printUsageAndExit();
        } else {
            int port = Integer.parseInt(portString);
            if (isHost) {
                runServer(port, username);
            } else {
                runClient("localhost", port, username);
            }
        }
    }

    private static void printUsageAndExit() {
        System.err.println("Usage: chat-client [--host] -p <port> -u <username>");
        System.exit(1);
    }

    private static void runClient(String hostName, int port, String username) throws IOException {
        try (
                Socket socket = new Socket(hostName, port);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            ChatClient chatClient = new ChatClient(in, out, username);
            chatClient.run();
        }
    }

    private static void runServer(int port, String username) throws IOException {
        try (
                ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            ChatClient chatClient = new ChatClient(in, out, username);
            chatClient.run();
        }
    }

}
