package com.nashss.se.creativecompanion.requests;

public class GetWordPoolListRequest {

    private final String userId;

    private GetWordPoolListRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GetWordPoolListRequest{" +
                "userId='" + userId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public GetWordPoolListRequest build() {
            return new GetWordPoolListRequest(userId);
        }
    }

}
