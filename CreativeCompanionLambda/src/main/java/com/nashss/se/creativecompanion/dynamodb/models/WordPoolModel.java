package com.nashss.se.creativecompanion.dynamodb.models;

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordPoolModel that = (WordPoolModel) o;
        return Objects.equals(userId, that.userId) && Objects.equals(wordPoolId, that.wordPoolId)
                && Objects.equals(wordPoolName, that.wordPoolName) && Objects.equals(wordPool,
                that.wordPool);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, wordPoolId, wordPoolName, wordPool);
    }

    public static WordPoolModel.Builder builder() {
        return new WordPoolModel.Builder();
    }

    public static class Builder {
        private String userId;
        private String wordPoolId;
        private String wordPoolName;
        private List<String> wordPool;

        public WordPoolModel.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public WordPoolModel.Builder withWordPoolId(String wordPoolId) {
            this.wordPoolId = wordPoolId;
            return this;
        }

        public WordPoolModel.Builder withWordPoolName(String wordPoolName) {
            this.wordPoolName = wordPoolName;
            return this;
        }

        public WordPoolModel.Builder withWordPool(List<String> wordPool) {
            this.wordPool = new ArrayList<>(wordPool);
            return this;
        }

        public WordPoolModel build() {
            return new WordPoolModel(userId, wordPoolId, wordPoolName , wordPool);
        }



    }
}
