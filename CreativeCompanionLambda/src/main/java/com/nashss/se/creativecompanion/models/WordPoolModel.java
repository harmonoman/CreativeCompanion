package com.nashss.se.creativecompanion.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WordPoolModel {

    private final String userId;
    private final String wordPoolId;
    private final String wordPoolName;
    private final List<String> wordPool;

    private WordPoolModel(String userId, String wordPoolId, String wordPoolName, List<String> wordPool) {
        this.userId = userId;
        this.wordPoolId = wordPoolId;
        this.wordPoolName = wordPoolName;
        this.wordPool = wordPool;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getWordPoolId() {
        return this.wordPoolId;
    }

    public String getWordPoolName() {
        return this.wordPoolName;
    }

    public List<String> getWordPool() {
        return new ArrayList<>(this.wordPool);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WordPoolModel that = (WordPoolModel) o;
        return Objects.equals(userId, that.userId) && Objects.equals(wordPoolId, that.wordPoolId) &&
                Objects.equals(wordPoolName, that.wordPoolName) && Objects.equals(wordPool,
                that.wordPool);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, wordPoolId, wordPoolName, wordPool);
    }

    /**
     * builder method for the WordPoolModel.
     * @return Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;
        private String wordPoolId;
        private String wordPoolName;
        private List<String> wordPool;

        /**
         * withUserId method for the WordPoolModel builder.
         * @param newUserId String with new UserId
         * @return Builder
         */
        public Builder withUserId(String newUserId) {
            this.userId = newUserId;
            return this;
        }

        /**
         * withWordPoolId method for the WordPoolModel builder.
         * @param newWordPoolId String with new WordPoolId
         * @return Builder
         */
        public Builder withWordPoolId(String newWordPoolId) {
            this.wordPoolId = newWordPoolId;
            return this;
        }

        /**
         * withWordPoolName method for the WordPoolModel builder.
         * @param newWordPoolName String with new WordPoolName
         * @return Builder
         */
        public Builder withWordPoolName(String newWordPoolName) {
            this.wordPoolName = newWordPoolName;
            return this;
        }

        /**
         * withWordPool method for the WordPoolModel builder.
         * @param newWordPool String with new WordPool
         * @return Builder
         */
        public Builder withWordPool(List<String> newWordPool) {
            this.wordPool = new ArrayList<>(newWordPool);
            return this;
        }

        /**
         * build method for the WordPoolModel builder.
         * @return WordPoolModel
         */
        public WordPoolModel build() {
            return new WordPoolModel(userId, wordPoolId, wordPoolName , wordPool);
        }
    }
}
