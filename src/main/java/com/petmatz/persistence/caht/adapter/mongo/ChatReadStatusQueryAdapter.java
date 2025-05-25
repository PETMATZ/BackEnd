package com.petmatz.persistence.caht.adapter.mongo;

import com.petmatz.application.chat.support.ChatUtils;
import com.petmatz.domain.chat.ChatReadStatus;
import com.petmatz.domain.chat.port.ChatReadStatusQueryPort;
import com.petmatz.persistence.caht.mapper.ChatDomainMapper;
import com.petmatz.persistence.caht.mongo.ChatReadStatusDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatReadStatusQueryAdapter implements ChatReadStatusQueryPort {

    private final MongoTemplate mongoTemplate;

    //chat_read_status의 자기 자신의 마지막 채팅 시간대를 조회
    @Override
    public ChatReadStatus selectChatMessageLastStatus(String chatRoomId, Long userId) {
        Query query = new Query(Criteria.where("_id").is(ChatUtils.addString(chatRoomId,userId)).and("userId").is(userId));
        ChatReadStatusDocs chatReadStatusDocs = mongoTemplate.findOne(query, ChatReadStatusDocs.class);
        return ChatDomainMapper.to(chatReadStatusDocs);
    }

}
