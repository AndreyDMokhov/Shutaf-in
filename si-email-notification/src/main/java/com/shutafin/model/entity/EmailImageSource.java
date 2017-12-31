package com.shutafin.model.entity;

import com.shutafin.model.base.AbstractEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "EMAIL_IMAGE_SOURCE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailImageSource extends AbstractEntity {

    @JoinColumn(name = "EMAIL_NOTIFICATION_LOG_ID", nullable = false)
    @ManyToOne
    private EmailNotificationLog emailNotificationLog;

    @Column(name = "CONTENT_ID", nullable = false, length = 20)
    private String contentId;

    @Column(name = "IMAGE_SOURCE", nullable = false, length = 100000)
    private byte[] imageSource;

}
