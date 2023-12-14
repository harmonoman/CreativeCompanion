package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.converters.ModelConverter;
import com.nashss.se.creativecompanion.dynamodb.ProjectDao;
import com.nashss.se.creativecompanion.dynamodb.models.Project;
import com.nashss.se.creativecompanion.models.ProjectModel;
import com.nashss.se.creativecompanion.activity.request.CreateProjectRequest;
import com.nashss.se.creativecompanion.activity.result.CreateProjectResult;
import com.nashss.se.creativecompanion.utils.ServiceUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import javax.inject.Inject;

/**
 * Implementation of the CreateProjectActivity for the CreativeCompanionService's CreateProject API.
 * <p>
 * This API allows the customer to create a new project with no data.
 */
public class CreateProjectActivity {
    private final Logger log = LogManager.getLogger();
    private final ProjectDao projectDao;

    /**
     * Instantiates a new CreateProjectActivity object.
     *
     * @param projectDao ProjectDao to access the projects table.
     */
    @Inject
    public CreateProjectActivity(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    /**
     * This method handles the incoming request by persisting a new project
     * with the provided project name and customer ID from the request.
     * <p>
     * It then returns the newly created project.
     * <p>
     * If the provided project name or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param createProjectRequest request object containing the project name and customer ID
     *                              associated with it
     * @return createProjectResult result object containing the API defined {@link ProjectModel}
     */
    public CreateProjectResult handleRequest(final CreateProjectRequest createProjectRequest) {
        log.info("Received CreateProjectRequest {}", createProjectRequest);

        Project newProject = new Project();
        newProject.setUserId(createProjectRequest.getUserId());
        newProject.setProjectId(ServiceUtils.generateProjectId());
        newProject.setProjectName(createProjectRequest.getProjectName());
        newProject.setWordPool(new ArrayList<>());
        newProject.setWorkspace(new ArrayList<>());

        projectDao.saveProject(newProject);

        ProjectModel projectModel = new ModelConverter().toProjectModel(newProject);
        System.out.println(projectModel);
        return CreateProjectResult.builder()
                .withProject(projectModel)
                .build();
    }
}
