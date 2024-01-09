package com.nashss.se.creativecompanion.activity.request;

public class GetWordPoolByNameRequest {

    private final String userId;
    private final String wordPoolName;

    private GetWordPoolByNameRequest(String userId, String wordPoolName) {
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
        return "GetWordPoolByNameRequest{" +
                "userId='" + userId + '\'' +
                ", wordPoolName='" + wordPoolName + '\'' +
                '}';
    }

    /**
     * builder method for the GetWordPoolByNameRequest class.
     * @return Builder object
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for the GetWordPoolByNameRequest class.
     */
    public static class Builder {
        private String userId;
        private String wordPoolName;

        /**
         * withUserId method for the GetWordPoolByNameRequest builder class.
         * @param newUserId String representing the new userId.
         * @return Builder object
         */
        public Builder withUserId(String newUserId) {
            this.userId = newUserId;
            return this;
        }

        /**
         * withWordPoolName method for the GetWordPoolByNameRequest builder class.
         * @param newWordPoolName String representing the new wordPoolName.
         * @return Builder object
         */
        public Builder withWordPoolName(String newWordPoolName) {
            this.wordPoolName = newWordPoolName;
            return this;
        }

        /**
         * Replace spaces in the project name with a specified character.
         * @param replacementChar The character to replace spaces with.
         * @return Builder object
         */
        public Builder replaceSpaces(char replacementChar) {
            if (this.wordPoolName != null) {
                this.wordPoolName = this.wordPoolName.replace(' ', replacementChar);
            }
            return this;
        }

        /**
         * build method for the GetWordPoolByNameRequest builder class.
         * @return GetWordPoolByNameRequest object
         */
        public GetWordPoolByNameRequest build() {
            return new GetWordPoolByNameRequest(userId, wordPoolName);
        }
    }
}
