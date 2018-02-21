package com.shutafin.service.deal.impl;

import com.shutafin.model.web.account.AccountStatus;
import com.shutafin.model.web.account.AccountUserImageWeb;
import com.shutafin.model.web.deal.*;
import com.shutafin.model.web.email.EmailNotificationDealWeb;
import com.shutafin.model.web.email.EmailReason;
import com.shutafin.model.web.email.EmailUserImageSource;
import com.shutafin.model.web.email.EmailUserLanguage;
import com.shutafin.model.web.email.response.EmailDealCreationResponse;
import com.shutafin.model.web.email.response.EmailDealUserAddingResponse;
import com.shutafin.model.web.email.response.EmailDealUserRemovingResponse;
import com.shutafin.sender.account.UserAccountControllerSender;
import com.shutafin.sender.deal.DealControllerSender;
import com.shutafin.sender.email.EmailNotificationSenderControllerSender;
import com.shutafin.sender.matching.UserMatchControllerSender;
import com.shutafin.service.deal.DealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class DealServiceImpl implements DealService {

    @Autowired
    private DealControllerSender dealControllerSender;

    @Autowired
    private EmailNotificationSenderControllerSender emailNotificationSenderControllerSender;

    @Autowired
    private UserAccountControllerSender userAccountControllerSender;

    @Autowired
    private UserMatchControllerSender userMatchControllerSender;

    @Override
    public DealWeb initiateDeal(DealWeb dealWeb, Long userId) {
        dealWeb = dealControllerSender.initiateDeal(dealWeb, userId);
        emailNotificationSenderControllerSender.sendEmailDeal(
                getEmailNotificationDealWeb(dealWeb, userId, null, EmailReason.DEAL_CREATION));
        return dealWeb;
    }

    private EmailNotificationDealWeb getEmailNotificationDealWeb(DealWeb dealWeb, Long originUserId, Long userToChange,
                                                                 EmailReason emailReason) {
        List<EmailUserLanguage> emailUserLanguageList = userAccountControllerSender.getEmailUserLanguage(dealWeb.getUsers());
        return EmailNotificationDealWeb.builder()
                .emailReason(emailReason)
                .dealId(dealWeb.getDealId())
                .dealTitle(dealWeb.getTitle())
                .userOrigin(getEmailUserImageSource(originUserId))
                .userToChange(getEmailUserImageSource(userToChange))
                .emailUserLanguage(emailUserLanguageList)
                .build();
    }

    private EmailNotificationDealWeb getEmailNotificationDealWeb(Long dealId, Long originUserId, Long userToChange,
                                                                 EmailReason emailReason) {
        DealResponse deal = dealControllerSender.getDeal(dealId, originUserId);
        List<Long> users = new ArrayList<>();
        for (AccountUserImageWeb accountUserImageWeb : deal.getUsers()) {
            if (accountUserImageWeb.getUserId() != userToChange) {
                users.add(accountUserImageWeb.getUserId());
            }
        }
        List<EmailUserLanguage> emailUserLanguageList = userAccountControllerSender.getEmailUserLanguage(users);
        return EmailNotificationDealWeb.builder()
                .emailReason(emailReason)
                .dealId(deal.getDealId())
                .dealTitle(deal.getTitle())
                .userOrigin(getEmailUserImageSource(originUserId))
                .userToChange(getEmailUserImageSource(userToChange))
                .emailUserLanguage(emailUserLanguageList)
                .build();
    }

    private EmailUserImageSource getEmailUserImageSource(Long userId) {
        if (userId == null) {
            return null;
        }
        AccountUserImageWeb accountUserImageWeb = userAccountControllerSender.getUserAccountProfileImage(userId);
        return EmailUserImageSource.builder()
                .userId(accountUserImageWeb.getUserId())
                .firstName(accountUserImageWeb.getFirstName())
                .lastName(accountUserImageWeb.getLastName())
                .imageSource(accountUserImageWeb.getImage())
                .build();
    }

    @Override
    public DealResponse confirmDealUser(String link) {
        EmailDealCreationResponse emailDeal =
                (EmailDealCreationResponse) emailNotificationSenderControllerSender.confirmLink(link, EmailReason.DEAL_CREATION);
        Long userId = emailDeal.getUserId();
        try {
            setUserAccountMatchingStatus(userId, AccountStatus.DEAL, false, false);
            dealControllerSender.confirmDealUser(emailDeal.getDealId(), userId);
        } catch (Exception e) {
            setUserAccountMatchingStatus(userId, AccountStatus.COMPLETED_REQUIRED_MATCHING, true, true);
        }
        return dealControllerSender.getDeal(emailDeal.getDealId(), userId);
    }

    @Override
    public DealResponse leaveDeal(Long dealId, Long userId) {
        DealResponse deal = null;
        try {
            deal = dealControllerSender.getDeal(dealId, userId);
            changeUsersDealStatus(deal, userId);
            dealControllerSender.leaveDeal(dealId, userId);
        } catch (Exception e) {
            rollbackIfDealNotArchive(deal, userId);
        }
        return deal;
    }

    @Override
    public void removeDealUser(Long dealId, Long userOriginId, Long userToRemoveId) {
        emailNotificationSenderControllerSender.sendEmailDeal(
                getEmailNotificationDealWeb(dealId, userOriginId, userToRemoveId, EmailReason.DEAL_USER_REMOVING));
    }

    @Override
    public DealResponse confirmRemoveDealUser(String link) {
        DealResponse deal = null;
        EmailDealUserRemovingResponse emailDeal = (EmailDealUserRemovingResponse)
                emailNotificationSenderControllerSender.confirmLink(link, EmailReason.DEAL_USER_REMOVING);
        try {
            if (emailDeal.getIsConfirmed()) {
                deal = dealControllerSender.getDeal(emailDeal.getDealId(), emailDeal.getUserOriginId());
                changeUsersDealStatus(deal, emailDeal.getUserIdToRemove());
                dealControllerSender.removeDealUser(emailDeal.getDealId(), emailDeal.getUserOriginId(), emailDeal.getUserIdToRemove());
            }
        } catch (Exception e) {
            if (emailDeal.getIsConfirmed()) {
                rollbackIfDealNotArchive(deal, emailDeal.getUserIdToRemove());
            }
        }
        return dealControllerSender.getDeal(emailDeal.getDealId(), emailDeal.getUserOriginId());
    }

    @Override
    public DealResponse addDealUser(Long dealId, Long userOriginId, Long userToAddId) {
        emailNotificationSenderControllerSender.sendEmailDeal(
                getEmailNotificationDealWeb(dealId, userOriginId, userToAddId, EmailReason.DEAL_USER_ADDING));
        dealControllerSender.addDealUser(dealId, userOriginId, userToAddId);
        return dealControllerSender.getDeal(dealId, userOriginId);
    }

    @Override
    public DealResponse confirmAddDealUser(String link) {
        EmailDealUserAddingResponse emailDeal = (EmailDealUserAddingResponse)
                emailNotificationSenderControllerSender.confirmLink(link, EmailReason.DEAL_USER_ADDING);
        try {
            if (emailDeal.getIsConfirmed()) {
                setUserAccountMatchingStatus(emailDeal.getUserIdToAdd(), AccountStatus.DEAL, false, false);
                dealControllerSender.confirmAddDealUser(emailDeal.getDealId(), emailDeal.getUserOriginId(), emailDeal.getUserIdToAdd());
            }
        } catch (Exception e) {
            if (emailDeal.getIsConfirmed()) {
                setUserAccountMatchingStatus(emailDeal.getUserIdToAdd(), AccountStatus.COMPLETED_REQUIRED_MATCHING, true, true);
            }
        }
        return dealControllerSender.getDeal(emailDeal.getDealId(), emailDeal.getUserOriginId());
    }

    private void changeUsersDealStatus(DealResponse deal, Long userId) {
        if (deal.getStatusId() == DealStatus.ARCHIVE) {
            for (AccountUserImageWeb accountUserImageWeb : deal.getUsers()) {
                setUserAccountMatchingStatus(accountUserImageWeb.getUserId(),
                        AccountStatus.COMPLETED_REQUIRED_MATCHING, true, true);
            }
        } else {
            setUserAccountMatchingStatus(userId, AccountStatus.COMPLETED_REQUIRED_MATCHING, true, true);
        }
    }

    private void rollbackIfDealNotArchive(DealResponse deal, Long userId) {
        if (deal != null) {
            if (deal.getStatusId() != DealStatus.ARCHIVE) {
                setUserAccountMatchingStatus(userId, AccountStatus.DEAL, false, false);
            }
        }
    }

    private void setUserAccountMatchingStatus(Long userId, AccountStatus accountStatus, Boolean enforce, Boolean isMatchingEnabled) {
        userAccountControllerSender.updateUserAccountStatus(userId, accountStatus, enforce);
        userMatchControllerSender.configure(userId, isMatchingEnabled);
    }

    @Override
    public List<DealUserWeb> getAllUserDeals(Long userId) {
        return dealControllerSender.getAllUserDeals(userId);
    }

    @Override
    public DealResponse getDeal(Long dealId, Long userId) {
        return dealControllerSender.getDeal(dealId, userId);
    }

    @Override
    public DealWeb renameDeal(Long dealId, Long userId, DealTitleChangeWeb dealTitleChangeWeb) {
        return dealControllerSender.renameDeal(dealId, userId, dealTitleChangeWeb);
    }

    @Override
    public void deleteDeal(Long dealId, Long userId) {
        dealControllerSender.deleteDeal(dealId, userId);
    }
}
