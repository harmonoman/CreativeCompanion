package com.nashss.se.creativecompanion.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

@JsonDeserialize(builder = UpdateWordPoolRequest.Builder.class)
public class UpdateWordPoolRequest {

    private final String userId;
    private final String wordPoolId;
    private final String wordPoolName;
    private final List<String> wordPool;

    private UpdateWordPoolRequest(String userId, String wordPoolId, String wordPoolName, List<String> wordPool) {
        this.userId = userId;
        this.wordPoolId = wordPoolId;
        this.wordPoolName = wordPoolName;
        this.wordPool = wordPool;
    }

    public String getUserId() {
        return userId;
    }

    public String getWordPoolId() {
        return wordPoolId;
    }

    public String getWordPoolName() {
        return wordPoolName;
    }

    public List<String>  getWordPool() {
        return wordPool;
    }

    @Override
    public String toString() {
        return "UpdateWordPoolRequest{" +
                "userId='" + userId + '\'' +
                ", wordPoolId='" + wordPoolId + '\'' +
                ", wordPoolName='" + wordPoolName + '\'' +
                ", wordPool=" + wordPool +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {

        private String userId;
        private String wordPoolId;
        private String wordPoolName;
        private List<String> wordPool;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withWordPoolId(String wordPoolId) {
            this.wordPoolId = wordPoolId;
            return this;
        }

        public Builder withWordPoolName(String wordPoolName) {
            this.wordPoolName = wordPoolName;
            return this;
        }

        public Builder withWordPool(List<String> wordPool) {
            this.wordPool = wordPool;
            return this;
        }

        public UpdateWordPoolRequest build() {
            return new UpdateWordPoolRequest(userId, wordPoolId, wordPoolName, wordPool);
        }
    }
}
