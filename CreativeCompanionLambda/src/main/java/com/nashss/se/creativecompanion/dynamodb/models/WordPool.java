package com.nashss.se.creativecompanion.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

import java.util.List;
import java.util.Objects;

public class WordPool {

    private String userId;
    private String wordPoolId;
    private String wordPoolName;
    private List<String> wordPool;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return this.userId;
    }

    @DynamoDBRangeKey(attributeName = "wordPoolId")
    public String getWordPoolId() {
        return this.wordPoolId;
    }

    public void setWordPoolId(String wordPoolId) {
        this.wordPoolId = wordPoolId;
    }

    @DynamoDBAttribute(attributeName = "wordPoolName")
    public String getWordPoolName() {
        return this.wordPoolName;
    }

    public void setWordPoolName(String wordPoolName) {
        this.wordPoolName = wordPoolName;
    }

    public void setWordPool(List<String> wordPool) {
        this.wordPool = wordPool;
    }

    @DynamoDBAttribute(attributeName = "wordPool")
    public List<String> getWordPool() {
        return this.wordPool;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordPool wordPool1 = (WordPool) o;
        return Objects.equals(userId, wordPool1.userId) && Objects.equals(wordPoolId, wordPool1.wordPoolId)
                && Objects.equals(wordPoolName, wordPool1.wordPoolName) && Objects.equals(wordPool,
                wordPool1.wordPool);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, wordPoolId, wordPoolName, wordPool);
    }

    @Override
    public String toString() {
        return "WordPool{" +
                "userId='" + userId + '\'' +
                ", wordPoolId='" + wordPoolId + '\'' +
                ", wordPoolName='" + wordPoolName + '\'' +
                ", wordPool=" + wordPool +
                '}';
    }
}
