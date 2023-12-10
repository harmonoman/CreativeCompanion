package com.nashss.se.creativecompanion.results;

import com.nashss.se.creativecompanion.models.ProjectModel;

public class UpdateProjectResult {

    private final ProjectModel project;

    private UpdateProjectResult(ProjectModel project) {
        this.project = project;
    }

    public ProjectModel getProject() {
        return project;
    }

    @Override
    public String toString() {
        return "UpdateProjectResult{" +
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

        public UpdateProjectResult build() {
            return new UpdateProjectResult(project);
        }
    }
}
