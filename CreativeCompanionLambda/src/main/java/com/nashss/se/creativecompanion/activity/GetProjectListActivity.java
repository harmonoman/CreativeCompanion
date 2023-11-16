package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.dynamodb.ProjectDao;
import com.nashss.se.creativecompanion.dynamodb.models.ModelConverter;
import com.nashss.se.creativecompanion.dynamodb.models.Project;
import com.nashss.se.creativecompanion.dynamodb.models.ProjectModel;
import com.nashss.se.creativecompanion.requests.GetProjectListRequest;
import com.nashss.se.creativecompanion.results.GetProjectListResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.inject.Inject;

/**
 * Implementation of the GetProjectListActivity for the CreativeCompanionService's GetProject API.
 * <p>
 * This API allows the customer to see all of their projects.
 */
public class GetProjectListActivity {

    private final Logger log = LogManager.getLogger();
    private final ProjectDao projectDao;

    /**
     * Instantiates a new SearchProjectsActivity object.
     *
     * @param projectDao ProjectDao to access the projects table.
     */
    @Inject
    public GetProjectListActivity(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    /**
     * This method handles the incoming request by retrieving List of a specified user's Projects from the database.
     * <p>
     * It then returns the matching projects, or an empty result list if none are found.
     *
     * @param getProjectListRequest request object containing the User ID
     * @return getProjectListResult result object containing the pantries that were created by that User ID
     */
    public GetProjectListResult handleRequest(final GetProjectListRequest getProjectListRequest) {
        log.info("Received GetProjectListRequest {}", getProjectListRequest);

        List<Project> results = projectDao.getUserProjects(getProjectListRequest.getUserId());
        List<ProjectModel> projectModels = new ModelConverter().toProjectModelList(results);

        return GetProjectListResult.builder()
                .withProjects(projectModels)
                .build();
    }
}
