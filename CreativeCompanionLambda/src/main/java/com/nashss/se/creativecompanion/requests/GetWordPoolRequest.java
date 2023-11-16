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

        public GetWordPoolRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public GetWordPoolRequest.Builder withWordPoolId(String wordPoolId) {
            this.wordPoolId = wordPoolId;
            return this;
        }

        public GetWordPoolRequest build() {
            return new GetWordPoolRequest(userId, wordPoolId);
        }
    }
}
