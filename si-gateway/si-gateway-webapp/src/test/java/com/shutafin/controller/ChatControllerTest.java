package com.shutafin.controller;


import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.entities.ReadMessagesRequest;
import com.shutafin.model.exception.exceptions.AuthenticationException;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.account.AccountUserWeb;
import com.shutafin.model.web.chat.ChatMessageResponse;
import com.shutafin.model.web.chat.ChatWithUsersListDTO;
import com.shutafin.model.web.common.UserSearchResponse;
import com.shutafin.model.web.user.UserBaseResponse;
import com.shutafin.service.*;
import com.shutafin.system.BaseTestImpl;
import com.shutafin.system.ControllerRequest;
import org.atteo.evo.inflector.English;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class ChatControllerTest extends BaseTestImpl {

    private static final String VALID_SESSION_ID = "validsessionid";
    private static final String INVALID_SESSION_ID = "invalidsessionid";
    private static final String ADD_CHAT_URL = "/chat/new";
    private static final String RENAME_CHAT_URL ="/chat/rename" ;
    private static final String ADD_CHAT_USER_URL = "/add/user";
    private static final String REMOVE_CHAT_URL = "/remove/chat";
    private static final String GET_CHAT_URL = "/chat/get/chats";
    private static final String GET_MASSAGES_URL = "/get/messages";
    private static final String GET_USERS_URL = "/chat/allUsers";
    private static final String UPDATE_MESSAGES_AS_READ_URL = "/chat/updateMessagesAsRead";

    private List<String> errorList;
    private Long validUser;
    private ReadMessagesRequest readMessagesRequest;


    @MockBean
    private SessionManagementService sessionManagementService;

    @MockBean
    private ChatManagementService chatManagementService;

    @MockBean
    private ChatAuthorizationService chatAuthorizationService;

    @MockBean
    private ChatInfoService chatInfoService;

    @MockBean
    private UserSearchService userSearchService;

    @MockBean
    private UserMatchService userMatchService;


    @Before
    public void setUp(){
        validUser = 1L;
        errorList = new ArrayList<>();
        List<ChatWithUsersListDTO> listChatWithUsersListDTO = new ArrayList<>();
        List<ChatMessageResponse> listChatMessageResponse = new ArrayList<>();
        List<ChatMessage> listChatMessage = new ArrayList<>();
        List<UserBaseResponse> listUserBaseResponse = new ArrayList<>();
        readMessagesRequest = createReadMessagesRequest();

        Mockito.when(sessionManagementService.findUserWithValidSession(VALID_SESSION_ID)).thenReturn(validUser);
        Mockito.when(sessionManagementService.findUserWithValidSession(INVALID_SESSION_ID))
                .thenThrow(new AuthenticationException());
        Mockito.when(chatManagementService.createNewChat(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(new ChatWithUsersListDTO());
        Mockito.when(chatManagementService.renameChat(Mockito.any(Chat.class), Mockito.anyString(), Mockito.anyLong()))
                .thenReturn(new ChatWithUsersListDTO());
        Mockito.doNothing().when(chatManagementService).updateMessagesAsRead(Mockito.anyListOf(Long.class), Mockito.anyLong());

        Mockito.doNothing().when(chatManagementService).addChatUserToChat(Mockito.anyLong(), Mockito.any(Chat.class), Mockito.anyLong());
        Mockito.doNothing().when(chatManagementService).removeChatUserFromChat(Mockito.anyLong(), Mockito.any(Chat.class), Mockito.anyLong());
        Mockito.when(chatInfoService.getListChats(Mockito.anyLong()))
                .thenReturn(listChatWithUsersListDTO);
        Mockito.when(chatInfoService.getListMessages(Mockito.anyLong(),Mockito.anyLong()))
                .thenReturn(listChatMessage);
        Mockito.when(chatAuthorizationService.findAuthorizedChat(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(new Chat());
        Mockito.when(chatAuthorizationService.findAuthorizedChatUser(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(new ChatUser());
        Mockito.when(userSearchService.userBaseResponseByList(Mockito.anyLong(),Mockito.anyListOf(Long.class)))
                .thenReturn(listUserBaseResponse);
        Mockito.when(userMatchService.findMatchingUsers(Mockito.anyLong()))
                .thenReturn(Mockito.anyListOf(Long.class));
    }

    private ReadMessagesRequest createReadMessagesRequest() {
        ReadMessagesRequest readMessagesRequest = new ReadMessagesRequest();
        List<Long> listLong = new ArrayList<>();
        readMessagesRequest.setMessageIdList(listLong);
        return readMessagesRequest;
    }

    @Test
    public void addChat_Positive(){
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(ADD_CHAT_URL+"/"+"chat_title"+"/"+1L)
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void renameChat_Positive(){
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(RENAME_CHAT_URL+"/"+1L+"/"+"chat_title")
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void addChatUser_Positive(){
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl("/chat"+"/"+1L+ADD_CHAT_USER_URL+"/"+1L)
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void removeChatUser_Positive(){
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl("/chat"+"/"+1L+ADD_CHAT_USER_URL+"/"+1L)
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void removeChat_Positive(){
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl("/chat"+"/" + 1L + REMOVE_CHAT_URL)
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void getChats_Positive(){
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(GET_CHAT_URL)
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(sessionHeaders)
                .setResponseClass(List.class)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void getMessages_Positive(){
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl("/chat"+"/" + 1L + GET_MASSAGES_URL)
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void getUsers_Positive(){
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(GET_USERS_URL)
                .setHttpMethod(HttpMethod.GET)
                .setHeaders(sessionHeaders)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNull(apiResponse.getError());
    }

    @Test
    public void updateMessagesAsRead_Positive() {
        List<HttpHeaders> sessionHeaders = addSessionIdToHeader(VALID_SESSION_ID);
        ControllerRequest request = ControllerRequest.builder()
                .setUrl(UPDATE_MESSAGES_AS_READ_URL)
                .setHttpMethod(HttpMethod.PUT)
                .setHeaders(sessionHeaders)
                .setRequestObject(readMessagesRequest)
                .build();
        APIWebResponse apiResponse = getResponse(request);

        Assert.assertNull(apiResponse.getError());

    }

}
