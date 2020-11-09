package com.apogee_research.chat_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StdInThread extends Thread {

    private final InputConsumer consumer;

    public StdInThread(InputConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void run() {
        try (
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                consumer.accept(userInput);
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }
}
