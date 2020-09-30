package com.apogee_research.chat_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ChatClient {
    private final BufferedReader clientIn;
    private final PrintWriter clientOut;

    public ChatClient(BufferedReader clientIn, PrintWriter clientOut) {
        this.clientIn = clientIn;
        this.clientOut = clientOut;
    }

    public void run() throws IOException {
        StdInThread thread = new StdInThread(this::processStdIn);
        thread.start();

        clientOut.println("A user has joined the chat");

        String receivedMessage;
        while ((receivedMessage = clientIn.readLine()) != null) {
            System.out.println(receivedMessage);
        }
    }

    private void processStdIn(String stdInLine) {
        clientOut.println(stdInLine);
    }
}
