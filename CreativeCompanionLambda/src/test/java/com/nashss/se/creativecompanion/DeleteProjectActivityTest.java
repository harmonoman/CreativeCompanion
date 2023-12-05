package com.nashss.se.creativecompanion;

import com.nashss.se.creativecompanion.activity.DeleteProjectActivity;
import com.nashss.se.creativecompanion.dynamodb.ProjectDao;
import com.nashss.se.creativecompanion.requests.DeleteProjectRequest;
import com.nashss.se.creativecompanion.results.DeleteProjectResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeleteProjectActivityTest {

    @Mock
    private ProjectDao projectDao;

    @InjectMocks
    private DeleteProjectActivity deleteProjectActivity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void handleRequest_projectFoundAndDeleted_returnsSuccessResult() {
        // GIVEN
        String expectedUserId = "expectedUserId";
        String expectedProjectId = "expectedProjectId";

        when(projectDao.deleteProject(expectedUserId, expectedProjectId)).thenReturn(true);

        DeleteProjectRequest request = DeleteProjectRequest.builder()
                .withUserId(expectedUserId)
                .withProjectId(expectedProjectId)
                .build();

        // WHEN
        DeleteProjectResult result = deleteProjectActivity.handleRequest(request);

        // THEN
        assertTrue(result.isSuccess());
        verify(projectDao).deleteProject(expectedUserId, expectedProjectId);
    }

    @Test
    public void handleRequest_projectNotFound_returnsFailureResult() {
        // GIVEN
        String expectedUserId = "expectedUserId";
        String expectedProjectId = "expectedProjectId";

        when(projectDao.deleteProject(expectedUserId, expectedProjectId)).thenReturn(false);

        DeleteProjectRequest request = DeleteProjectRequest.builder()
                .withUserId(expectedUserId)
                .withProjectId(expectedProjectId)
                .build();

        // WHEN
        DeleteProjectResult result = deleteProjectActivity.handleRequest(request);

        // THEN
        assertFalse(result.isSuccess());
        verify(projectDao).deleteProject(expectedUserId, expectedProjectId);
    }
}