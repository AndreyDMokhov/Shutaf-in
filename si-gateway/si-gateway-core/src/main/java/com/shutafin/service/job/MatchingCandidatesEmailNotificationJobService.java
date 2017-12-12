package com.shutafin.service.job;


import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.types.AccountStatus;
import com.shutafin.model.entities.types.AccountType;
import com.shutafin.model.entities.types.EmailReason;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

    // MS-matching
@Component
public class MatchingCandidatesEmailNotificationJobService {

    private static final String URL_PROFILE = "/#/profile/";
    private static final String URL_SEARCH = "/#/users/search";
    private static final Integer MAX_COUNT_MATCHING_USERS = 3;

    private UserAccountRepository userAccountRepository;
    //TODO moved to matching service
    private UserMatchService userMatchService;
    private EnvironmentConfigurationService environmentConfigurationService;
    private EmailTemplateService emailTemplateService;
    private EmailNotificationSenderService mailSenderService;
    private UserAccountService userAccountService;
    private UserImageService userImageService;

    @Autowired
    public MatchingCandidatesEmailNotificationJobService(
            UserAccountRepository userAccountRepository,
            UserMatchService userMatchService,
            EnvironmentConfigurationService environmentConfigurationService,
            EmailTemplateService emailTemplateService,
            EmailNotificationSenderService mailSenderService,
            UserAccountService userAccountService,
            UserImageService userImageService) {
        this.userAccountRepository = userAccountRepository;
        this.userMatchService = userMatchService;
        this.environmentConfigurationService = environmentConfigurationService;
        this.emailTemplateService = emailTemplateService;
        this.mailSenderService = mailSenderService;
        this.userAccountService = userAccountService;
        this.userImageService = userImageService;
    }


    //    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void sendEmailNotification() {
        List<UserAccount> userAccounts = userAccountRepository.findAllByAccountStatusAndAccountType(AccountStatus.CONFIRMED, AccountType.REGULAR);
        for (UserAccount userAccount : userAccounts) {
            User user = userAccount.getUser();
            //todo switch to general matching
            //todo add account status condition = matching completed
            List<User> matchingUsers = userMatchService.findMatchingUsers(user);
            if (!matchingUsers.isEmpty()) {
                sendEmail(userAccount, matchingUsers);
            }
        }
    }

    // TODO: MS-email EmailNotificationSenderController.sendEmail()
    private void sendEmail(UserAccount userAccount, List<User> matchingUsers) {
        String link = "";
        String serverAddress = environmentConfigurationService.getServerAddress();
        Map<String, byte[]> imageSources = new TreeMap<>();
        Integer maxCountMatchingUsers = matchingUsers.size() >= MAX_COUNT_MATCHING_USERS ? MAX_COUNT_MATCHING_USERS : matchingUsers.size();

        for (User matchingUser : matchingUsers.subList(0, maxCountMatchingUsers)) {
            imageSources.put(matchingUser.getId().toString(), getUserImage(matchingUser));
            link = link.concat(getLink(matchingUser, serverAddress));
        }
        link = link.concat(addSearchToLink(serverAddress));

        EmailMessage emailMessage = emailTemplateService.getEmailMessage(
                userAccount.getUser(),
                EmailReason.MATCHING_CANDIDATES,
                userAccount.getLanguage(),
                link,
                imageSources);

        mailSenderService.sendEmail(emailMessage, EmailReason.MATCHING_CANDIDATES);
    }

    private byte[] getUserImage(User matchingUser) {
        String image;
        if (userAccountService.findUserAccountProfileImage(matchingUser) == null) {
            image = userImageService.getDefaultImageBase64();
        } else {
            image = userAccountService.findUserAccountProfileImage(matchingUser).getImage();
        }
        return Base64.getDecoder().decode(image);
    }

    private String getLink(User matchingUser, String serverAddress) {
        return ""
                .concat("<p style=\"font-size:14px\"><a href=\"")
                .concat(serverAddress)
                .concat(URL_PROFILE)
                .concat(matchingUser.getId().toString())
                .concat("\"> ")
                .concat(matchingUser.getFirstName())
                .concat(" ")
                .concat(matchingUser.getLastName())
                .concat(" <br><img src=\"cid:")
                .concat(matchingUser.getId().toString())
                .concat("\" style=\"width:128px;height:128px;\">")
                .concat("</a></p>");
    }

    private String addSearchToLink(String serverAddress) {
        return ""
                .concat("<p><a href=\"")
                .concat(serverAddress)
                .concat(URL_SEARCH)
                .concat("\">");
    }
}
