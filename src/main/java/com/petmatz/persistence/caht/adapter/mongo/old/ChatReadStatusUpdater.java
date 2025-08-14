//package com.petmatz.persistence.caht.adapter.mongo.old;
//
//import com.petmatz.persistence.caht.mongo.ChatReadStatusDocs;
//import com.petmatz.application.chat.support.ChatUtils;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
//@Component
//@RequiredArgsConstructor
//public class ChatReadStatusUpdater {
//
//    private final MongoTemplate mongoTemplate;
//
//
//    public void updateMessageStatusTime(String chatRoomId, String userEmail) {
//        Query query = makeQuery(chatRoomId, userEmail);
//        Update update = makeUpdate();
//        mongoTemplate.updateFirst(query, update, ChatReadStatusDocs.class);
//    }
//
//    private Query makeQuery(String chatRoomId, String userEmail) {
//        return new Query(Criteria.where("_id").is(ChatUtils.addString(chatRoomId,userEmail)).and("userEmail").is(userEmail));
//    }
//
//
//    private Update makeUpdate() {
//        return new Update()
//                .set("lastReadTimestamp", LocalDateTime.now());
//    }
//}
