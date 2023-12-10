package com.nashss.se.creativecompanion.requests;

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

    /**
     * builder method for the GetProjectRequest class.
     * @return Builder object
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for the GetProjectRequest class.
     */
    public static class Builder {
        private String userId;
        private String projectId;

        /**
         * withUserId method for the GetProjectRequest builder class.
         * @param newUserId String representing the new userId.
         * @return Builder
         */
        public Builder withUserId(String newUserId) {
            this.userId = newUserId;
            return this;
        }

        /**
         * withProjectId method for the GetProjectRequest builder class.
         * @param newProjectId String representing the new projectId.
         * @return Builder
         */
        public Builder withProjectId(String newProjectId) {
            this.projectId = newProjectId;
            return this;
        }

        /**
         * build method for the GetProjectRequest builder class.
         * @return GetProjectRequest object
         */
        public GetProjectRequest build() {
            return new GetProjectRequest(userId, projectId);
        }
    }
}
