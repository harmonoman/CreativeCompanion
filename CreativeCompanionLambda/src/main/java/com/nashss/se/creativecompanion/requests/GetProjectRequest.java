package com.nashss.se.creativecompanion.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetProjectRequest.Builder.class)
public class GetProjectRequest {

    private final String userId;
    private final String projectId;

    private GetProjectRequest(String userId, String projectId) {
        this.userId = userId;
        this.projectId = projectId;
    }

    public String getUserId() {
        return userId;
    }

    public String getProjectId() {
        return projectId;
    }

    @Override
    public String toString() {
        return "GetProjectRequest{" +
                "userId='" + userId + '\'' +
                ", projectId='" + projectId + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private String projectId;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withProjectId(String projectId) {
            this.projectId = projectId;
            return this;
        }

        public GetProjectRequest build() {
            return new GetProjectRequest(userId, projectId);
        }
    }
}
