package com.nashss.se.creativecompanion.activity.result;

import com.nashss.se.creativecompanion.models.WordPoolModel;

public class GetWordPoolResult {

    private final WordPoolModel wordPool;

    private GetWordPoolResult(WordPoolModel wordPool) {
        this.wordPool = wordPool;
    }

    public WordPoolModel getWordPool() {
        return wordPool;
    }

    @Override
    public String toString() {
        return "GetWordPoolResult{" +
                "wordPool=" + wordPool +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static GetWordPoolResult.Builder builder() {
        return new GetWordPoolResult.Builder();
    }

    public static class Builder {
        private WordPoolModel wordPool;

        public GetWordPoolResult.Builder withWordPool(WordPoolModel wordPool) {
            this.wordPool = wordPool;
            return this;
        }

        public GetWordPoolResult build() {
            return new GetWordPoolResult(wordPool);
        }
    }
}
