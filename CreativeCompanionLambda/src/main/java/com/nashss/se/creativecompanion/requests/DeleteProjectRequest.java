package com.nashss.se.creativecompanion.requests;

public class DeleteProjectRequest {

    private final String userId;
    private final String projectId;

    private DeleteProjectRequest(String userId, String projectId) {
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
        return "DeleteProjectRequest{" +
                "userId='" + userId + '\'' +
                ", projectId='" + projectId + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private String projectId;

        public Builder withUserId(String newUserId) {
            this.userId = newUserId;
            return this;
        }

        public Builder withProjectId(String newProjectId) {
            this.projectId = newProjectId;
            return this;
        }

        public DeleteProjectRequest build() {
            return new DeleteProjectRequest(userId, projectId);
        }
    }
}
