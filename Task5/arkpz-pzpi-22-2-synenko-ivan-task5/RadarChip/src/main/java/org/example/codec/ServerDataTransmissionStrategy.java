package org.example.codec;

import org.example.model.server.SkyObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static java.lang.Thread.sleep;

public interface ServerDataTransmissionStrategy {

    //The server prototype accepted data as a JSON, that's why it's by
    //default in a JSON format. New class may overwrite this.
    int MAX_RETRIES = 3;  // Maximum number of retries
    long RETRY_DELAY_MS = 5000; // Delay between retries in milliseconds

    // Encodes a list of SkyObjects into JSON format
    default Object encode(List<SkyObject> data) {
        StringBuilder result = new StringBuilder("[");
        data.forEach(obj -> result.append(obj.toJson()).append(","));
        result.deleteCharAt(result.length() - 1);
        return result.append("]").toString();
    }

    // Sends data to the server with retry logic
    default void sendToServer(Object data, String serverUrl) {
        int attempts = 0;

        while (attempts < MAX_RETRIES) {
            try {
                attempts++;
                System.out.println("Attempt " + attempts + " to send data to server.");

                // Open connection to server
                URL url = new URL(serverUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");

                // Send data
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(((String) data).getBytes());
                    os.flush();
                }

                // Check response code
                int responseCode = connection.getResponseCode();
                System.out.println("Server response: " + responseCode);

                if (responseCode >= 200 && responseCode < 300) {
                    // Successful request; exit retry loop
                    break;
                } else {
                    System.err.println("Unexpected server response: " + responseCode);
                }
            } catch (IOException e) {
                System.err.println("Failed to send data: " + e.getMessage());
            }

            // Wait before retrying
            if (attempts < MAX_RETRIES) {
                try {
                    System.out.println("Retrying in " + RETRY_DELAY_MS / 1000 + " seconds...");
                    Thread.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException e) {
                    System.err.println("Retry delay interrupted: " + e.getMessage());
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        if (attempts == MAX_RETRIES) {
            System.err.println("All attempts to send data to server failed. Abandoning request.");
        }
    }
}
