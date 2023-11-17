package com.nashss.se.creativecompanion.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
// import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

import java.util.List;
import java.util.Objects;

/**
 * Represents a record in the projects table.
 */
@DynamoDBTable(tableName = "projects")
public class Project {
    private String userId;
    private String projectId;
    private String projectName;

    private List<String> wordPool;
    private List<String> workspace;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return this.userId;
    }
    @DynamoDBRangeKey(attributeName = "projectId")
    public String getProjectId() {
        return this.projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @DynamoDBAttribute(attributeName = "projectName")
    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setWordPool(List<String> wordPool) {
        this.wordPool = wordPool;
    }

    @DynamoDBAttribute(attributeName = "wordPool")
    public List<String> getWordPool() {
        return this.wordPool;
    }

    public void setWorkspace(List<String> workspace) {
        this.workspace = workspace;
    }

    @DynamoDBAttribute(attributeName = "workspace")
    public List<String> getWorkspace() {
        return this.workspace;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(userId, project.userId) && Objects.equals(projectId, project.projectId) &&
                Objects.equals(projectName, project.projectName) && Objects.equals(wordPool,
                project.wordPool) && Objects.equals(workspace, project.workspace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, projectId, projectName, wordPool, workspace);
    }

    @Override
    public String toString() {
        return "Project{" +
                "userId='" + userId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", wordPool=" + wordPool +
                ", workspace=" + workspace +
                '}';
    }


}

