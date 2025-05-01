package com.petmatz.persistence.user.repository;

import com.petmatz.persistence.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsById(Long userId);

    boolean existsByAccountEntity_AccountId(String accountEntityAccountId);

    Optional<UserEntity> findById(Long userId);

    @Query("SELECT u.id FROM User u WHERE u.id = :id")
    Optional<String> findUsernameById(@Param("id") Long id);

    Optional<UserEntity> findUserEntityByAccountEntity_AccountId(String accountId);

    @Query("SELECT u.accountEntity.accountId FROM User u WHERE u.id = :userId")
    String findAccountIdByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.id = :userId")
    void deleteUserById(@Param("userId") Long userId);

    @Query("SELECT u FROM User u WHERE u.locationEntity.regionCode = :regionCode ORDER BY u.userStatsEntity.recommendationCount DESC")
    List<UserEntity> findRank(@Param("regionCode") Integer regionCode);



    //---------TODO 아래는 임시


}
