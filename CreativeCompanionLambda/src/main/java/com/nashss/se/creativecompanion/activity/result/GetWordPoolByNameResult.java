package com.nashss.se.creativecompanion.activity.result;

import com.nashss.se.creativecompanion.models.WordPoolModel;

public class GetWordPoolByNameResult {

    private final WordPoolModel wordPool;

    private GetWordPoolByNameResult(WordPoolModel wordPool) {
        this.wordPool = wordPool;
    }

    public WordPoolModel getWordPoolByName() {
        return wordPool;
    }

    @Override
    public String toString() {
        return "GetWordPoolByNameResult{" +
                "wordPool=" + wordPool +
                '}';
    }

    /**
     * builder method for the GetWordPoolByNameResult class.
     * @return Builder object
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private WordPoolModel builderWordPool ;

        /**
         * withWordPoolName method for the GetWordPoolByNameResult builder class.
         * @param wordPool String representing the WordPoolModel.
         * @return Builder
         */
        public Builder withWordPoolName(WordPoolModel wordPool) {
            this.builderWordPool = wordPool;
            return this;
        }

        /**
         * Builds a result for the wordPool.
         * @return GetWordPoolByNameResult
         */
        public GetWordPoolByNameResult build() {
            return new GetWordPoolByNameResult(builderWordPool);
        }
    }


}

