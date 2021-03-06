package com.shutafin.core.service.impl;

import com.shutafin.core.service.UserAccountService;
import com.shutafin.core.service.UserImageService;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.exception.exceptions.AccountBlockedException;
import com.shutafin.model.exception.exceptions.AccountNotConfirmedException;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.exception.exceptions.SystemException;
import com.shutafin.model.types.CompressionType;
import com.shutafin.model.web.account.AccountStatus;
import com.shutafin.model.web.account.AccountUserImageWeb;
import com.shutafin.repository.account.UserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserImageService userImageService;


    @Override
    public AccountStatus getUserAccountStatus(User user) {
        if (user != null){
            UserAccount userAccount = userAccountRepository.findByUser(user);
            return userAccount.getAccountStatus();
        }
        return null;
    }

    @Override
    @Transactional
    public AccountStatus updateUserAccountStatus(Integer accountStatusId, User user) {

        if (user != null){
            UserAccount userAccount = userAccountRepository.findByUser(user);
            userAccount.setAccountStatus(AccountStatus.getById(accountStatusId));
            userAccountRepository.save(userAccount);

            return userAccount.getAccountStatus();
        }

        return null;
    }

    @Override
    public UserImage updateProfileImage(AccountUserImageWeb userImageWeb, User user) {
        UserImage userImage = null;
        UserAccount userAccount = userAccountRepository.findByUser(user);

        if (userImageWeb.getId() != null) {
            try {
                userImage = userImageService.getUserImage(user, userImageWeb.getId());
            } catch (ResourceNotFoundException exp) {
                log.warn("Resource not found exception:");
                log.warn("User Image with ID {} was not found", userImageWeb.getId());
                userImage = null;
            }
        }

        if (userImage == null) {
            userImage = userImageService.addUserImage(userImageWeb, user, CompressionType.COMPRESSION_RATE_0_7);
        }

        if (userAccount != null) {
            userAccount.setUserImage(userImage);
        }
        return userImage;
    }

    @Override
    public UserImage findUserAccountProfileImage(User user) {
        return userAccountRepository.findByUser(user).getUserImage();
    }

    @Override
    public List<UserImage> findUsersAccountsProfileImages(List<User> users) {
        return userAccountRepository
                .findAllByUserIn(users)
                .stream()
                .map(UserAccount::getUserImage)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserAccountProfileImage(User user) {
        UserAccount userAccount = userAccountRepository.findByUser(user);
        userAccount.setUserImage(null);
    }

    @Override
    public void checkUserAccountStatus(User user) {
        UserAccount userAccount = userAccountRepository.findByUser(user);
        if (userAccount == null) {
            String message = String.format("UserAccount for user with ID %s does not exist", user.getId());
            log.error(message, user.getId());
            throw new SystemException(message);
        }
        AccountStatus accountStatus = userAccount.getAccountStatus();
        if (accountStatus == AccountStatus.BLOCKED) {
            log.warn("UserAccount for userId {} is BLOCKED", user.getId());
            throw new AccountBlockedException();
        }
        if (accountStatus == AccountStatus.NEW) {
            log.warn("UserAccount for userId {} is not CONFIRMED", user.getId());
            throw new AccountNotConfirmedException();
        }
    }

    @Override
    public UserAccount findUserAccountByUser(User user) {
        return userAccountRepository.findByUser(user);
    }
}
