package creativecompanion.dynamodb.models;

import java.util.*;

public class ProjectModel {
    private final String userId;
    private final String projectId;
    private final String projectName;
    private final List<String> wordPool;
    private final List<String> workspace;


    private ProjectModel(String userId, String projectId, String projectName,
                        List<String> wordPool, List<String> workspace) {
        this.userId = userId;
        this.projectId = projectId;
        this.projectName = projectName;
        this.wordPool = wordPool;
        this.workspace = workspace;
    }

    public String getProjectId() {
        return this.projectId;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public String getUserId() {
        return this.userId;
    }

    public List<String> getWordPool() {
        return new ArrayList<>(this.wordPool);
    }

    public List<String> getWorkspace() {
        return new ArrayList<>(this.workspace);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectModel that = (ProjectModel) o;
        return Objects.equals(userId, that.userId) && Objects.equals(projectId, that.projectId) &&
                Objects.equals(projectName, that.projectName) && Objects.equals(wordPool, that.wordPool) &&
                Objects.equals(workspace, that.workspace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, projectId, projectName, wordPool, workspace);
    }


    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private String projectId;
        private String projectName;
        private List<String> wordPool;
        private List<String> workspace;



        public Builder withProjectId(String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder withProjectName(String projectName) {
            this.projectName = projectName;
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withWordPool(List<String> wordPool) {
            this.wordPool = new ArrayList<>(wordPool);
            return this;
        }

        public Builder withWorkspace(List<String> workspace) {
            this.workspace = new ArrayList<>(workspace);
            return this;
        }


        public ProjectModel build() {
            return new ProjectModel(projectId, projectName, userId, wordPool, workspace);
        }
    }
}
