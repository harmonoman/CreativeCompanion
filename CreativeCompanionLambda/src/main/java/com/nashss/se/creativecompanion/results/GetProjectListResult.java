package com.nashss.se.creativecompanion.results;

import com.nashss.se.creativecompanion.dynamodb.models.ProjectModel;

import java.util.ArrayList;
import java.util.List;

public class GetProjectListResult {

    private final List<ProjectModel> projects;

    private GetProjectListResult(List<ProjectModel> projects) {
        this.projects = projects;
    }

    public List<ProjectModel> getProjects() {
        return projects;
    }

    @Override
    public String toString() {
        return "GetProjectListResult{" +
                "projects=" + projects +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<ProjectModel> projects ;

        public Builder withProjects(List<ProjectModel> projects) {
            this.projects = new ArrayList<>(projects);
            return this;
        }

        public GetProjectListResult build() {
            return new GetProjectListResult(projects);
        }
    }
}
