package com.nashss.se.creativecompanion.requests;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

public class CreateWordPoolRequest {

    private final String userId;
    private final String wordPoolName;

    private CreateWordPoolRequest(String userId, String wordPoolName) {
        this.userId = userId;
        this.wordPoolName = wordPoolName;
    }

    public String getUserId() {
        return userId;
    }

    public String getWordPoolName() {
        return wordPoolName;
    }

    @Override
    public String toString() {
        return "CreateWordPoolRequest{" +
                "userId='" + userId + '\'' +
                ", wordPoolName='" + wordPoolName + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static CreateWordPoolRequest.Builder builder() {
        return new CreateWordPoolRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private String wordPoolName;


        public CreateWordPoolRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public CreateWordPoolRequest.Builder withWordPoolName(String wordPoolName) {
            this.wordPoolName = wordPoolName;
            return this;
        }

        public CreateWordPoolRequest build() {
            return new CreateWordPoolRequest(userId, wordPoolName);
        }
    }
}