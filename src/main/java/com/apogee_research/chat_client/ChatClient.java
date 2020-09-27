package com.apogee_research.chat_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ChatClient {
    private final BufferedReader clientIn;
    private final PrintWriter clientOut;
    private final String username;

    public ChatClient(BufferedReader clientIn, PrintWriter clientOut, String username) {
        this.clientIn = clientIn;
        this.clientOut = clientOut;
        this.username = username;
    }

    public void run() throws IOException {
        StdInThread thread = new StdInThread(this::processStdIn);
        thread.start();

        clientOut.printf("%s has joined the chat\n", username);

        String receivedMessage;
        while ((receivedMessage = clientIn.readLine()) != null) {
            System.out.println(receivedMessage);
        }
    }

    private void processStdIn(String stdInLine) {
        clientOut.printf("%s: %s\n", username, stdInLine);
    }
}
