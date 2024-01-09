package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.activity.request.DeleteProjectRequest;
import com.nashss.se.creativecompanion.activity.result.DeleteProjectResult;
import com.nashss.se.creativecompanion.dynamodb.ProjectDao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
public class DeleteProjectActivity {

    private final Logger log = LogManager.getLogger();
    private final ProjectDao projectDao;

    /**
     * Instantiates a new DeleteProjectActivity object.
     *
     * @param projectDao ProjectDao to access the project table.
     */
    @Inject
    public DeleteProjectActivity(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    /**
     * This method handles the incoming request by retrieving a specified user's Project from the database.
     * <p>
     * It then returns the matching project.
     *
     * @param deleteProjectRequest request object containing the User ID & the Project Id
     * @return deleteProjectResult result object containing the project requested that were created by that User ID
     */
    public DeleteProjectResult handleRequest(final DeleteProjectRequest deleteProjectRequest) {
        log.info("Received DeleteProjectRequest {}", deleteProjectRequest);

        Boolean result = projectDao.deleteProject(deleteProjectRequest.getUserId(),
                deleteProjectRequest.getProjectId());

        return DeleteProjectResult.builder()
                .withSuccess(result)
                .build();
    }
}
