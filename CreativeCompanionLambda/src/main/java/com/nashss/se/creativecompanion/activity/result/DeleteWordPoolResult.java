package com.nashss.se.creativecompanion.activity.result;

public class DeleteWordPoolResult {

    private final Boolean success;

    private DeleteWordPoolResult(Boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "DeleteWordPoolResult{" +
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

        public DeleteWordPoolResult build() {
            return new DeleteWordPoolResult(success);
        }
    }
}
