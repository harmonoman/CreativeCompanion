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

    /**
     * builder method for the GetProjectByNameRequest class.
     * @return Builder object
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for the GetProjectByNameRequest class.
     */
    public static class Builder {
        private String userId;
        private String projectName;

        /**
         * withUserId method for the GetProjectByNameRequest builder class.
         * @param newUserId String representing the new userId.
         * @return Builder object
         */
        public Builder withUserId(String newUserId) {
            this.userId = newUserId;
            return this;
        }

        /**
         * withProjectName method for the GetProjectByNameRequest builder class.
         * @param newProjectName String representing the new projectName.
         * @return Builder object
         */
        public Builder withProjectName(String newProjectName) {
            this.projectName = newProjectName;
            return this;
        }

        /**
         * build method for the GetProjectByNameRequest builder class.
         * @return GetProjectByNameRequest object
         */
        public GetProjectByNameRequest build() {
            return new GetProjectByNameRequest(userId, projectName);
        }
    }
}
