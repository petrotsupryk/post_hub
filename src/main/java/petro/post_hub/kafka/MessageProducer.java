package petro.post_hub.kafka;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import petro.post_hub.kafka.model.Service;
import petro.post_hub.kafka.model.UtilMessage;
import tools.jackson.databind.json.JsonMapper;

@Component
@RequiredArgsConstructor

public class MessageProducer {
    private final JsonMapper jsonMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${aditional.kafka.topic}")
    private String topic;
    @Value("${kafka.enabled}")
    private boolean isEnabled;

    public void sendLog(@NotNull UtilMessage utilMessage) {
        if (!isEnabled) {
            return;
        }
        try {
            utilMessage.setService(Service.POST_SERVICE);
            String json = jsonMapper.writeValueAsString(utilMessage);
            kafkaTemplate.send(topic, json).get();
        } catch (Exception e) {

        }

    }


}
