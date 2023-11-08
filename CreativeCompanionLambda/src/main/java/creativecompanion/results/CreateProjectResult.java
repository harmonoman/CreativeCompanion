package creativecompanion.results;

import creativecompanion.dynamodb.models.ProjectModel;

public class CreateProjectResult {
    private final ProjectModel project;

    private CreateProjectResult(ProjectModel project) {
        this.project = project;
    }

    public ProjectModel getProject() {
        return project;
    }

    @Override
    public String toString() {
        return "CreateProjectResult{" +
                "project=" + project +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ProjectModel project;

        public Builder withProject(ProjectModel project) {
            this.project = project;
            return this;
        }

        public CreateProjectResult build() {
            return new CreateProjectResult(project);
        }
    }
}
