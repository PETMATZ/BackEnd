package com.petmatz.domain.old.chatting.component;

import com.petmatz.domain.old.chatting.docs.ChatReadStatusDocs;
import com.petmatz.domain.old.chatting.utils.ChatUtils;
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
