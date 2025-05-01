package com.petmatz.persistence.user.adapter;

import com.petmatz.application.user.exception.UserException;
import com.petmatz.common.exception.PersistenceException;
import com.petmatz.domain.user.User;
import com.petmatz.domain.user.model.Profile;
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

import java.util.List;

import static com.petmatz.application.user.exception.UserErrorCode.USER_NOT_FOUND;

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
    public User findAccountIdByUserInfo(String accountId) {
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

    @Override
    public List<User> findByRegionCodeOrderByRecommendationCountDesc(Integer regionCode) {
        List<UserEntity> rank = userRepository.findRank(regionCode);
        return rank.stream().map(UserDomainMapper::fromDomain).toList();
    }

    @Override
    public void updateUser(User user, Long userId) {
        Profile profile = user.getProfile();
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new PersistenceException(PersistenceErrorCode.USER_NOT_FOUND));
        userEntity.getProfileEntity().updateProfile(profile.getNickname(), profile.getPreferredSizes(), profile.getIntroduction(), profile.getCareAvailable(), profile.getProfileImg());
    }

    @Override
    public void updatePassword(String currentPassword, String newPassword, Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new PersistenceException(PersistenceErrorCode.USER_NOT_FOUND));
        userEntity.getAccountEntity().updatePassword(newPassword);
    }

    @Override
    public void updateRecommendation(Long recommendedId) {
        UserEntity userEntity = userRepository.findById(recommendedId).orElseThrow(() -> new PersistenceException(PersistenceErrorCode.USER_NOT_FOUND));
        userEntity.getUserStatsEntity().updateRecommendation();
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserException(USER_NOT_FOUND);
        }
        userRepository.deleteById(userId);
    }


}
