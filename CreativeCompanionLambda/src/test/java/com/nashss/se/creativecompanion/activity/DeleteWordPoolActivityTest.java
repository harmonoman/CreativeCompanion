package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.dynamodb.WordPoolDao;
import com.nashss.se.creativecompanion.activity.request.DeleteWordPoolRequest;
import com.nashss.se.creativecompanion.activity.result.DeleteWordPoolResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeleteWordPoolActivityTest {

    @Mock
    private WordPoolDao wordPoolDao;

    @InjectMocks
    private DeleteWordPoolActivity deleteWordPoolActivity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void handleRequest_wordPoolFoundAndDeleted_returnsSuccessResult() {
        // GIVEN
        String expectedUserId = "expectedUserId";
        String expectedWordPoolId = "expectedWordPoolId";

        when(wordPoolDao.deleteWordPool(expectedUserId, expectedWordPoolId)).thenReturn(true);

        DeleteWordPoolRequest request = DeleteWordPoolRequest.builder()
                .withUserId(expectedUserId)
                .withWordPoolId(expectedWordPoolId)
                .build();

        // WHEN
        DeleteWordPoolResult result = deleteWordPoolActivity.handleRequest(request);

        // THEN
        assertTrue(result.isSuccess());
        verify(wordPoolDao).deleteWordPool(expectedUserId, expectedWordPoolId);
    }

    @Test
    public void handleRequest_wordPoolNotFound_returnsFailureResult() {
        // GIVEN
        String expectedUserId = "expectedUserId";
        String expectedWordPoolId = "expectedWordPoolId";

        when(wordPoolDao.deleteWordPool(expectedUserId, expectedWordPoolId)).thenReturn(false);

        DeleteWordPoolRequest request = DeleteWordPoolRequest.builder()
                .withUserId(expectedUserId)
                .withWordPoolId(expectedWordPoolId)
                .build();

        // WHEN
        DeleteWordPoolResult result = deleteWordPoolActivity.handleRequest(request);

        // THEN
        assertFalse(result.isSuccess());
        verify(wordPoolDao).deleteWordPool(expectedUserId, expectedWordPoolId);
    }
}