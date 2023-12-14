package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.dynamodb.WordPoolDao;
import com.nashss.se.creativecompanion.dynamodb.models.WordPool;
import com.nashss.se.creativecompanion.activity.request.GetWordPoolListRequest;
import com.nashss.se.creativecompanion.activity.result.GetWordPoolListResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GetWordPoolListActivityTest {

    @Mock
    private WordPoolDao wordPoolDao;

    private GetWordPoolListActivity getWordPoolListActivity;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        getWordPoolListActivity = new GetWordPoolListActivity(wordPoolDao);
    }

    @Test
    void handleRequest_userIdWithWordPools_returnsListOfWordPools() {
        // GIVEN
        WordPool wordPool = new WordPool();
        WordPool wordPool2 = new WordPool();
        String userId = "expectedUserID";
        wordPool.setUserId(userId);
        wordPool2.setUserId(userId);

        List<String> words = List.of("words");
        wordPool.setWordPool(words);
        List<String> words2 = List.of("words");
        wordPool2.setWordPool(words2);

        List<WordPool> wordPoolList = new ArrayList<>();
        wordPoolList.add(wordPool);
        wordPoolList.add(wordPool2);


        GetWordPoolListRequest request = GetWordPoolListRequest.builder()
                .withUserId(userId)
                .build();

        when(wordPoolDao.getUserWordPools(request.getUserId())).thenReturn(wordPoolList);

        // WHEN
        GetWordPoolListResult result = getWordPoolListActivity.handleRequest(request);

        // THEN
        assertEquals(wordPoolList.get(0).getUserId(), result.getWordPools().get(0).getUserId());
    }
}
