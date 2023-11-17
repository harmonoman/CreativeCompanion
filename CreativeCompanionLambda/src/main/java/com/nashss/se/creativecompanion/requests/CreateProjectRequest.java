package com.nashss.se.creativecompanion.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateProjectRequest.Builder.class)
public class CreateProjectRequest {
    private final String userId;
    private final String projectName;

    private CreateProjectRequest(String userId, String projectName) {
        this.userId = userId;
        this.projectName = projectName;

    }
    public String getUserId() {
        return userId;
    }

    public String getProjectName() {
        return projectName;
    }

    @Override
    public String toString() {
        return "CreateProjectRequest{" +
                "userId='" + userId + '\'' +
                ", projectName='" + projectName + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private String projectName;


        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withProjectName(String projectName) {
            this.projectName = projectName;
            return this;
        }

        public CreateProjectRequest build() {
            return new CreateProjectRequest(userId, projectName);
        }
    }
}
