package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.converters.ModelConverter;
import com.nashss.se.creativecompanion.dynamodb.ProjectDao;
import com.nashss.se.creativecompanion.dynamodb.models.Project;
import com.nashss.se.creativecompanion.metrics.MetricsPublisher;
import com.nashss.se.creativecompanion.models.ProjectModel;
import com.nashss.se.creativecompanion.activity.request.UpdateProjectRequest;
import com.nashss.se.creativecompanion.activity.result.UpdateProjectResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the UpdateProjectActivity for the CreativeCompanionService's UpdateProject API.
 *
 * This API allows the customer to update their saved project's information.
 */
public class UpdateProjectActivity {
    private final Logger log = LogManager.getLogger();
    private final ProjectDao projectDao;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a new UpdateProjectActivity object.
     *
     * @param projectDao projectDao that is being injected
     * @param metricsPublisher metricsPublisher that is being injected
     */
    @Inject
    public UpdateProjectActivity(ProjectDao projectDao, MetricsPublisher metricsPublisher) {
        this.projectDao = projectDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * This method handles the incoming request by retrieving the project, updating it,
     * and persisting the project.
     * <p>
     * It then returns the updated project.
     * <p>
     * If the project does not exist, this should throw a ProjectNotFoundException.
     * <p>
     *
     * @param updateProjectRequest request object containing the project ID, project name, and user ID
     *                              associated with it
     * @return updateProjectResult result object containing the API defined {@link ProjectModel}
     */
    public UpdateProjectResult handleRequest(final UpdateProjectRequest updateProjectRequest) {

        long startTime = System.currentTimeMillis();

        log.info("Received UpdateProjectRequest {}", updateProjectRequest);

        Project project = new Project();
        project.setUserId(updateProjectRequest.getUserId());
        project.setProjectId(updateProjectRequest.getProjectId());
        project.setProjectName(updateProjectRequest.getProjectName());
        project.setWordPool(updateProjectRequest.getWordPool());
        project.setWorkspace(updateProjectRequest.getWorkspace());
        project = projectDao.saveProject(project);

        ProjectModel projectModel = new ModelConverter().toProjectModel(project);

        long endTime = System.currentTimeMillis();
        double elapsedTime = endTime - startTime;
        metricsPublisher.addTime("UpdateProjectHandlingTime", elapsedTime);

        return UpdateProjectResult.builder()
                .withProject(projectModel)
                .build();
    }
}
