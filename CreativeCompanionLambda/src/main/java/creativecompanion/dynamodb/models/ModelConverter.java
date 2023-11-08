package creativecompanion.dynamodb.models;

public class ModelConverter {

    /**
     * Converts a provided {@link Project} into a {@link ProjectModel} representation.
     *
     * @param project the project to convert
     * @return the converted project
     */
    public ProjectModel toProjectModel(Project project) {

        return ProjectModel.builder()
                .withProjectId(project.getProjectId())
                .withProjectName(project.getProjectName())
                .withUserId(project.getUserId())
                .withWordPool(project.getWordPool())
                .withWorkspace(project.getWorkspace())
                .build();

    }
}
