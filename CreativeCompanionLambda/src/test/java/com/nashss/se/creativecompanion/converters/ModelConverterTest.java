package com.nashss.se.creativecompanion.converters;

import com.nashss.se.creativecompanion.dynamodb.models.Project;
import com.nashss.se.creativecompanion.dynamodb.models.WordPool;
import com.nashss.se.creativecompanion.models.ProjectModel;
import com.nashss.se.creativecompanion.models.WordPoolModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelConverterTest {
    private ModelConverter modelConverter = new ModelConverter();

    @Test
    void toProjectModel_withTestStrings_convertsProject() {
        // WHEN
        Project project = new Project();
        project.setUserId("userId");
        project.setProjectId("projectId");
        project.setProjectName("projectName");
        project.setWordPool(List.of("wordPool"));
        project.setWorkspace(List.of("workspace"));

        // GIVEN
        ProjectModel projectModel = modelConverter.toProjectModel(project);

        // THEN
        assertEquals(project.getUserId(), projectModel.getUserId());
        assertEquals(project.getProjectId(), projectModel.getProjectId());
        assertEquals(project.getProjectName(), projectModel.getProjectName());
        assertEquals(project.getWordPool(), projectModel.getWordPool());
        assertEquals(project.getWorkspace(), projectModel.getWorkspace());
    }

    @Test
    void toProjectModelList_withListOfProjectModels_convertsList() {

        // GIVEN
        Project project1 = new Project();
        project1.setUserId("userId1");
        project1.setProjectId("projectId1");
        project1.setProjectName("projectName1");
        project1.setWordPool(List.of("wordPool1"));
        project1.setWorkspace(List.of("workspace1"));

        Project project2 = new Project();
        project2.setUserId("userId2");
        project2.setProjectId("projectId2");
        project2.setProjectName("projectName2");
        project2.setWordPool(List.of("wordPool2"));
        project2.setWorkspace(List.of("workspace2"));

        List<Project> projectList = new ArrayList<>();
        projectList.add(project1);
        projectList.add(project2);

        // WHEN
        List<ProjectModel> projectModelList = modelConverter.toProjectModelList(projectList);

        // THEN
        assertEquals(2, projectModelList.size());
        assertEquals(project1.getUserId(), projectModelList.get(0).getUserId());
        assertEquals(project1.getProjectId(), projectModelList.get(0).getProjectId());
        assertEquals(project1.getProjectName(), projectModelList.get(0).getProjectName());
        assertEquals(project1.getWordPool(), projectModelList.get(0).getWordPool());
        assertEquals(project1.getWorkspace(), projectModelList.get(0).getWorkspace());
        assertEquals(project2.getUserId(), projectModelList.get(1).getUserId());
        assertEquals(project2.getProjectId(), projectModelList.get(1).getProjectId());
        assertEquals(project2.getProjectName(), projectModelList.get(1).getProjectName());
        assertEquals(project2.getWordPool(), projectModelList.get(1).getWordPool());
        assertEquals(project2.getWorkspace(), projectModelList.get(1).getWorkspace());
    }

    @Test
    void toWordPoolModel_withTestStrings_convertsWordPool() {
        // WHEN
        WordPool wordPool = new WordPool();
        wordPool.setUserId("userId");
        wordPool.setWordPoolId("projectId");
        wordPool.setWordPoolName("projectName");
        wordPool.setWordPool(List.of("wordPool"));

        // GIVEN
        WordPoolModel wordPoolModel = modelConverter.toWordPoolModel(wordPool);

        // THEN
        assertEquals(wordPool.getUserId(), wordPoolModel.getUserId());
        assertEquals(wordPool.getWordPoolId(), wordPoolModel.getWordPoolId());
        assertEquals(wordPool.getWordPoolName(), wordPoolModel.getWordPoolName());
        assertEquals(wordPool.getWordPool(), wordPoolModel.getWordPool());
    }

    @Test
    void toWordPoolModelList_withListOfWordPoolModels_convertsList() {

        // GIVEN
        WordPool wordPool1 = new WordPool();
        wordPool1.setUserId("userId1");
        wordPool1.setWordPoolId("wordPoolId1");
        wordPool1.setWordPoolName("wordPoolName1");
        wordPool1.setWordPool(List.of("wordPool1"));


        WordPool wordPool2 = new WordPool();
        wordPool2.setUserId("userId2");
        wordPool2.setWordPoolId("wordPoolId2");
        wordPool2.setWordPoolName("wordPoolName2");
        wordPool2.setWordPool(List.of("wordPool2"));

        List<WordPool> wordPoolList = new ArrayList<>();
        wordPoolList.add(wordPool1);
        wordPoolList.add(wordPool2);

        // WHEN
        List<WordPoolModel> wordPoolModelList = modelConverter.toWordPoolModelList(wordPoolList);

        // THEN
        assertEquals(2, wordPoolModelList.size());
        assertEquals(wordPool1.getUserId(), wordPoolModelList.get(0).getUserId());
        assertEquals(wordPool1.getWordPoolId(), wordPoolModelList.get(0).getWordPoolId());
        assertEquals(wordPool1.getWordPoolName(), wordPoolModelList.get(0).getWordPoolName());
        assertEquals(wordPool1.getWordPool(), wordPoolModelList.get(0).getWordPool());
        assertEquals(wordPool2.getUserId(), wordPoolModelList.get(1).getUserId());
        assertEquals(wordPool2.getWordPoolId(), wordPoolModelList.get(1).getWordPoolId());
        assertEquals(wordPool2.getWordPoolName(), wordPoolModelList.get(1).getWordPoolName());
        assertEquals(wordPool2.getWordPool(), wordPoolModelList.get(1).getWordPool());
    }

}
