package com.nashss.se.creativecompanion.results;

import com.nashss.se.creativecompanion.models.ProjectModel;

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

    /**
     * builder method for the GetProjectByNameResult class.
     * @return Builder object
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ProjectModel builderProject ;

        /**
         * withProjectName method for the GetProjectByNameResult builder class.
         * @param project String representing the ProjectModel.
         * @return Builder
         */
        public Builder withProjectName(ProjectModel project) {
            this.builderProject = project;
            return this;
        }

        /**
         * Builds a result for the project.
         * @return GetProjectByNameResult
         */
        public GetProjectByNameResult build() {
            return new GetProjectByNameResult(builderProject);
        }
    }


}
