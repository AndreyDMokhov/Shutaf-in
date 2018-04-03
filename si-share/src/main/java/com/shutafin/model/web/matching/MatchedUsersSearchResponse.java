package com.shutafin.model.web.matching;

import com.shutafin.model.web.common.UserSearchResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by evgeny on 3/28/2018.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MatchedUsersSearchResponse {
    private List<UserSearchResponse> matchedUsersPerPage;
    private Integer totalUsers;
}
