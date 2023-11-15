package com.nashss.se.creativecompanion;

import com.nashss.se.creativecompanion.activity.CreateProjectActivity;
import com.nashss.se.creativecompanion.dynamodb.ProjectDao;
import com.nashss.se.creativecompanion.dynamodb.models.Project;
import com.nashss.se.creativecompanion.requests.CreateProjectRequest;
import com.nashss.se.creativecompanion.results.CreateProjectResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        //List<String> wordPool = List.of("this", "is", "a" , "wordPool", "list");
        //List<String> workspace = List.of("this", "is", "a" , "workspace", "list");

        CreateProjectRequest request = CreateProjectRequest.builder()
                .withProjectName(expectedProjectName)
                .withUserId(expectedUserId)
                .build();

        // WHEN
        CreateProjectResult result = createProjectActivity.handleRequest(request);

        // THEN
        verify(projectDao).saveProject(any(Project.class));

        //assertNotNull(result.getProject().getUserId());
        assertEquals(expectedUserId, result.getProject().getUserId());
        assertEquals(expectedProjectName, result.getProject().getProjectName());

    }










}
