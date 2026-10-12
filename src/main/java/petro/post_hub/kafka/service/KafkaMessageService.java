package petro.post_hub.kafka.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import petro.post_hub.kafka.MessageProducer;
import petro.post_hub.kafka.model.ActionType;
import petro.post_hub.kafka.model.PriorityType;
import petro.post_hub.kafka.model.UtilMessage;

@Service
@AllArgsConstructor
public class KafkaMessageService {
    private final MessageProducer messageProducer;

    public void sendPostCreatedLog(int postId, int userId) {
        UtilMessage utilMessage = UtilMessage.builder()
                .userId(userId)
                .message("user with id: " + userId + "create post with id: " + postId)
                .actionType(ActionType.CREATE)
                .priorityType(PriorityType.MEDIUM)
                .build();
        messageProducer.sendLog(utilMessage);
    }

    public void sendPostGetLog(int postId) {
        UtilMessage utilMessage = UtilMessage.builder()
                .message("user get post with id: " + postId)
                .actionType(ActionType.GET)
                .priorityType(PriorityType.LOW)
                .build();
        messageProducer.sendLog(utilMessage);
    }

    public void sendPostUpdateLog(int postId, int userId) {
        UtilMessage utilMessage = UtilMessage.builder()
                .userId(userId)
                .message("user with id: " + userId + "update post with id: " + postId)
                .actionType(ActionType.UPDATE)
                .priorityType(PriorityType.MEDIUM)
                .build();
        messageProducer.sendLog(utilMessage);
    }

    public void sendPostDeleteLog(int postId, int userId) {
        UtilMessage utilMessage = UtilMessage.builder()
                .userId(userId)
                .message("user with id: " + userId + "delete post with id: " + postId)
                .actionType(ActionType.DELETE)
                .priorityType(PriorityType.MEDIUM)
                .build();
        messageProducer.sendLog(utilMessage);
    }

    public void findPostByPaginationLog() {
        UtilMessage utilMessage = UtilMessage.builder()
                .message("user getAllPost with pagination")
                .actionType(ActionType.GET)
                .priorityType(PriorityType.LOW)
                .build();
        messageProducer.sendLog(utilMessage);
    }

    public void findPostByPaginationWithParamLog() {
        UtilMessage utilMessage = UtilMessage.builder()
                .message("user getAllPost with pagination and param")
                .actionType(ActionType.GET)
                .priorityType(PriorityType.LOW)
                .build();
        messageProducer.sendLog(utilMessage);
    }
}
