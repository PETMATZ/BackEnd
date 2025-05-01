package com.petmatz.domain.user;


//TODO 뺴야됨
import com.petmatz.application.user.exception.UserException;

import com.petmatz.domain.user.constant.PreferredSize;
import com.petmatz.domain.user.model.Account;
import com.petmatz.domain.user.model.Location;
import com.petmatz.domain.user.model.Profile;
import com.petmatz.domain.user.model.UserState;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static com.petmatz.application.user.exception.UserErrorCode.PASS_WORD_MISMATCH;
import static com.petmatz.application.user.exception.UserErrorCode.USER_NOT_FOUND;

@Getter
@Builder
public class User {

    private final Long id;
    private final Account account;
    private final Profile profile;
    private final Location location;
    private final UserState stats;


    public User(Long id, Account account, Profile profile, Location location, UserState stats) {
        this.id = id;
        this.account = account;
        this.profile = profile;
        this.location = location;
        this.stats = stats;
    }

    public void checkAccountIdAndPassword(String accountId, String password) {
        if (this.account.getAccountId().equals(accountId)) {
            throw new UserException(USER_NOT_FOUND);
        }
        if (this.account.getPassword().equals(password)) {
            throw new UserException(PASS_WORD_MISMATCH);
        }
    }

    public void updateUserInfo(String nickname, List<PreferredSize> preferredSizes, String introduction, boolean careAvailable, String imgURL) {
        profile.updateProfile(nickname, preferredSizes, introduction, careAvailable, imgURL);
    }


}
