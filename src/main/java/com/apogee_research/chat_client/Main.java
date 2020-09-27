package com.apogee_research.chat_client;

import java.io.*;
import java.net.*;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("test");

        if (args.length == 1) {
            int port = Integer.parseInt(args[0]);
            runClient("localhost", port);
        } else if (args.length == 2 && args[0].equals("--host")) {
            int port = Integer.parseInt(args[1]);
            runServer(port);
        } else {
            System.err.println("Usage: chat-client [--host] <port>");
            System.exit(1);
        }
    }

    private static void runClient(String hostName, int port) throws IOException {
        try (
                Socket socket = new Socket(hostName, port);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            ChatClient chatClient = new ChatClient(in, out);
            chatClient.run();
        }
    }

    private static void runServer(int port) throws IOException {
        try (
                ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            ChatClient chatClient = new ChatClient(in, out);
            chatClient.run();
        }
    }

}
