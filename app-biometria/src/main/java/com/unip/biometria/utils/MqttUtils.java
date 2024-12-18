package com.unip.biometria.utils;

import org.eclipse.paho.client.mqttv3.*;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.util.concurrent.CompletableFuture;

public class MqttUtils {

    private static final String BROKER_URL = "ssl://HOST_HIVEMQ_CLOUD:8883"; // URL do broker com SSL
    private static final String CLIENT_ID = "JavaClient";
    private static final String USERNAME = "USUARIO";
    private static final String PASSWORD = "SENHA";
    private static final String TOPIC_OUT = "topicOut";
    private static final String TOPIC_IN = "topicIn";

    private MqttClient client;

    public MqttUtils() throws MqttException {
        this.client = new MqttClient(BROKER_URL, CLIENT_ID);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setSocketFactory(getSocketFactory()); // Habilitar TLS
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());
        options.setCleanSession(true);

        
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.err.println("Conexão perdida: " + cause.getMessage());
                reconnect(); // Tentar reconectar em caso de falha
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                System.out.println("Mensagem recebida do tópico " + topic + ": " + new String(message.getPayload()));
                handleMessage(topic, new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("Mensagem entregue com sucesso!");
            }
        });

        client.connect(options);
        client.subscribe(TOPIC_OUT);
        System.out.println("Conectado ao broker MQTT!");
    }

    private SocketFactory getSocketFactory() {
        try {
            return SSLSocketFactory.getDefault(); // Utiliza o certificado CA do sistema
        } catch (Exception e) {
            throw new RuntimeException("Erro ao configurar o TLS", e);
        }
    }

    private void reconnect() {
        new Thread(() -> {
            while (!client.isConnected()) {
                try {
                    System.out.println("Tentando reconectar...");
                    client.reconnect();
                } catch (MqttException e) {
                    System.err.println("Erro ao reconectar: " + e.getMessage());
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }).start();
    }

    public CompletableFuture<Void> sendCommand(String command) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        try {
            MqttMessage message = new MqttMessage(command.getBytes());
            client.publish(TOPIC_IN, message);
            future.complete(null);
        } catch (MqttException e) {
            future.completeExceptionally(e);
        }
        return future;
    }

    private int handleMessage(String topic, String payload) {
        switch (payload) {
            case "100":
                System.out.println("Digital cadastrada com sucesso!");
                return 1;
            case "101":
                System.out.println("Digital excluída com sucesso!");
                return 1;
            case "5":
                System.out.println("Sem posições disponíveis.");
                return 2;
            case "8":
                System.out.println("Erro ao salvar digital.");
                return 3;
            default:
                System.out.println("Resposta desconhecida: " + payload);
                return 0;
        }
    }
}
