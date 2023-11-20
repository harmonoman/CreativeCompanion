package com.nashss.se.creativecompanion;

import com.nashss.se.creativecompanion.activity.UpdateProjectActivity;
import com.nashss.se.creativecompanion.dynamodb.ProjectDao;
import com.nashss.se.creativecompanion.dynamodb.models.Project;
import com.nashss.se.creativecompanion.requests.UpdateProjectRequest;
import com.nashss.se.creativecompanion.results.UpdateProjectResult;
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

public class UpdateProjectActivityTest {

    @Mock
    private ProjectDao projectDao;

    @Mock
    private MetricsPublisher metricsPublisher;

    private UpdateProjectActivity updateProjectActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        updateProjectActivity = new UpdateProjectActivity(projectDao, metricsPublisher);
    }

    @Test
    public void handleRequest_withValidInputs_updatesProject() {

        // GIVEN
        String expectedUserId = "expectedUserId";
        String expectedProjectId = "expectedProjectId";
        String expectedProjectName = "expectedProjectName";
        List<String> wordPool = List.of("this", "is", "a" , "wordPool", "list");
        List<String> workspace = List.of("this", "is", "a" , "workspace", "list");

        UpdateProjectRequest request = UpdateProjectRequest.builder()
                .withUserId(expectedUserId)
                .withProjectId(expectedProjectId)
                .withProjectName(expectedProjectName)
                .withWordPool(wordPool)
                .withWorkspace(workspace)
                .build();

        Project newProject = new Project();
        newProject.setUserId(expectedUserId);
        newProject.setProjectId(expectedProjectId);
        newProject.setProjectName(expectedProjectName);

        when(projectDao.getProject(expectedUserId, expectedProjectId)).thenReturn(newProject);
        when(projectDao.saveProject(newProject)).thenReturn(newProject);

        // WHEN
        UpdateProjectResult result = updateProjectActivity.handleRequest(request);

        // THEN
        verify(projectDao).saveProject(any(Project.class));

        //assertNotNull(result.getProject().getUserId());
        assertEquals(expectedUserId, result.getProject().getUserId());
        assertEquals(expectedProjectId, result.getProject().getProjectId());
        assertEquals(expectedProjectName, result.getProject().getProjectName());
        assertEquals(wordPool, result.getProject().getWordPool());
        assertEquals(workspace, result.getProject().getWorkspace());
    }
}
