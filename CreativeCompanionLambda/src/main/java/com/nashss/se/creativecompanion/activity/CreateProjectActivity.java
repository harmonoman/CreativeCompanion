package com.nashss.se.creativecompanion.activity;

//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.nashss.se.creativecompanion.dynamodb.ProjectDao;
import com.nashss.se.creativecompanion.dynamodb.models.ModelConverter;
import com.nashss.se.creativecompanion.dynamodb.models.Project;
import com.nashss.se.creativecompanion.dynamodb.models.ProjectModel;
import com.nashss.se.creativecompanion.requests.CreateProjectRequest;
import com.nashss.se.creativecompanion.results.CreateProjectResult;
import com.nashss.se.creativecompanion.utils.DigitalProjectServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;

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
        newProject.setProjectId(DigitalProjectServiceUtils.generateProjectId());
        newProject.setProjectName(createProjectRequest.getProjectName());
        newProject.setWordPool(new ArrayList<>());
        newProject.setWorkspace(new ArrayList<>());


        //newProject.setInventory(new HashSet<>()); // what should this be? what does a Project hold?

        projectDao.saveProject(newProject);

        ProjectModel projectModel = new ModelConverter().toProjectModel(newProject);
        return CreateProjectResult.builder()
                .withProject(projectModel)
                .build();
    }
}
