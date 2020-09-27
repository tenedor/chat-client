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
            StdInThread thread = new StdInThread(out);
            thread.start();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
        }
    }

    private static void runServer(int port) throws IOException {
        try (
                ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            StdInThread thread = new StdInThread(out);
            thread.start();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
        }
    }

    private static class StdInThread extends Thread {

        private final PrintWriter out;

        public StdInThread(PrintWriter out) {
            this.out = out;
        }

        @Override
        public void run() {
            try (
                    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
            ) {
                String userInput;
                while ((userInput = stdIn.readLine()) != null) {
                    out.println(userInput);
                }
            } catch (IOException e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }
}
