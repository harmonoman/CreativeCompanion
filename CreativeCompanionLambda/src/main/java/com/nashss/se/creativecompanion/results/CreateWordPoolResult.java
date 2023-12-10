package com.nashss.se.creativecompanion.results;

import com.nashss.se.creativecompanion.models.WordPoolModel;

public class CreateWordPoolResult {

    private final WordPoolModel wordPool;

    private CreateWordPoolResult(WordPoolModel wordPool) {
        this.wordPool = wordPool;
    }

    public WordPoolModel getWordPool() {
        return wordPool;
    }

    @Override
    public String toString() {
        return "CreateWordPoolResult{" +
                "wordPool=" + wordPool +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static CreateWordPoolResult.Builder builder() {
        return new CreateWordPoolResult.Builder();
    }

    public static class Builder {
        private WordPoolModel wordPool;

        public CreateWordPoolResult.Builder withWordPool(WordPoolModel wordPool) {
            this.wordPool = wordPool;
            return this;
        }

        public CreateWordPoolResult build() {
            return new CreateWordPoolResult(wordPool);
        }
    }
}
