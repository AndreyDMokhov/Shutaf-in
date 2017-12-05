package com.shutafin.model.web.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatUserDTO {

    private Long userId;

    private String firstName;

    private String lastName;
}
