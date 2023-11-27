package com.nashss.se.creativecompanion.requests;

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

    public static GetWordPoolRequest.Builder builder() {
        return new GetWordPoolRequest.Builder();
    }

    public static class Builder {
        private String userId;
        private String wordPoolId;

        public GetWordPoolRequest.Builder withUserId(String newUserId) {
            this.userId = newUserId;
            return this;
        }

        public GetWordPoolRequest.Builder withWordPoolId(String newWordPoolId) {
            this.wordPoolId = newWordPoolId;
            return this;
        }

        /*
        build method for GetWordPoolRequest builder
         */
        public GetWordPoolRequest build() {
            return new GetWordPoolRequest(userId, wordPoolId);
        }
    }
}
