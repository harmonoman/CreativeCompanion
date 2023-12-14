package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.dynamodb.ProjectDao;
import com.nashss.se.creativecompanion.dynamodb.models.Project;
import com.nashss.se.creativecompanion.activity.request.CreateProjectRequest;
import com.nashss.se.creativecompanion.activity.result.CreateProjectResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class CreateProjectActivityTest {
    @Mock
    private ProjectDao projectDao;

    private CreateProjectActivity createProjectActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        createProjectActivity = new CreateProjectActivity(projectDao);
    }

    @Test
    public void handleRequest_withValidInputs_createsAndSavesNewProject() {
        // GIVEN
        String expectedUserId = "expectedUserId";
        String expectedProjectName = "expectedProjectName";

        CreateProjectRequest request = CreateProjectRequest.builder()
                .withUserId(expectedUserId)
                .withProjectName(expectedProjectName)
                .build();

        // WHEN
        CreateProjectResult result = createProjectActivity.handleRequest(request);

        // THEN
        verify(projectDao).saveProject(any(Project.class));

        assertEquals(expectedUserId, result.getProject().getUserId());
        assertEquals(expectedProjectName, result.getProject().getProjectName());
    }
}
