package com.nashss.se.creativecompanion.results;

public class DeleteProjectResult {

    private final Boolean success;

    private DeleteProjectResult(Boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "DeleteProjectResult{" +
                "success=" + success +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private boolean success;


        public Builder withSuccess(Boolean success) {
            this.success = success;
            return this;
        }

        public DeleteProjectResult build() {
            return new DeleteProjectResult(success);
        }
    }
}
