package com.nashss.se.creativecompanion;

import com.nashss.se.creativecompanion.activity.GetWordPoolActivity;
import com.nashss.se.creativecompanion.dynamodb.WordPoolDao;
import com.nashss.se.creativecompanion.dynamodb.models.WordPool;
import com.nashss.se.creativecompanion.requests.GetWordPoolRequest;
import com.nashss.se.creativecompanion.results.GetWordPoolResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
public class GetWordPoolActivityTest {

    @Mock
    private WordPoolDao wordPoolDao;

    private GetWordPoolActivity getWordPoolActivity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        getWordPoolActivity = new GetWordPoolActivity(wordPoolDao);
    }

    @Test
    public void handleRequest_savedWordPoolFound_returnsWordPoolModelInResult() {
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

        when(wordPoolDao.getWordPool(expectedUserId,expectedWordPoolId)).thenReturn(wordPool);

        GetWordPoolRequest request = GetWordPoolRequest.builder()
            .withUserId(expectedUserId)
            .withWordPoolId(expectedWordPoolId)
            .build();

        // WHEN
        GetWordPoolResult result = getWordPoolActivity.handleRequest(request);

        // THEN
        assertEquals(expectedUserId, result.getWordPool().getUserId());
        assertEquals(expectedWordPoolId, result.getWordPool().getWordPoolId());
        assertEquals(expectedWordPoolName, result.getWordPool().getWordPoolName());
        assertEquals(expectedWordPool, result.getWordPool().getWordPool());
    }
}
