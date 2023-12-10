package com.nashss.se.creativecompanion.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

@JsonDeserialize(builder = UpdateProjectRequest.Builder.class)
public class UpdateProjectRequest {

    private final String userId;
    private final String projectId;
    private final String projectName;
    private final List<String> wordPool;
    private final List<String> workspace;

    private UpdateProjectRequest(String userId, String projectId, String projectName, List<String> wordPool,
                                 List<String> workspace) {
        this.userId = userId;
        this.projectId = projectId;
        this.projectName = projectName;
        this.wordPool = wordPool;
        this.workspace = workspace;
    }

    public String getUserId() {
        return userId;
    }

    /**
     * Getter method for the UpdateProjectRequest class.
     * @return projectId
     */
    public String getProjectId() {
        System.out.println("***** inside UpdateProjectRequest *****: " + projectId);
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public List<String>  getWordPool() {
        return wordPool;
    }

    public List<String>  getWorkspace() {
        return workspace;
    }

    @Override
    public String toString() {
        return "UpdateProjectRequest{" +
                "userId='" + userId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", wordPool=" + wordPool +
                ", workspace=" + workspace +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {

        private String userId;
        private String projectId;
        private String projectName;
        private List<String> wordPool;
        private List<String> workspace;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withProjectId(String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder withProjectName(String projectName) {
            this.projectName = projectName;
            return this;
        }

        public Builder withWordPool(List<String> wordPool) {
            this.wordPool = wordPool;
            return this;
        }

        public Builder withWorkspace(List<String> workspace) {
            this.workspace = workspace;
            return this;
        }

        public UpdateProjectRequest build() {
            return new UpdateProjectRequest(userId, projectId, projectName, wordPool, workspace);
        }
    }
}


