package com.shutafin.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.entities.infrastructure.Language;

public interface UserAccountRepositoryCustom {
    Language findUserLanguage(User user);
    void updateUserLanguage(Language language, User user);
    void updateUserAccountImage(UserImage userImage, User user);
    Long findUserAccountImageId(User user);
}
