package com.petmatz.persistence.caht.adapter.mongo;

import com.petmatz.persistence.caht.mongo.ChatRoomMetadataDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatRoomMetaDataDeleter {

    private final MongoTemplate mongoTemplate;

    public void deleteChatRoomMetaDataDocs(String roomId) {
        Query query = new Query(Criteria.where("_id").is(roomId));
        mongoTemplate.remove(query, ChatRoomMetadataDocs.class);
    }
}
