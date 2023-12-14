package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.dynamodb.ProjectDao;
import com.nashss.se.creativecompanion.dynamodb.models.Project;
import com.nashss.se.creativecompanion.activity.request.GetProjectRequest;
import com.nashss.se.creativecompanion.activity.result.GetProjectResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GetProjectActivityTest {

    @Mock
    private ProjectDao projectDao;

    private GetProjectActivity getProjectActivity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        getProjectActivity = new GetProjectActivity(projectDao);
    }

    @Test
    public void handleRequest_savedProjectFound_returnsProjectModelInResult() {
        // GIVEN
        String expectedUserId = "expectedUserId";
        String expectedProjectId = "expectedProjectId";
        String expectedProjectName = "expectedProjectName";
        List<String> expectedWordPool = List.of("words");
        List<String> expectedWorkspace = List.of("workspace");

        Project project = new Project();
        project.setUserId(expectedUserId);
        project.setProjectId(expectedProjectId);
        project.setProjectName(expectedProjectName);
        project.setWordPool(expectedWordPool);
        project.setWorkspace(expectedWorkspace);

        when(projectDao.getProject(expectedUserId, expectedProjectId)).thenReturn(project);

        GetProjectRequest request = GetProjectRequest.builder()
                .withUserId(expectedUserId)
                .withProjectId(expectedProjectId)
                .build();

        // WHEN
        GetProjectResult result = getProjectActivity.handleRequest(request);

        // THEN
        assertEquals(expectedUserId, result.getProject().getUserId());
        assertEquals(expectedProjectId, result.getProject().getProjectId());
        assertEquals(expectedProjectName, result.getProject().getProjectName());
        assertEquals(expectedWordPool, result.getProject().getWordPool());
        assertEquals(expectedWorkspace, result.getProject().getWorkspace());
    }
}
