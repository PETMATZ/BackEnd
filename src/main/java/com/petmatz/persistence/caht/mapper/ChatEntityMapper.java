package com.petmatz.persistence.caht.mapper;

import com.petmatz.persistence.caht.entity.ChatRoomEntity;

public class ChatEntityMapper {

    public static ChatRoomEntity to(Object caregiverId, Object entrustedId) {
        return new ChatRoomEntity(convertToLong(caregiverId), convertToLong(entrustedId));
    }

    private static Long convertToLong(Object value) {
        if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof String) {
            return Long.parseLong((String) value);
        } else {
            throw new IllegalArgumentException("지원하지 않는 타입 입니다. : " + value.getClass());
        }
    }
}
