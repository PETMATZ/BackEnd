package com.petmatz.persistence.caht.repository;

import com.petmatz.persistence.caht.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

    @Query("SELECT c FROM ChatRoomEntity c " +
            "JOIN c.participants p1 " +
            "JOIN c.participants p2 " +
            "WHERE p1.user.accountEntity.accountId = :user1 AND p2.user.accountEntity.accountId = :user2")
    Optional<ChatRoomEntity> findChatRoomByUsers(@Param("user1") String user1,
                                                 @Param("user2") String user2);


}
