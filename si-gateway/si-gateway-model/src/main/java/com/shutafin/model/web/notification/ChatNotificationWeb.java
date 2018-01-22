package com.shutafin.model.web.notification;

import com.shutafin.model.web.chat.ChatWithUsersListDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatNotificationWeb {

    public ChatWithUsersListDTO chatWithUsersListDTO;
    public NotificationReason notificationReason;
}
