package com.nashss.se.creativecompanion;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.creativecompanion.dynamodb.ProjectDao;
import com.nashss.se.creativecompanion.dynamodb.models.Project;
import com.nashss.se.creativecompanion.exceptions.ProjectNotFoundException;
import com.nashss.se.creativecompanion.metrics.MetricsConstants;
import com.nashss.se.creativecompanion.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

public class ProjectDaoTest {

    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @Mock
    private MetricsPublisher metricsPublisher;


    private ProjectDao projectDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        projectDao = new ProjectDao(dynamoDBMapper, metricsPublisher);
    }

    @Test
    public void getProject_withUserIdAndProjectId_callsMapperWithPartitionKey() {
        // GIVEN
        String userId = "userId";
        String projectId = "projectId";
        when(dynamoDBMapper.load(Project.class, userId,projectId)).thenReturn(
                new Project());

        // WHEN
        Project project = projectDao.getProject(userId, projectId);

        // THEN
        assertNotNull(project);
        verify(dynamoDBMapper).load(Project.class, userId, projectId);
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETPROJECT_PROJECTNOTFOUND_COUNT),
                anyDouble());

    }

    @Test
    public void getProject_projectIdNotFound_throwsProjectNotFoundException() {
        // GIVEN
        String nonexistentUserId = "NotReal";
        String nonexistentProjectId = "NotReal";
        when(dynamoDBMapper.load(Project.class,
                nonexistentUserId,nonexistentProjectId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(ProjectNotFoundException.class, () -> projectDao.getProject(
                nonexistentUserId, nonexistentProjectId));
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETPROJECT_PROJECTNOTFOUND_COUNT),
                anyDouble());
    }

    @Test
    public void saveProject_callsMapperWithProject() {
        // GIVEN
        Project project = new Project();

        // WHEN
        Project result = projectDao.saveProject(project);

        // THEN
        verify(dynamoDBMapper).save(project);
        assertEquals(project, result);
    }

}
