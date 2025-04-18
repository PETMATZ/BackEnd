package com.petmatz.persistence.user.adapter;

import com.petmatz.common.exception.PersistenceException;
import com.petmatz.domain.user.User;
import com.petmatz.domain.user.port.UserCommandPort;
import com.petmatz.domain.user.port.UserQueryPort;
import com.petmatz.persistence.exception.PersistenceErrorCode;
import com.petmatz.persistence.user.UserEntity;
import com.petmatz.persistence.user.mapper.UserDomainMapper;
import com.petmatz.persistence.user.mapper.UserEntityMapper;
import com.petmatz.persistence.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Transactional
public class UserAdapter implements UserCommandPort, UserQueryPort {

    private final UserRepository userRepository;

    @Override
    public Long save(User user) {
        UserEntity userEntity = userRepository.save(UserEntityMapper.toEntity(user));
        return userEntity.getId();
    }

    @Override
    public void updateUserLocation(Double latitude, Double longitude, String region, Integer regionCode, String accountId) {
        UserEntity userEntity = userRepository.findUserEntityByAccountEntity_AccountId(accountId).orElseThrow(() -> new PersistenceException(PersistenceErrorCode.USER_NOT_FOUND));
        userEntity.getLocationEntity().updateLocation(latitude, longitude, region, regionCode);
    }

    @Override
    public boolean existsByAccountId(String accountId) {
        return userRepository.existsByAccountEntity_AccountId(accountId);
    }

    @Override
    public User findByUserInfo(String accountId) {
        return UserDomainMapper.fromDomain(userRepository.findUserEntityByAccountEntity_AccountId(accountId).orElseThrow(() -> new PersistenceException(PersistenceErrorCode.USER_NOT_FOUND)));
    }

    @Override
    public String findAccountIdByUserId(Long userId) {
        return userRepository.findAccountIdByUserId(userId);
    }

    @Override
    public User findById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new PersistenceException(PersistenceErrorCode.USER_NOT_FOUND));
        return UserDomainMapper.fromDomain(userEntity);
    }


}
