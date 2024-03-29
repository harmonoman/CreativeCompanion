package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.activity.request.GetProjectRequest;
import com.nashss.se.creativecompanion.activity.result.GetProjectResult;
import com.nashss.se.creativecompanion.converters.ModelConverter;
import com.nashss.se.creativecompanion.dynamodb.ProjectDao;
import com.nashss.se.creativecompanion.dynamodb.models.Project;
import com.nashss.se.creativecompanion.models.ProjectModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the GetProjectActivity for the CreativeCompanionService's GetProject API.
 * <p>
 * This API allows the customer to see the details of a specific project.
 */
public class GetProjectActivity {

    private final Logger log = LogManager.getLogger();
    private final ProjectDao projectDao;

    /**
     * Instantiates a new GetProjectActivity object.
     *
     * @param projectDao ProjectDao to access the project table.
     */
    @Inject
    public GetProjectActivity(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    /**
     * This method handles the incoming request by retrieving a specified user's Project from the database.
     * <p>
     * It then returns the matching project.
     *
     * @param getProjectRequest request object containing the User ID & the Project Id
     * @return getProjectResult result object containing the project requested that were created by that User ID
     */
    public GetProjectResult handleRequest(final GetProjectRequest getProjectRequest) {
        log.info("Received GetProjectRequest {}", getProjectRequest);

        Project result = projectDao.getProject(getProjectRequest.getUserId(), getProjectRequest.getProjectId());
        ProjectModel projectModel = new ModelConverter().toProjectModel(result);
        System.out.println("***** inside GetProjectActivity *****: " + result);
        return GetProjectResult.builder()
                .withProject(projectModel)
                .build();
    }
}
