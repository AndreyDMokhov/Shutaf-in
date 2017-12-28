package com.shutafin.model.entities;


import com.shutafin.model.AbstractEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "EMAIL_IMAGE_SOURCE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Deprecated
public class EmailImageSource extends AbstractEntity {

    @JoinColumn(name = "EMAIL_NOTIFICATION_LOG_ID", nullable = false)
    @ManyToOne
    private EmailNotificationLog emailNotificationLog;

    @Column(name = "CONTENT_ID", nullable = false, length = 20)
    private String contentId;

    @Column(name = "IMAGE_SOURCE", nullable = false)
    private byte[] imageSource;
}
