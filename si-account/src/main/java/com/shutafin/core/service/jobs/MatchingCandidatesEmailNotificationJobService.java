package com.shutafin.core.service.jobs;

import com.shutafin.core.service.UserAccountService;
import com.shutafin.core.service.UserImageService;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.types.AccountType;
import com.shutafin.model.web.account.AccountStatus;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.web.email.EmailReason;
import com.shutafin.model.web.email.EmailUserImageSource;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.account.UserRepository;
import com.shutafin.sender.email.EmailNotificationSenderControllerSender;
import com.shutafin.sender.matching.UserMatchControllerSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
@Profile("!dev")
public class MatchingCandidatesEmailNotificationJobService {

    private static final Integer MAX_COUNT_MATCHING_USERS = 3;

    private UserAccountRepository userAccountRepository;
    private UserMatchControllerSender userMatchControllerSender;
    private EmailNotificationSenderControllerSender emailNotificationSenderControllerSender;
    private UserRepository userRepository;
    private UserAccountService userAccountService;
    private UserImageService userImageService;

    public MatchingCandidatesEmailNotificationJobService(UserAccountRepository userAccountRepository,
                                                         UserMatchControllerSender userMatchControllerSender,
                                                         EmailNotificationSenderControllerSender emailNotificationSenderControllerSender,
                                                         UserRepository userRepository,
                                                         UserAccountService userAccountService,
                                                         UserImageService userImageService) {
        this.userAccountRepository = userAccountRepository;
        this.userMatchControllerSender = userMatchControllerSender;
        this.emailNotificationSenderControllerSender = emailNotificationSenderControllerSender;
        this.userRepository = userRepository;
        this.userAccountService = userAccountService;
        this.userImageService = userImageService;
    }

//    @Scheduled(fixedDelay = 720000)
    @Transactional
    public void sendEmailNotification() {
        List<UserAccount> userAccounts = userAccountRepository.findAllByAccountStatusAndAccountType(
                                                                                            AccountStatus.COMPLETED_REQUIRED_MATCHING,
                                                                                            AccountType.REGULAR);
        for (UserAccount userAccount : userAccounts) {
            List<Long> matchingUsers = userMatchControllerSender.getMatchingUsers(userAccount.getUser().getId());
            log.info("Found {} users matching with user ID {} ({} {})",
                    matchingUsers.size(),
                    userAccount.getUser().getId(),
                    userAccount.getUser().getFirstName(),
                    userAccount.getUser().getLastName());

            if (!matchingUsers.isEmpty()) {
                sendEmail(userAccount, matchingUsers);
            }
        }
    }

    private void sendEmail(UserAccount userAccount, List<Long> matchingUsers) {
        Integer maxCountMatchingUsers = matchingUsers.size() >= MAX_COUNT_MATCHING_USERS ? MAX_COUNT_MATCHING_USERS : matchingUsers.size();
        Set<EmailUserImageSource> emailUserImageSources = new LinkedHashSet<>();
        for (Long matchingUser : matchingUsers.subList(0, maxCountMatchingUsers)) {
            User user = userRepository.findOne(matchingUser);
            EmailUserImageSource emailUserImageSource = new EmailUserImageSource(matchingUser, user.getFirstName(), user.getLastName(), getUserImage(user));
            emailUserImageSources.add(emailUserImageSource);
        }
        log.info("Sending matching users offer to {}", userAccount.getUser().getEmail());
        emailNotificationSenderControllerSender.sendEmail(getEmailNotificationWeb(userAccount, emailUserImageSources));
    }

    private EmailNotificationWeb getEmailNotificationWeb(UserAccount userAccount, Set<EmailUserImageSource> emailUserImageSources) {
        User user = userAccount.getUser();
        return EmailNotificationWeb
                .builder()
                .userId(user.getId())
                .emailTo(user.getEmail())
                .emailChange(null)
                .languageCode(userAccount.getLanguage().getDescription())
                .emailReason(EmailReason.MATCHING_CANDIDATES)
                .emailUserImageSources(emailUserImageSources)
                .build();
    }

    private String getUserImage(User user) {
        if (userAccountService.findUserAccountProfileImage(user) == null) {
            return userImageService.getDefaultImageBase64();
        } else {
            return userAccountService.findUserAccountProfileImage(user).getImageStorage().getImageEncoded();
        }
    }

}
