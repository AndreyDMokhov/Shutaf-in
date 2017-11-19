package com.shutafin.model.entity;

import com.shutafin.model.base.AbstractEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "EMAIL_NOTIFICATION_LOG")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailNotificationLog extends AbstractEntity {

    @Column(name = "USER_ID", nullable = true)
    private Long userId;

    @Column(name = "EMAIL_TO", nullable = false, length = 100)
    private String emailTo;

    @Column(name = "IS_SEND_FAILED")
    private Boolean isSendFailed;

    @Column(name = "EMAIL_HEADER", nullable = false)
    private String emailHeader;

    @Column(name = "EMAIL_CONTENT", nullable = false)
    @Lob
    private String emailContent;

    @Column(name = "EMAIL_REASON_ID", nullable = false)
    @Convert(converter = EmailReasonConverter.class)
    private EmailReason emailReason;

}
