#include <WiFi.h>
#include <PubSubClient.h>
#include <WiFiClientSecure.h>
#include <Adafruit_Fingerprint.h>

// Configurações do Sensor de Digitais
const uint32_t password = 0x0; 
Adafruit_Fingerprint fingerprintSensor = Adafruit_Fingerprint(&Serial2, password);

// Pinos dos LEDs
#define LED_RED 12
#define LED_GREEN 13
#define LED_BLUE 14

// Configurações de Wi-Fi
const char *ssid = "SSID";
const char *wifi_password = "SENHA";

// Configurações do Broker MQTT
const char *mqtt_server = "HOST_HIVEMQ_CLOUD";
const int mqtt_port = 8883;
const char *mqtt_user = "USUARIO";
const char *mqtt_password = "SENHA";

// Certificado CA
const char *ca_cert = R"EOF(
-----BEGIN CERTIFICATE-----
CERTIFICADO_CA
-----END CERTIFICATE-----
)EOF";

// Tópicos MQTT
const char *topic_publish = "topicOut";
const char *topic_subscribe = "topicIn";

// Instâncias de Wi-Fi e MQTT
WiFiClientSecure espClient;
PubSubClient client(espClient);

// Códigos de erro
const char* ERROR_INVALID_POSITION = "4";
const char* ERROR_PROCESS_IMAGE = "7";
const char* ERROR_SAVE_FINGERPRINT = "8";
const char* ERROR_DELETE_FINGERPRINT = "9";
const char* ERROR_FINGERPRINT_NOT_FOUND = "10";
const char* ERROR_SENSOR_NOT_WORKING = "5";

// Callback para mensagens MQTT recebidas
void mqttCallback(char *topic, byte *payload, unsigned int length) {
    String message = String((char*)payload).substring(0, length);
    if (message.length() > 1) {
        int command = (int)message[0]; // Primeiro caractere: comando
        int position = message.substring(1).toInt(); // Restante: posição
        
        if (position < 1 || position > 127) {
            client.publish(topic_publish, ERROR_INVALID_POSITION);
            return;
        }

        switch (command) {
            case 1: storeFingerprint(position); break; // Cadastro de digital
            case 2: checkFingerprint(); break; // Verificar digital
            case 3: deleteFingerprint(position); break; // Apagar digital
            default: client.publish(topic_publish, "2"); // Comando inválido
        }
    } else {
        client.publish(topic_publish, "3"); // Mensagem malformada
    }
}

// Configura o Wi-Fi
void setup_wifi() {
    WiFi.begin(ssid, wifi_password);
    while (WiFi.status() != WL_CONNECTED) {
        pisca(LED_BLUE);
        delay(500);
    }
    pisca(LED_GREEN);
}

// Reconexão MQTT
void reconnect() {
    while (!client.connected()) {
        if (client.connect("ESP32FingerprintClient", mqtt_user, mqtt_password)) {
            client.subscribe(topic_subscribe);
            pisca(LED_GREEN);
        } else {
            pisca(LED_RED);
            delay(2000); // Atraso antes de tentar reconectar
        }
    }
 }

// Aguarda a leitura da digital
bool waitForFingerprint() {
    while (fingerprintSensor.getImage() != FINGERPRINT_OK) {
        delay(100);
    }
    return true;
}

// Cadastro de digital
void storeFingerprint(int position) {
    if (!waitForFingerprint() || fingerprintSensor.image2Tz(1) != FINGERPRINT_OK) {
        client.publish(topic_publish, ERROR_PROCESS_IMAGE);
        return;
    }

    delay(2000); // Aguarda um momento antes de remover o dedo

    while (fingerprintSensor.getImage() == FINGERPRINT_NOFINGER) {
        delay(100);
    }

    if (!waitForFingerprint() || fingerprintSensor.image2Tz(2) != FINGERPRINT_OK || fingerprintSensor.createModel() != FINGERPRINT_OK) {
        client.publish(topic_publish, ERROR_PROCESS_IMAGE);
        return;
    }

    if (fingerprintSensor.storeModel(position) == FINGERPRINT_OK) {
        client.publish(topic_publish, "100"); // Sucesso
    } else {
        client.publish(topic_publish, ERROR_SAVE_FINGERPRINT);
    }
}

// Apagar digital
void deleteFingerprint(int position) {
    if (fingerprintSensor.deleteModel(position) == FINGERPRINT_OK) {
        client.publish(topic_publish, "101"); // Sucesso na exclusão
    } else {
        client.publish(topic_publish, ERROR_DELETE_FINGERPRINT);
    }
}

// Verificar digital
void checkFingerprint() {
    if (waitForFingerprint() && fingerprintSensor.image2Tz() == FINGERPRINT_OK && fingerprintSensor.fingerFastSearch() == FINGERPRINT_OK) {
        String fingerID = String(fingerprintSensor.fingerID);
        client.publish(topic_publish, fingerID.c_str());
    } else {
        client.publish(topic_publish, ERROR_FINGERPRINT_NOT_FOUND);
    }
}

// Pisca LEDs
void pisca(int pin) {
    for (int i = 0; i < 3; i++) {
        digitalWrite(pin, LOW);
        delay(200);
        digitalWrite(pin, HIGH);
        delay(200);
    }
}

void setup() {
    Serial.begin(57600);

    pinMode(LED_RED, OUTPUT);
    pinMode(LED_GREEN, OUTPUT);
    pinMode(LED_BLUE, OUTPUT);

    setup_wifi();

    espClient.setCACert(ca_cert);
    client.setServer(mqtt_server, mqtt_port);
    client.setCallback(mqttCallback);

    // Verifica se o sensor está funcionando
    if (!fingerprintSensor.verifyPassword()) {
        client.publish(topic_publish, ERROR_SENSOR_NOT_WORKING);
    }

    reconnect();
}

void loop() {
    if (!client.connected()) {
        reconnect();
    }
    client.loop();
} 
