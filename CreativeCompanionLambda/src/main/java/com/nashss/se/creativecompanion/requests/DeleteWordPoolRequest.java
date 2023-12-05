package com.nashss.se.creativecompanion.requests;

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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private String wordPoolId;

        public Builder withUserId(String newUserId) {
            this.userId = newUserId;
            return this;
        }

        public Builder withWordPoolId(String newWordPoolId) {
            this.wordPoolId = newWordPoolId;
            return this;
        }

        public DeleteWordPoolRequest build() {
            return new DeleteWordPoolRequest(userId, wordPoolId);
        }
    }
}
