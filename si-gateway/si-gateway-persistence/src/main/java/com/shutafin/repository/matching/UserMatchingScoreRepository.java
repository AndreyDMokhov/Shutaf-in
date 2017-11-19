package com.shutafin.repository.matching;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.matching.UserMatchingScore;
import com.shutafin.repository.base.BaseJpaRepository;

import java.util.List;
@Deprecated
public interface UserMatchingScoreRepository extends BaseJpaRepository<UserMatchingScore, Long> {

    UserMatchingScore findByUserOriginAndUserToMatch(User userOrigin, User userToMatch);
    List<UserMatchingScore> findByUserOrigin(User userOrigin);
    Long deleteAllByUserOriginOrUserToMatch(User userOrigin, User userToMatch);

}
