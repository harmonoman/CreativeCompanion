package com.nashss.se.creativecompanion.activity.result;

import com.nashss.se.creativecompanion.models.WordPoolModel;

public class UpdateWordPoolResult {

    private final WordPoolModel wordPool;

    private UpdateWordPoolResult(WordPoolModel wordPool) {
        this.wordPool = wordPool;
    }

    public WordPoolModel getWordPool() {
        return wordPool;
    }

    @Override
    public String toString() {
        return "UpdateWordPoolResult{" +
                "wordPool=" + wordPool +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private WordPoolModel wordPool;

        public Builder withWordPool(WordPoolModel wordPool) {
            this.wordPool = wordPool;
            return this;
        }

        public UpdateWordPoolResult build() {
            return new UpdateWordPoolResult(wordPool);
        }
    }
}
