package com.petmatz.persistence.caht.repository;

import com.petmatz.persistence.caht.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

    @Query("SELECT c FROM ChatRoomEntity c " +
            "WHERE (c.caregiverId = :id1 AND c.entrustedId = :id2) " +
            "   OR (c.caregiverId = :id2 AND c.entrustedId = :id1)")
    Optional<ChatRoomEntity> findChatRoomByUsers(
            @Param("id1") Long id1,
            @Param("id2") Long id2);


    @Query("SELECT DISTINCT cr.id FROM ChatRoomEntity cr WHERE cr.caregiverId = :userId OR cr.entrustedId = :userId")
    List<ChatRoomEntity> findDistinctByUserAccountId(@Param("userId") Long userId);

}
