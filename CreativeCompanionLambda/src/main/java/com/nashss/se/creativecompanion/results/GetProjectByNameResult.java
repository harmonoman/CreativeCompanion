package com.nashss.se.creativecompanion.results;

import com.nashss.se.creativecompanion.dynamodb.models.ProjectModel;

public class GetProjectByNameResult {

    private final ProjectModel project;

    private GetProjectByNameResult(ProjectModel project) {
        this.project = project;
    }

    public ProjectModel getProjectByName() {
        return project;
    }

    @Override
    public String toString() {
        return "GetProjectByNameResult{" +
                "project=" + project +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ProjectModel project ;

        public GetProjectByNameResult.Builder withProjectName(ProjectModel project) {
            this.project = project;
            return this;
        }

        public GetProjectByNameResult build() {
            return new GetProjectByNameResult(project);
        }
    }


}
