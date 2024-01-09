package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.dynamodb.WordPoolDao;
import com.nashss.se.creativecompanion.dynamodb.models.WordPool;
import com.nashss.se.creativecompanion.activity.request.GetWordPoolByNameRequest;
import com.nashss.se.creativecompanion.activity.result.GetWordPoolByNameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GetWordPoolByNameActivityTest {

    @Mock
    private WordPoolDao wordPoolDao;

    private GetWordPoolByNameActivity getWordPoolByNameActivity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        getWordPoolByNameActivity = new GetWordPoolByNameActivity(wordPoolDao);
    }

    @Test
    public void handleRequest_savedWordPoolFoundByName_returnsWordPoolModelInResult() {
        // GIVEN
        String expectedUserId = "expectedUserId";
        String expectedWordPoolId = "expectedWordPoolId";
        String expectedWordPoolName = "expectedWordPoolName";
        List<String> expectedWordPool = List.of("words");



        WordPool wordPool = new WordPool();
        wordPool.setUserId(expectedUserId);
        wordPool.setWordPoolId(expectedWordPoolId);
        wordPool.setWordPoolName(expectedWordPoolName);
        wordPool.setWordPool(expectedWordPool);


        when(wordPoolDao.getWordPoolByName(expectedUserId, expectedWordPoolName)).thenReturn(wordPool);

        GetWordPoolByNameRequest request = GetWordPoolByNameRequest.builder()
                .withUserId(expectedUserId)
                .withWordPoolName(expectedWordPoolName)
                .build();

        // WHEN
        GetWordPoolByNameResult result = getWordPoolByNameActivity.handleRequest(request);

        // THEN
        assertEquals(expectedUserId, result.getWordPoolByName().getUserId());
        assertEquals(expectedWordPoolId, result.getWordPoolByName().getWordPoolId());
        assertEquals(expectedWordPoolName, result.getWordPoolByName().getWordPoolName());
        assertEquals(expectedWordPool, result.getWordPoolByName().getWordPool());

    }
}
