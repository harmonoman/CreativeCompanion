package com.nashss.se.creativecompanion.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateWordPoolRequest.Builder.class)
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
    public static Builder builder() {
        return new CreateWordPoolRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private String wordPoolName;


        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withWordPoolName(String wordPoolName) {
            this.wordPoolName = wordPoolName;
            return this;
        }

        // Method to replace spaces
        public CreateWordPoolRequest.Builder replaceSpaces() {
            if (this.wordPoolName != null) {
                this.wordPoolName = this.wordPoolName.replace(' ', '_');
            }
            return this;
        }

        public CreateWordPoolRequest build() {
            return new CreateWordPoolRequest(userId, wordPoolName);
        }
    }
}
