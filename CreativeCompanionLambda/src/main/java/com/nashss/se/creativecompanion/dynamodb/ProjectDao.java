package com.nashss.se.creativecompanion.dynamodb;

import com.nashss.se.creativecompanion.dynamodb.models.Project;
import com.nashss.se.creativecompanion.exceptions.ProjectNotFoundException;
import com.nashss.se.creativecompanion.metrics.MetricsConstants;
import com.nashss.se.creativecompanion.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for a project using {@link Project} to represent the model in DynamoDB.
 */
@Singleton
public class ProjectDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a ProjectDao object.
     *
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the projects table
     * @param metricsPublisher the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public ProjectDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Returns the {@link Project} corresponding to the specified id.
     *
     * @param userId    the User ID
     * @param projectId the Project ID
     * @return the stored Project, or null if none was found.
     */
    public Project getProject(String userId, String projectId) {
        Project project = this.dynamoDbMapper.load(Project.class, userId, projectId);

        if (project == null) {
            metricsPublisher.addCount(MetricsConstants.GETPROJECT_PROJECTNOTFOUND_COUNT, 1);
            throw new ProjectNotFoundException("Could not find project with id " + projectId);

        }
        metricsPublisher.addCount(MetricsConstants.GETPROJECT_PROJECTNOTFOUND_COUNT, 0);
        return project;
    }

    /**
     * Saves (creates or updates) the given project.
     *
     * @param project The project to save
     * @return The Project object that was saved
     */
    public Project saveProject(Project project) {
        System.out.println("inside ProjectDao; project: " + project);
        this.dynamoDbMapper.save(project);
        return project;
    }

    /**
     * Perform a search (via a "scan") of the projects table for projects matching the given criteria.
     *
     * @param userId a String containing the UserId.
     * @return a List of Project objects that were made by the User.
     */
    public List<Project> getUserProjects(String userId) {
        Project project = new Project();
        project.setUserId(userId);

        DynamoDBQueryExpression<Project> dynamoDBQueryExpression = new DynamoDBQueryExpression<Project>()
                .withHashKeyValues(project);
        DynamoDBMapper mapper = new DynamoDBMapper(DynamoDBClientProvider.getDynamoDBClient());

        PaginatedQueryList<Project> projectList = mapper.query(Project.class, dynamoDBQueryExpression);
        return projectList;
    }

    /**
     * Deletes the project corresponding to the specified id.
     *
     * @param userId    the User ID
     * @param projectId the Project ID
     * @return true if the deletion was successful, false otherwise
     */
    public boolean deleteProject(String userId, String projectId) {
        // Check if the project exists before deletion
        Project project = getProject(userId, projectId);
        if (project != null) {
            this.dynamoDbMapper.delete(project);
            // Deletion successful
            return true;
        } else {
            // Project not found, deletion unsuccessful
            return false;
        }
    }

    public Project getProjectByName(String userId, String projectName) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":userId", new AttributeValue().withS(userId));
        valueMap.put(":projectName", new AttributeValue().withS(projectName));
        DynamoDBQueryExpression<Project> queryExpression = new DynamoDBQueryExpression<Project>()
                .withIndexName(Project.PROJECT_NAME_INDEX)
                .withConsistentRead(false)
                .withKeyConditionExpression("userId = :userId AND projectName = :projectName")
                .withExpressionAttributeValues(valueMap)
                .withLimit(1);

        PaginatedQueryList<Project> queryResult = dynamoDbMapper.query(Project.class,queryExpression);

        if (!queryResult.isEmpty()) {
            return queryResult.get(0);
        } else {
            return null;
        }
    }
}
