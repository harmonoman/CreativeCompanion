package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.dynamodb.WordPoolDao;
import com.nashss.se.creativecompanion.dynamodb.models.WordPool;
import com.nashss.se.creativecompanion.activity.request.CreateWordPoolRequest;
import com.nashss.se.creativecompanion.activity.result.CreateWordPoolResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;
public class CreateWordPoolActivityTest {

    @Mock
    private WordPoolDao wordPoolDao;

    private CreateWordPoolActivity createWordPoolActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        createWordPoolActivity = new CreateWordPoolActivity(wordPoolDao);
    }

    @Test
    public void handleRequest_withValidInputs_createsAndSavesNewWordPool() {
        // GIVEN
        String expectedUserId = "expectedUserId";
        String expectedWordPoolName = "expectedWordPoolName";

        CreateWordPoolRequest request = CreateWordPoolRequest.builder()
                .withUserId(expectedUserId)
                .withWordPoolName(expectedWordPoolName)
                .build();

        // WHEN
        CreateWordPoolResult result = createWordPoolActivity.handleRequest(request);

        // THEN
        verify(wordPoolDao).saveWordPool(any(WordPool.class));

        assertEquals(expectedUserId, result.getWordPool().getUserId());
        assertEquals(expectedWordPoolName, result.getWordPool().getWordPoolName());
    }
}
