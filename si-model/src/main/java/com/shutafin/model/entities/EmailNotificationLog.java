package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;
import com.shutafin.model.entities.types.EmailReason;
import com.shutafin.model.entities.types.EmailReasonConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
@Entity
@Table(name = "EMAIL_NOTIFICATION_LOG")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailNotificationLog extends AbstractEntity {

    @JoinColumn(name = "USER_ID", nullable = true)
    @ManyToOne
    private User user;

    @Column(name = "EMAIL_TO", nullable = false, length = 100)
    private String emailTo;

    @Column(name = "IS_SEND_FAILED")
    private Boolean isSendFailed;

    @Column(name = "EMAIL_CONTENT", nullable = false)
    @Lob
    private String emailContent;

    @Column(name = "EMAIL_REASON_ID", nullable = false)
    @Convert(converter = EmailReasonConverter.class)
    private EmailReason emailReason;

}
