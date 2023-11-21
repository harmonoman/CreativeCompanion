package com.nashss.se.creativecompanion;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.nashss.se.creativecompanion.activity.GetProjectListActivity;
import com.nashss.se.creativecompanion.dynamodb.ProjectDao;
import com.nashss.se.creativecompanion.dynamodb.models.Project;
import com.nashss.se.creativecompanion.requests.GetProjectListRequest;
import com.nashss.se.creativecompanion.results.GetProjectListResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GetProjectListActivityTest {

    @Mock
    private ProjectDao projectDao;

    private GetProjectListActivity getProjectListActivity;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        getProjectListActivity = new GetProjectListActivity(projectDao);
    }

    @Test
    void handleRequest_userIdWithProjects_returnsListOfProjects() {
        // GIVEN
        Project project = new Project();
        Project project2 = new Project();
        String userId = "expectedUserID";
        project.setUserId(userId);
        project2.setUserId(userId);

        List<String> wordPool = List.of("words");
        project.setWordPool(wordPool);
        List<String> wordPool2 = List.of("words");
        project2.setWordPool(wordPool2);

        List<String> workspace = List.of("words");
        project.setWorkspace(workspace);
        List<String> workspace2 = List.of("words");
        project2.setWorkspace(workspace2);



        List<Project> projectList = new ArrayList<>();
        projectList.add(project);
        projectList.add(project2);


        GetProjectListRequest request = GetProjectListRequest.builder()
                .withUserId(userId)
                .build();

        when(projectDao.getUserProjects(request.getUserId())).thenReturn(projectList);

        // WHEN
        GetProjectListResult result = getProjectListActivity.handleRequest(request);

        // THEN
        assertEquals(projectList.get(0).getUserId(), result.getProjects().get(0).getUserId());
    }




}
