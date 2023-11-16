package com.nashss.se.creativecompanion.results;

import com.nashss.se.creativecompanion.dynamodb.models.WordPoolModel;

import java.util.ArrayList;
import java.util.List;

public class GetWordPoolListResult {

    private final List<WordPoolModel> wordPools;

    private GetWordPoolListResult(List<WordPoolModel> wordPools) {
        this.wordPools = wordPools;
    }

    public List<WordPoolModel> getWordPools() {
        return wordPools;
    }

    @Override
    public String toString() {
        return "GetWordPoolListResult{" +
                "wordPools=" + wordPools +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<WordPoolModel> wordPools ;

        public Builder withWordPools(List<WordPoolModel> wordPools) {
            this.wordPools = new ArrayList<>(wordPools);
            return this;
        }

        public GetWordPoolListResult build() {
            return new GetWordPoolListResult(wordPools);
        }
    }

}
