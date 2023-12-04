package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.dynamodb.ProjectDao;
import com.nashss.se.creativecompanion.dynamodb.models.Project;
import com.nashss.se.creativecompanion.requests.UpdateProjectRequest;
import com.nashss.se.creativecompanion.results.UpdateProjectResult;
import com.nashss.se.creativecompanion.dynamodb.models.ModelConverter;
import com.nashss.se.creativecompanion.metrics.MetricsPublisher;
import com.nashss.se.creativecompanion.dynamodb.models.ProjectModel;

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
     * @param projectDao
     * @param metricsPublisher
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
        log.info("Received UpdateProjectRequest {}", updateProjectRequest);

        Project project = new Project();
        project.setUserId(updateProjectRequest.getUserId());
        project.setProjectId(updateProjectRequest.getProjectId());
        System.out.println("***** projectId in UpdateProjectActivity *****: " + updateProjectRequest.getProjectId());
        project.setProjectName(updateProjectRequest.getProjectName());
        System.out.println("***** projectName in UpdateProjectActivity *****: " + updateProjectRequest.getProjectName());
        project.setWordPool(updateProjectRequest.getWordPool());
        project.setWorkspace(updateProjectRequest.getWorkspace());
        project = projectDao.saveProject(project);

        ProjectModel projectModel = new ModelConverter().toProjectModel(project);

        return UpdateProjectResult.builder()
                .withProject(projectModel)
                .build();
    }
}
