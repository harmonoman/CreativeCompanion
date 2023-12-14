package com.nashss.se.creativecompanion.activity.request;

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

    /**
     * builder method for the DeleteProjectRequest class.
     * @return Builder object
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for the DeleteProjectRequest class.
     */
    public static class Builder {
        private String userId;
        private String projectId;

        /**
         * withUserId method for the DeleteProjectRequest builder class.
         * @param newUserId String representing the new userId.
         * @return Builder
         */
        public Builder withUserId(String newUserId) {
            this.userId = newUserId;
            return this;
        }

        /**
         * withProjectId method for the DeleteProjectRequest builder class.
         * @param newProjectId String representing the new projectId.
         * @return Builder
         */
        public Builder withProjectId(String newProjectId) {
            this.projectId = newProjectId;
            return this;
        }

        /**
         * build method for the DeleteProjectRequest builder class.
         * @return DeleteProjectRequest object
         */
        public DeleteProjectRequest build() {
            return new DeleteProjectRequest(userId, projectId);
        }
    }
}
