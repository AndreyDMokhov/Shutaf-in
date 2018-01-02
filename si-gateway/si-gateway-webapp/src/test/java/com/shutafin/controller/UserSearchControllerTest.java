package com.shutafin.controller;


import com.shutafin.service.UserSearchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserSearchControllerTest {

    @MockBean
    private UserSearchService userSearchService;

    @Before
    public void setUp(){
        Mockito.when(userSearchService.userSearchByMap(Mockito.anyLong(), Mockito.anyMap(), Mockito.anyString()))
                .thenReturn(Mockito.anyList());
//        List<UserSearchResponse>

    }

    @Test
    public void getMatchingUsers_Positive(){


    }

}
