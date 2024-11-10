package net.davidesalerno.sample.todo_list.connector;

import net.davidesalerno.sample.todo_list.entity.Log;
import net.davidesalerno.sample.todo_list.repository.LogRepository;
import net.davidesalerno.sample.todo_list.utils.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class KakfkaListenerConnector {

    private final KafkaProducerConnector kafkaProducerConnector;
    private final LogRepository logRepository;
    Logger logger = LoggerFactory.getLogger(KakfkaListenerConnector.class);

    private static final Map<String, String> NEXT_TOPICS = Map.of(
            "log", "hop1",
            "hop1", "hop2",
            "hop2", "hop3",
            "hop3", "final"
    );

    public KakfkaListenerConnector(LogRepository logRepository, KafkaProducerConnector kafkaProducerConnector){
        this.logRepository = logRepository;
        this.kafkaProducerConnector = kafkaProducerConnector;
    }

    @KafkaListener(topics = {"log", "hop1", "hop2", "hop3", "final" }, groupId = "todo")
    public void listenEcho(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws ClassNotFoundException {
        logger.info("Received Message in group todo: " + message + " in topic " + topic);
        Log logMessage = null;
        try {
            logMessage = (Log) SerializationUtils.stringToObject(message);
            if(topic.equals("final")){
                logRepository.insert(logMessage);
            }else{
                String nextTopic = NEXT_TOPICS.get(topic);
                kafkaProducerConnector.sendMessage(nextTopic,message);
            }
        } catch (IOException e) {
            logger.warn("Unable to read message from Kafka due to:", e);
        }

    }
}
