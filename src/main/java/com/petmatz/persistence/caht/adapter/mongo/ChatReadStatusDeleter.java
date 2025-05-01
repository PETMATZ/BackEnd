package com.petmatz.persistence.caht.adapter.mongo;

import com.petmatz.persistence.caht.mongo.ChatReadStatusDocs;
import com.petmatz.application.chat.support.ChatUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatReadStatusDeleter {
    private final MongoTemplate mongoTemplate;

    public void deleteChatReadStatusDocs(List<String> userEmail, String roomId) {
        for (String useremail : userEmail) {
            Query query = new Query(Criteria.where("_id").is(ChatUtils.addString(roomId, useremail)));
            mongoTemplate.remove(query, ChatReadStatusDocs.class);
        }
    }
}
