package com.nashss.se.creativecompanion.results;

import com.nashss.se.creativecompanion.dynamodb.models.ProjectModel;

public class GetProjectResult {

    private final ProjectModel project;

    private GetProjectResult(ProjectModel project) {
        this.project = project;
    }

    public ProjectModel getProject() {
        return project;
    }

    @Override
    public String toString() {
        return "GetProjectResult{" +
                "project=" + project +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ProjectModel project ;

        public Builder withProject(ProjectModel project) {
            this.project = project;
            return this;
        }

        public GetProjectResult build() {
            return new GetProjectResult(project);
        }
    }
}
