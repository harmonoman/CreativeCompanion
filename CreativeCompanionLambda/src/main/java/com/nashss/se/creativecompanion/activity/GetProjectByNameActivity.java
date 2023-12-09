package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.dynamodb.ProjectDao;
import com.nashss.se.creativecompanion.dynamodb.models.ModelConverter;
import com.nashss.se.creativecompanion.dynamodb.models.Project;
import com.nashss.se.creativecompanion.dynamodb.models.ProjectModel;
import com.nashss.se.creativecompanion.requests.GetProjectByNameRequest;
import com.nashss.se.creativecompanion.requests.GetProjectRequest;
import com.nashss.se.creativecompanion.results.GetProjectByNameResult;
import com.nashss.se.creativecompanion.results.GetProjectResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the GetProjectByNameActivity for the CreativeCompanionService's GetProjectByName API.
 * <p>
 * This API allows the customer to get a specific project by name and see it's details.
 */
public class GetProjectByNameActivity {

    private final Logger log = LogManager.getLogger();
    private final ProjectDao projectDao;

    /**
     * Instantiates a new GetProjectByNameActivity object.
     *
     * @param projectDao ProjectDao to access the project table.
     */
    @Inject
    public GetProjectByNameActivity(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    /**
     * This method handles the incoming request by retrieving a specified user's Project from the database by Name.
     * <p>
     * It then returns the matching project.
     *
     * @param getProjectByNameRequest request object containing the User ID & the Project Id
     * @return getProjectResult result object containing the project requested that were created by that User ID
     */
    public GetProjectByNameResult handleRequest(final GetProjectByNameRequest getProjectByNameRequest) {
        log.info("Received GetProjectByNameRequest {}", getProjectByNameRequest);

        Project result = projectDao.getProjectByName(getProjectByNameRequest.getUserId(), getProjectByNameRequest.getProjectName());
        ProjectModel projectModel = new ModelConverter().toProjectModel(result);
        System.out.println("***** inside GetProjectByNameActivity *****: " + result);
        return GetProjectByNameResult.builder()
                .withProjectName(projectModel)
                .build();
    }

}
