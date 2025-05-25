package com.petmatz.persistence.caht.repository;

import com.petmatz.persistence.caht.entity.UserToChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserToChatRoomRepository extends JpaRepository<UserToChatRoomEntity, Long> {

    @Query("SELECT DISTINCT cr.chatRoom.id FROM UserToChatRoomEntity cr WHERE cr.user.accountEntity.accountId = :accountId")
    List<Long> findDistinctByUserAccountId(@Param("accountId") String accountId);

    @Query("SELECT cr FROM UserToChatRoomEntity cr WHERE cr.chatRoom.id IN :chatRoomIdList AND cr.user.accountEntity.accountId != :accountId")
    List<UserToChatRoomEntity> selectChatRoomUserList(@Param("chatRoomIdList") List<Long> chatRoomIdList,
                                                      @Param("accountId") String accountId);

    void deleteByChatRoom_Id(Long chatRoomId);

    @Query("select u.user.accountEntity.accountId from UserToChatRoomEntity u where u.chatRoom.id = :chatRoomId")
    List<String> selectChatRoomForUserList(@Param("chatRoomId") Long chatRoomId);

    @Query(value = "SELECT chat_room_id FROM user_to_chat_room_entity " +
            "WHERE account_id IN (:careEmail, :receiverEmail) " +
            "GROUP BY chat_room_id " +
            "HAVING COUNT(DISTINCT account_id) = 2",
            nativeQuery = true)
    Optional<String> selectChatRoomIdForUser1ToUser2(@Param("careEmail") String careEmail,
                                                     @Param("receiverEmail") String receiverEmail);

}
