package com.petmatz.domain.old.chatting.utils;

import java.util.concurrent.atomic.AtomicReference;

public class ChatUtils {

    public static String addString(String chatRoomID, String userEmail) {
        AtomicReference<StringBuilder> stringBuilder = new AtomicReference<>(new StringBuilder());
        stringBuilder.get().append(chatRoomID);
        stringBuilder.get().append("_");
        stringBuilder.get().append(userEmail);
        return stringBuilder.toString();
    }

}
