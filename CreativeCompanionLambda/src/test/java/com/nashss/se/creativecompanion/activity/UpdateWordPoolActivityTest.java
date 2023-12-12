package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.activity.UpdateWordPoolActivity;
import com.nashss.se.creativecompanion.dynamodb.WordPoolDao;
import com.nashss.se.creativecompanion.dynamodb.models.WordPool;
import com.nashss.se.creativecompanion.requests.UpdateWordPoolRequest;
import com.nashss.se.creativecompanion.results.UpdateWordPoolResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.nashss.se.creativecompanion.metrics.MetricsPublisher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class UpdateWordPoolActivityTest {

    @Mock
    private WordPoolDao wordPoolDao;

    @Mock
    private MetricsPublisher metricsPublisher;

    private UpdateWordPoolActivity updateWordPoolActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        updateWordPoolActivity = new UpdateWordPoolActivity(wordPoolDao, metricsPublisher);
    }

    @Test
    public void handleRequest_withValidInputs_updatesWordPool() {

        // GIVEN
        String expectedUserId = "expectedUserId";
        String expectedWordPoolId = "expectedWordPoolId";
        String expectedWordPoolName = "expectedWordPoolName";
        List<String> wordPool = List.of("this", "is", "a" , "wordPool", "list");
        List<String> workspace = List.of("this", "is", "a" , "workspace", "list");

        UpdateWordPoolRequest request = UpdateWordPoolRequest.builder()
                .withUserId(expectedUserId)
                .withWordPoolId(expectedWordPoolId)
                .withWordPoolName(expectedWordPoolName)
                .withWordPool(wordPool)
                .build();

        WordPool newWordPool = new WordPool();
        newWordPool.setUserId(expectedUserId);
        newWordPool.setWordPoolId(expectedWordPoolId);
        newWordPool.setWordPoolName(expectedWordPoolName);
        newWordPool.setWordPool(wordPool);

        when(wordPoolDao.getWordPool(expectedUserId, expectedWordPoolId)).thenReturn(newWordPool);
        when(wordPoolDao.saveWordPool(newWordPool)).thenReturn(newWordPool);

        // WHEN
        UpdateWordPoolResult result = updateWordPoolActivity.handleRequest(request);

        // THEN
        verify(wordPoolDao).saveWordPool(any(WordPool.class));

        //assertNotNull(result.getProject().getUserId());
        assertEquals(expectedUserId, result.getWordPool().getUserId());
        assertEquals(expectedWordPoolId, result.getWordPool().getWordPoolId());
        assertEquals(expectedWordPoolName, result.getWordPool().getWordPoolName());
        assertEquals(wordPool, result.getWordPool().getWordPool());
    }
}
