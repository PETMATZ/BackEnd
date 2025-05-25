package com.petmatz.persistence.caht.adapter.mongo.support;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.time.LocalDateTime;

public class MakeQuery {

    public static Aggregation createQuerySelectChatMessagesPaging(String chatRoomsId, Long userId, LocalDateTime lastReadTimestamp, int pageNumber, int pageSize) {
        return Aggregation.newAggregation(
                Aggregation.match(Criteria.where("_id").is(chatRoomsId)), // 특정 채팅방 필터
                Aggregation.unwind("messages"), // 메시지 배열의 각 항목 분리
                Aggregation.match(
                        Criteria.where("messages.msgTimestamp").gt(lastReadTimestamp)
                                .orOperator(
                                        Criteria.where("messages.receiverId").is(userId) // 특정 사용자와 연관된 메시지
                                )
                ),
                Aggregation.count().as("messageCount") // 메시지 개수 계산
        );
    };

}
