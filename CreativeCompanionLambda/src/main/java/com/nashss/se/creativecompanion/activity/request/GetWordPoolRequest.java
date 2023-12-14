package com.nashss.se.creativecompanion.activity.request;

public class GetWordPoolRequest {

    private final String userId;
    private final String wordPoolId;

    private GetWordPoolRequest(String userId, String wordPoolId) {
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
        return "GetWordPoolRequest{" +
                "userId='" + userId + '\'' +
                ", wordPoolId='" + wordPoolId + '\'' +
                '}';
    }

    /**
     * builder method for the GetWordPoolRequest class.
     * @return Builder object
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for the GetWordPoolRequest class.
     */
    public static class Builder {
        private String userId;
        private String wordPoolId;

        /**
         * withUserId method for the GetWordPoolRequest builder class.
         * @param newUserId String representing the new userId.
         * @return Builder
         */
        public Builder withUserId(String newUserId) {
            this.userId = newUserId;
            return this;
        }

        /**
         * withWordPoolId method for the GetWordPoolRequest builder class.
         * @param newWordPoolId String representing the new wordPoolId.
         * @return Builder
         */
        public Builder withWordPoolId(String newWordPoolId) {
            this.wordPoolId = newWordPoolId;
            return this;
        }

        /**
         * build method for the GetWordPoolRequest builder class.
         * @return GetWordPoolRequest object
         */
        public GetWordPoolRequest build() {
            return new GetWordPoolRequest(userId, wordPoolId);
        }
    }
}
