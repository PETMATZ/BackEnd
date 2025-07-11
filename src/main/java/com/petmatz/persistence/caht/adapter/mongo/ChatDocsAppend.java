package com.petmatz.persistence.caht.adapter.mongo;

import com.petmatz.persistence.caht.mongo.ChatReadStatusDocs;
import com.petmatz.persistence.caht.mongo.ChatRoomDocs;
import com.petmatz.persistence.caht.mongo.ChatRoomMetadataDocs;
import com.petmatz.application.chat.dto.ChatRoomInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ChatDocsAppend {

    private final MongoTemplate mongoTemplate;


    //채팅방 생성시 이에 해당하는 Docs도 같이 생성 ( 채팅 내역, 채팅방 메타 데이터 )
    //참고로 chat_read_status는 자기자신과 상대방 2개를 생성
    public void init(ChatRoomInfo chatRoomInfo,long chatRoomID) {
        ChatRoomDocs chatRoomDocs = createdChatRooms(chatRoomInfo, chatRoomID);
        ChatRoomMetadataDocs chatRoomMetadataDocs = createdChatRoomMetaDataDocs(chatRoomInfo, chatRoomID);
        ChatReadStatusDocs caregiverChatReadStatusDocs = createdChatReadStatusDocs(chatRoomInfo.caregiverInfo(),chatRoomID);
        ChatReadStatusDocs entrustedChatReadStatusDocs = createdChatReadStatusDocs(chatRoomInfo.entrustedInfo(),chatRoomID);

        mongoTemplate.save(caregiverChatReadStatusDocs);
        mongoTemplate.save(entrustedChatReadStatusDocs);
        mongoTemplate.save(chatRoomDocs);
        mongoTemplate.save(chatRoomMetadataDocs);
    }

    private ChatReadStatusDocs createdChatReadStatusDocs(String userEmail,long chatRoomID) {
        return ChatReadStatusDocs.initChatReadStatusData(userEmail, chatRoomID);
    }

    private ChatRoomMetadataDocs createdChatRoomMetaDataDocs(ChatRoomInfo chatRoomInfo,long chatRoomID) {
        return ChatRoomMetadataDocs.initChatRoomMetaData(chatRoomInfo,chatRoomID);
    }

    private ChatRoomDocs createdChatRooms(ChatRoomInfo chatRoomInfo,long chatRoomID) {
        return ChatRoomDocs.initChatRoomDocs(chatRoomInfo, chatRoomID );
    }




}
