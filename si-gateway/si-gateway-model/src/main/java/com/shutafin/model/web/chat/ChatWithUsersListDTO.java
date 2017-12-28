package com.shutafin.model.web.chat;

import com.shutafin.model.web.account.AccountUserWeb;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatWithUsersListDTO {

    public ChatWithUsersListDTO(Long id, String chatTitle, Boolean hasNoTitle) {
        this.id = id;
        this.chatTitle = chatTitle;
        this.hasNoTitle = hasNoTitle;
    }

    private Long id;

    private String chatTitle;

    private Boolean hasNoTitle;

    private List<AccountUserWeb> usersInChat;
}
