package com.shutafin.model.web.matching;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by evgeny on 3/29/2018.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MatchedUsersScoresSearchResponse {
    private Map<Long, Integer> matchedUsersScoresPerPage;
    private Integer totalUsers;
}
