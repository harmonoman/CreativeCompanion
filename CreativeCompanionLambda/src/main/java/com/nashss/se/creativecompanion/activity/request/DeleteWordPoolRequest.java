package com.nashss.se.creativecompanion.activity.request;

public class DeleteWordPoolRequest {

    private final String userId;
    private final String wordPoolId;

    private DeleteWordPoolRequest(String userId, String wordPoolId) {
        this.userId = userId;
        this.wordPoolId = wordPoolId;
    }

    public String getUserId() {
        return userId;
    }

    public String getWordPoolId() {
        return wordPoolId;
    }

    @Override
    public String toString() {
        return "DeleteWordPoolRequest{" +
                "userId='" + userId + '\'' +
                ", wordPoolId='" + wordPoolId + '\'' +
                '}';
    }

    /**
     * builder method for the DeleteWordPoolRequest class.
     * @return Builder object
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for the DeleteWordPoolRequest class.
     */
    public static class Builder {
        private String userId;
        private String wordPoolId;

        /**
         * withUserId method for the DeleteWordPoolRequest builder class.
         * @param newUserId String representing the new userId.
         * @return Builder
         */
        public Builder withUserId(String newUserId) {
            this.userId = newUserId;
            return this;
        }

        /**
         * withWordPoolId method for the DeleteWordPoolRequest builder class.
         * @param newWordPoolId String representing the new wordPoolId.
         * @return Builder
         */
        public Builder withWordPoolId(String newWordPoolId) {
            this.wordPoolId = newWordPoolId;
            return this;
        }

        /**
         * build method for the DeleteWordPoolRequest builder class.
         * @return DeleteWordPoolRequest object
         */
        public DeleteWordPoolRequest build() {
            return new DeleteWordPoolRequest(userId, wordPoolId);
        }
    }
}
