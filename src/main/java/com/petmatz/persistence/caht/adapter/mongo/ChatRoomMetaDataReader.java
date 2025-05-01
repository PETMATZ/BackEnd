//package com.petmatz.persistence.caht.adapter.mongo;
//
//import com.petmatz.persistence.caht.mongo.ChatRoomMetadataDocs;
//import com.petmatz.application.chat.dto.ChatRoomMetaDataInfo;
//import com.petmatz.application.chat.dto.IChatUserInfo;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//
//@Component
//@RequiredArgsConstructor
//public class ChatRoomMetaDataReader {
//
//    private final MongoTemplate mongoTemplate;
//
//    //자기자신이 속한 채팅방의 메타데이터를 전부 조회 및 해당 채팅방의 안읽은 메세지 갯수를 업데이트해서 반환
//    public List<ChatRoomMetaDataInfo> findChatRoomMetaDataInfo(List<Long> chatRoomNumbers, Map<String, Integer> unreadCountList, Map<String, IChatUserInfo> userList) {
//        List<String> chatNumberList = chatRoomNumbers.stream().map(Object::toString).toList();
//        Criteria criteria = Criteria.where("_id").in(chatNumberList);
//        Query query = new Query(criteria);
//
//        return mongoTemplate.find(query, ChatRoomMetadataDocs.class).stream()
//                .map(chatRoomMetadataDocs -> {
//                    int unreadCount = unreadCountList.getOrDefault(chatRoomMetadataDocs.getRoom_id(), 0);
//                    IChatUserInfo iChatUserInfo = userList.getOrDefault(chatRoomMetadataDocs.getRoom_id(), null);
//                    return ChatRoomMetaDataInfo.of(chatRoomMetadataDocs, 0, unreadCount, iChatUserInfo);
//                })
//                .collect(Collectors.toList());
//
//    }
//}
