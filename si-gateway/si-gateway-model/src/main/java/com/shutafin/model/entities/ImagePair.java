package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Deprecated
@Entity
@Table(name = "IMAGE_PAIR")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImagePair extends AbstractEntity {

    @JoinColumn(name = "ORIGINAL_IMAGE_ID")
    @OneToOne
    private UserImage originalImage;

    @JoinColumn(name = "COMPRESSED_IMAGE_ID")
    @OneToOne
    private UserImage compressedImage;

}
