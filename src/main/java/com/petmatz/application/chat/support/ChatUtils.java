package com.petmatz.application.chat.support;

import java.util.concurrent.atomic.AtomicReference;

public class ChatUtils {

    public static String addString(String chatRoomID, Long userEmail) {
        AtomicReference<StringBuilder> stringBuilder = new AtomicReference<>(new StringBuilder());
        stringBuilder.get().append(chatRoomID);
        stringBuilder.get().append("_");
        stringBuilder.get().append(userEmail);
        return stringBuilder.toString();
    }

}
