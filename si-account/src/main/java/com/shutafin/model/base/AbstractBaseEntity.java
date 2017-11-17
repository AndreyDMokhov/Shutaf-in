package com.shutafin.model.base;

import lombok.*;

import javax.persistence.*;


@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;



}
