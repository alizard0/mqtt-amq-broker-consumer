package com.alizard0;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import java.util.concurrent.TimeUnit;

public class Application {

  private static String TOPIC_NAME = "mqtt-topic";

  public static void main(final String[] args) throws Exception {
    BlockingConnection connection = connect();

    Topic[] topics = {
            new Topic(TOPIC_NAME, QoS.AT_LEAST_ONCE)
    };
    connection.subscribe(topics);

    // Consume messages
    while(true) {
      Message message = connection.receive(5, TimeUnit.SECONDS);
      System.out.println("Received: " + new String(message.getPayload()));
    }


  }

  private static BlockingConnection connect() throws Exception {
    MQTT mqtt = new MQTT();
    mqtt.setHost("tcp://localhost:1883");
    mqtt.setCleanSession(true);
    BlockingConnection connection = mqtt.blockingConnection();
    connection.connect();
    System.out.println("Connected!");
    return connection;
  }
}
