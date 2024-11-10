package net.davidesalerno.sample.todo_list.connector;

import net.davidesalerno.sample.todo_list.entity.Log;
import net.davidesalerno.sample.todo_list.utils.SerializationUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaProducerConnector {

    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerConnector(KafkaTemplate<String, String> kafkaTemplate ){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topicName, Log msg) throws IOException {
        String kafkaMessage = SerializationUtils.objectToString(msg);
        kafkaTemplate.send(topicName, kafkaMessage);
    }

    public void sendMessage(String topicName, String msg)  {
        kafkaTemplate.send(topicName, msg);
    }
}
