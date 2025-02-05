package com.microserviciologistic.createuser.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microserviciologistic.createuser.config.PlainWebSocketClient;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class WebSocketClientService {

    private static final String WEBSOCKET_URL = "ws://34.224.173.116:5001/ws";
    private PlainWebSocketClient webSocketClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        connectWebSocket();
    }

    // Synchronized method to avoid race conditions on reconnection
    private synchronized void connectWebSocket() {
        try {
            System.out.println("Trying to connect to WebSocket on: " + WEBSOCKET_URL);
            webSocketClient = new PlainWebSocketClient(new URI(WEBSOCKET_URL));
        } catch (Exception e) {
            System.err.println("Error to connect to WebSocket: " + e.getMessage());
            scheduleReconnect();
        }
    }

    // Recconection after 5 seconds
    private void scheduleReconnect() {
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("Reconnection to WebSocket...");
                connectWebSocket();
            } catch (InterruptedException e) {
                System.err.println("Error of reconnection : " + e.getMessage());
            }
        }).start();
    }

    public void sendEvent(String operation, Object data) {
        if (webSocketClient == null || !webSocketClient.isConnected()) {
            System.err.println("WebSocket is not connected. Trying reconnect...");
            connectWebSocket();
            return;
        }
        try {
            String jsonMessage = objectMapper.writeValueAsString(new WebSocketMessage(operation, data));
            webSocketClient.sendMessage(jsonMessage);
        } catch (Exception e) {
            System.err.println("Error to sent message: " + e.getMessage());
        }
    }

    // Inner class to structure the message to be sent
    private static class WebSocketMessage {
        private String operation;
        private Object user;

        public WebSocketMessage(String operation, Object user) {
            this.operation = operation;
            this.user = user;
        }

        public String getOperation() {
            return operation;
        }

        public Object getUser() {
            return user;
        }
    }
}
