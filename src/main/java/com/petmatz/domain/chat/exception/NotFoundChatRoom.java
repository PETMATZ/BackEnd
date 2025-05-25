package com.petmatz.domain.chat.exception;

import com.petmatz.common.exception.DomainException;

public class NotFoundChatRoom extends DomainException {
    public static final DomainException EXCEPTION = new NotFoundChatRoom();

    public NotFoundChatRoom() {
        super(ChatRoomErrorCode.NOT_FOUND_CHATROOM);
    }
}
