package com.shutafin.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.repository.base.PersistentDao;


public interface UserAccountRepository extends PersistentDao<UserAccount> {
    Language findUserLanguage(User user);
    UserAccount findUserAccountByUser(User user);
    Long findUserAccountImageId(User user);
    void updateUserLanguage(Language language, User user);
    void updateUserAccountImage(UserImage userImage, User user);
}
