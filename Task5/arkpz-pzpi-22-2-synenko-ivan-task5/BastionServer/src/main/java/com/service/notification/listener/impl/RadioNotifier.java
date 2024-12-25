package com.service.notification.listener.impl;


import com.config.RadioConfig;
import com.events.AlarmEvent;
import com.service.notification.listener.Notifier;

import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

public class RadioNotifier implements Notifier {

    private final RadioConfig config = RadioConfig.getInstance();

    @Override
    public void sendAlarmNotification(AlarmEvent event) {
        sendFileToServer(config.startAlarmFile, config.frequency);
    }

    @Override
    public void sendStopAlarmNotification() {
        sendFileToServer(config.stopAlarmFile, config.frequency);
    }

    private void sendFileToServer(String filename, float frequency) {
        try {
            URL url = new URL(config.URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // Build the JSON payload
            String payload = String.format("{\"audio_file\": \"%s\", \"frequency\" : \"%f\"}", filename, frequency);

            // Write payload to request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = payload.getBytes("utf-8");
                os.write(input, 0, input.length);
            } catch (ConnectException e) {
                System.out.println("Failed to send data to radio!");
                e.printStackTrace();
            }

            // Read response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Successfully sent " + filename + " to " + config.URL);
            } else {
                System.out.println("Failed to send " + filename + ". Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
