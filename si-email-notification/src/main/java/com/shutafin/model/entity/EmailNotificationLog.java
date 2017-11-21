package com.shutafin.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "EMAIL_NOTIFICATION_LOG")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailNotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID", nullable = true)
    private Long userId;

    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;

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

    @CreationTimestamp
    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @UpdateTimestamp
    @Column(name = "UPDATED_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

}
