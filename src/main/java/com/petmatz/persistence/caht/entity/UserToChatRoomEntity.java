package com.petmatz.persistence.caht.entity;

import com.petmatz.persistence.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class UserToChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoomEntity chatRoom; // 채팅방

    public UserToChatRoomEntity() {
    }

    public void addUser(UserEntity user) {
        this.user = user;
        if (!user.getChatRooms().contains(this)) {
            user.getChatRooms().add(this); // 양방향 관계 설정
        }
    }

    public void addChatRoom(ChatRoomEntity chatRoom) {
        this.chatRoom = chatRoom;
        if (!chatRoom.getParticipants().contains(this)) {
            chatRoom.getParticipants().add(this); // 양방향 관계 설정
        }
    }

    @Override
    public String toString() {
        return "UserToChatRoomEntity{" +
                "id=" + id +
                ", user.getNickname=" + user.getProfileEntity().getNickname() +
                '}';
    }
}
