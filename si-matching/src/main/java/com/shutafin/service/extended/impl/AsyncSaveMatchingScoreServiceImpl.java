package com.shutafin.service.extended.impl;

import com.shutafin.model.entities.extended.UserMatchingScore;
import com.shutafin.repository.extended.UserMatchingScoreRepository;
import com.shutafin.service.extended.AsyncSaveMatchingScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by evgeny on 2/14/2018.
 */
@Service
@Slf4j
public class AsyncSaveMatchingScoreServiceImpl implements AsyncSaveMatchingScoreService {

    @Autowired
    private UserMatchingScoreRepository userMatchingScoreRepository;

    @Override
    @Async
    public void saveMatchingScores(Collection<UserMatchingScore> matchingScores) {
        userMatchingScoreRepository.save(matchingScores);
    }
}
