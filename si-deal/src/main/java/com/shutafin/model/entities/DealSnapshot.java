package com.shutafin.model.entities;

import com.shutafin.model.base.AbstractEntity;
import com.shutafin.model.util.DealSnapshotInfo;
import com.shutafin.model.util.DealSnapshotInfoConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "DEAL_SNAPSHOT")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DealSnapshot extends AbstractEntity {

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "DEAL_SNAPSHOT_INFO", nullable = false)
    @Convert(converter = DealSnapshotInfoConverter.class)
    private DealSnapshotInfo dealSnapshotInfo;
}
