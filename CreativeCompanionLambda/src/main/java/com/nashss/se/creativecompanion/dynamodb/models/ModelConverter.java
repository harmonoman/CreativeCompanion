package com.nashss.se.creativecompanion.dynamodb.models;

public class ModelConverter {

    /**
     * Converts a provided {@link Project} into a {@link ProjectModel} representation.
     *
     * @param project the project to convert
     * @return the converted project
     */
    public ProjectModel toProjectModel(Project project) {

        return ProjectModel.builder()
                .withUserId(project.getUserId())
                .withProjectId(project.getProjectId())
                .withProjectName(project.getProjectName())
                .withWordPool(project.getWordPool())
                .withWorkspace(project.getWorkspace())
                .build();

    }

    /**
     * Converts a provided {@link WordPool} into a {@link WordPoolModel} representation.
     *
     * @param wordPool the project to convert
     * @return the converted project
     */
    public WordPoolModel toWordPoolModel(WordPool wordPool) {

        return WordPoolModel.builder()
                .withUserId(wordPool.getUserId())
                .withWordPoolId(wordPool.getWordPoolId())
                .withWordPoolName(wordPool.getWordPoolName())
                .withWordPool(wordPool.getWordPool())
                .build();

    }
}
