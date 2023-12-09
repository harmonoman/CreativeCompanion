package com.nashss.se.creativecompanion.requests;

public class GetProjectByNameRequest {

    private final String userId;
    private final String projectName;

    private GetProjectByNameRequest(String userId, String projectName) {
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
        return "GetProjectByNameRequest{" +
                "userId='" + userId + '\'' +
                ", projectName='" + projectName + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private String projectName;

        public GetProjectByNameRequest.Builder withUserId(String newUserId) {
            this.userId = newUserId;
            return this;
        }

        public GetProjectByNameRequest.Builder withProjectName(String newProjectName) {
            this.projectName = newProjectName;
            return this;
        }

        public GetProjectByNameRequest build() {
            return new GetProjectByNameRequest(userId, projectName);
        }
    }
}
