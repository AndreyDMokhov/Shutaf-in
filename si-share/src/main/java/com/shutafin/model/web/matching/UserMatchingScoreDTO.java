package com.shutafin.model.web.matching;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserMatchingScoreDTO {

    private Long userOriginId;

    private Long userToMatchId;

    private Integer score;

    private Date createdDate;

    private Date updatedDate;
}
