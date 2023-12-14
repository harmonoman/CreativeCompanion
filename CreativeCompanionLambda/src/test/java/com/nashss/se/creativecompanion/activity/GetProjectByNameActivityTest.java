package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.dynamodb.ProjectDao;
import com.nashss.se.creativecompanion.dynamodb.models.Project;
import com.nashss.se.creativecompanion.activity.request.GetProjectByNameRequest;
import com.nashss.se.creativecompanion.activity.result.GetProjectByNameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GetProjectByNameActivityTest {

    @Mock
    private ProjectDao projectDao;

    private GetProjectByNameActivity getProjectByNameActivity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        getProjectByNameActivity = new GetProjectByNameActivity(projectDao);
    }

    @Test
    public void handleRequest_savedProjectFoundByName_returnsProjectModelInResult() {
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

        when(projectDao.getProjectByName(expectedUserId, expectedProjectName)).thenReturn(project);

        GetProjectByNameRequest request = GetProjectByNameRequest.builder()
                .withUserId(expectedUserId)
                .withProjectName(expectedProjectName)
                .build();

        // WHEN
        GetProjectByNameResult result = getProjectByNameActivity.handleRequest(request);

        // THEN
        assertEquals(expectedUserId, result.getProjectByName().getUserId());
        assertEquals(expectedProjectId, result.getProjectByName().getProjectId());
        assertEquals(expectedProjectName, result.getProjectByName().getProjectName());
        assertEquals(expectedWordPool, result.getProjectByName().getWordPool());
        assertEquals(expectedWorkspace, result.getProjectByName().getWorkspace());
    }
}
