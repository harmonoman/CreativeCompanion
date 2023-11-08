package creativecompanion.requests;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

public class CreateProjectRequest {
    private final String projectName;
    private final String userId;

    private CreateProjectRequest(String projectName, String userId) {
        this.projectName = projectName;
        this.userId = userId;
    }

    public String getProjectName() {
        return projectName;
    }


    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "CreateProjectRequest{" +
                "projectName='" + projectName + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String projectName;
        private String userId;

        public Builder withProjectName(String projectName) {
            this.projectName = projectName;
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public CreateProjectRequest build() {
            return new CreateProjectRequest(projectName, userId);
        }
    }
}
