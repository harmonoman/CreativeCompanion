package com.nashss.se.creativecompanion.dynamodb.models;

import java.util.ArrayList;
import java.util.List;

public class ModelConverter {

    // ---------- PROJECT ----------

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

    // ---------- PROJECT LIST ----------

    /**
     * Converts a list of Projects to a list of ProjectModels.
     *
     * @param projects The Projects to convert to ProjectModels
     * @return The converted list of ProjectModels
     */
    public List<ProjectModel> toProjectModelList(List<Project> projects) {
        List<ProjectModel> projectModels = new ArrayList<>();

        for (Project project : projects) {
            projectModels.add(toProjectModel(project));
        }

        return projectModels;
    }

    // ---------- WORDPOOL ----------

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

    // ---------- WORDPOOL LIST ----------

    /**
     * Converts a list of WordPools to a list of WordPoolModels.
     *
     * @param wordPools The WordPools to convert to WordPoolModels
     * @return The converted list of WordPoolModels
     */
    public List<WordPoolModel> toWordPoolModelList(List<WordPool> wordPools) {
        List<WordPoolModel> wordPoolModels = new ArrayList<>();

        for (WordPool wordPool : wordPools) {
            wordPoolModels.add(toWordPoolModel(wordPool));
        }

        return wordPoolModels;
    }
}

