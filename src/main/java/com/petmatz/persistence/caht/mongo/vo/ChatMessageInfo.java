package com.petmatz.persistence.caht.mongo.vo;

import com.petmatz.common.constants.ChatMessageMsgType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ChatMessageInfo {


    private Long senderId;

    private Long receiverId;

    private String msg;

    private LocalDateTime msgTimestamp;

    private ChatMessageMsgType msg_type;

    private boolean readStatus = true;


    @Builder
    public ChatMessageInfo(Long senderId, Long receiverId, String msg, LocalDateTime msgTimestamp, ChatMessageMsgType msg_type, boolean readStatus) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.msg = msg;
        this.msgTimestamp = msgTimestamp;
        this.msg_type = msg_type;
        this.readStatus = readStatus;
    }

    public void changeReadStatus(LocalDateTime lastReadTimestamp) {
        this.readStatus = msgTimestamp.isBefore(lastReadTimestamp) || msgTimestamp.isEqual(lastReadTimestamp);
    }

//    public void addSenderEmail(Long senderId) {
//        if (this.senderId == null || this.receiverId == null) {
//            this.receiverId = senderId;
//        }
//    }

}
