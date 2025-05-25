//package com.petmatz.persistence.caht.adapter.mongo.old;
//
//
//import com.petmatz.persistence.caht.mongo.ChatReadStatusDocs;
//import com.petmatz.application.chat.support.ChatUtils;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class ChatReadStatusReader {
//
//    private final MongoTemplate mongoTemplate;
//
//    //chat_read_status의 자기 자신의 마지막 채팅 시간대를 조회
//    public ChatReadStatusDocs selectChatMessageLastStatus(String chatRoomId, String userEmail) {
//        Query query = new Query(Criteria.where("_id").is(ChatUtils.addString(chatRoomId,userEmail)).and("userEmail").is(userEmail));
//        return mongoTemplate.findOne(query, ChatReadStatusDocs.class);
//    }
//}
