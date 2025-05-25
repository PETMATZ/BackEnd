package com.petmatz.persistence.caht.adapter.mongo;

import com.petmatz.application.chat.dto.ChatRoomMetaDataInfo;
import com.petmatz.application.chat.dto.IChatUserInfo;
import com.petmatz.domain.chat.ChatRoom;
import com.petmatz.domain.chat.port.ChatRoomDocsQueryPort;
import com.petmatz.persistence.caht.adapter.mongo.support.MakeQuery;
import com.petmatz.persistence.caht.mongo.ChatRoomMetadataDocs;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatRoomDocsQueryAdapter implements ChatRoomDocsQueryPort {

        private final MongoTemplate mongoTemplate;


        @Override
        public List<ChatRoomMetaDataInfo> findChatRoomMetaDataInfo(List<ChatRoom> chatRoomNumbers, Map<String, Integer> unreadCountList, Map<String, IChatUserInfo> userList) {
            List<String> chatNumberList = chatRoomNumbers.stream().map(ChatRoom::chatRoomIdToString).toList();
        Criteria criteria = Criteria.where("_id").in(chatNumberList);
        Query query = new Query(criteria);

        return mongoTemplate.find(query, ChatRoomMetadataDocs.class).stream()
                .map(chatRoomMetadataDocs -> {
                    int unreadCount = unreadCountList.getOrDefault(chatRoomMetadataDocs.getRoom_id(), 0);
                    IChatUserInfo iChatUserInfo = userList.getOrDefault(chatRoomMetadataDocs.getRoom_id(), null);
                    return ChatRoomMetaDataInfo.of(chatRoomMetadataDocs, 0, unreadCount, iChatUserInfo);
                })
                .collect(Collectors.toList());

    }

    /**
     * 채팅방 목록에서 각 채팅방에 대한 도착한 메세지 갯수를 조회한다.
     * @param chatRoomsId
     * @param userId
     * @param lastReadTimestamp
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public Integer countChatMessagesHistoryToLastDataAndUserName(String chatRoomsId, Long userId, LocalDateTime lastReadTimestamp, int pageNumber, int pageSize) {

        Aggregation query = MakeQuery.createQuerySelectChatMessagesPaging(chatRoomsId, userId, lastReadTimestamp, pageNumber, pageSize);

        AggregationResults<Document> aggregate =
                mongoTemplate.aggregate(query, "chat_rooms", Document.class);

        Document uniqueMappedResult = aggregate.getUniqueMappedResult();

        return uniqueMappedResult != null ? uniqueMappedResult.getInteger("messageCount") : 0;
    }

}
