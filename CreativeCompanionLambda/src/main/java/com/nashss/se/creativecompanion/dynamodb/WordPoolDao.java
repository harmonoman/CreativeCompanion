package com.nashss.se.creativecompanion.dynamodb;

import com.nashss.se.creativecompanion.dynamodb.models.WordPool;
import com.nashss.se.creativecompanion.exceptions.WordPoolNotFoundException;
import com.nashss.se.creativecompanion.metrics.MetricsConstants;
import com.nashss.se.creativecompanion.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class WordPoolDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a WordPoolDao object.
     *
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the word-pools table
     * @param metricsPublisher the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public WordPoolDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Returns the {@link WordPool} corresponding to the specified id.
     *
     * @param userId    the User ID
     * @param wordPoolId the WordPool ID
     * @return the stored WordPool, or null if none was found.
     */
    public WordPool getWordPool(String userId, String wordPoolId) {
        WordPool wordPool = this.dynamoDbMapper.load(WordPool.class, userId, wordPoolId);

        if (wordPool == null) {
            metricsPublisher.addCount(MetricsConstants.GETWORDPOOL_WORDPOOLNOTFOUND_COUNT, 1);
            throw new WordPoolNotFoundException("Could not find wordPool with id " + wordPoolId);

        }
        metricsPublisher.addCount(MetricsConstants.GETWORDPOOL_WORDPOOLNOTFOUND_COUNT, 0);
        return wordPool;
    }

    /**
     * Saves (creates or updates) the given wordPool.
     *
     * @param wordPool The wordPool to save
     * @return The WordPool object that was saved
     */
    public WordPool saveWordPool(WordPool wordPool) {
        this.dynamoDbMapper.save(wordPool);
        return wordPool;
    }

    /**
     * Perform a search (via a "scan") of the word-pools table for wordPools matching the given criteria.
     *
     * @param userId a String containing the UserId.
     * @return a List of WordPool objects that were made by the User.
     */
    public List<WordPool> getUserWordPools(String userId) {
        WordPool wordPool = new WordPool();
        wordPool.setUserId(userId);

        DynamoDBQueryExpression<WordPool> dynamoDBQueryExpression = new DynamoDBQueryExpression<WordPool>()
                .withHashKeyValues(wordPool);
        DynamoDBMapper mapper = new DynamoDBMapper(DynamoDBClientProvider.getDynamoDBClient());

        PaginatedQueryList<WordPool> wordPoolList = mapper.query(WordPool.class, dynamoDBQueryExpression);
        return wordPoolList;
    }

    /**
     * Deletes the wordPool corresponding to the specified id.
     *
     * @param userId    the User ID
     * @param wordPoolId the WordPool ID
     * @return true if the deletion was successful, false otherwise
     */
    public boolean deleteWordPool(String userId, String wordPoolId) {
        // Check if the wordPool exists before deletion
        WordPool wordPool = getWordPool(userId, wordPoolId);
        if (wordPool != null) {
            this.dynamoDbMapper.delete(wordPool);
            // Deletion successful
            return true;
        } else {
            // WordPool not found, deletion unsuccessful
            return false;
        }
    }

    /**
     *
     * @param userId String for the user Id.
     * @param wordPoolName String for the wordPool name.
     * @return WordPool object.
     */
    public WordPool getWordPoolByName(String userId, String wordPoolName) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":userId", new AttributeValue().withS(userId));
        valueMap.put(":wordPoolName", new AttributeValue().withS(wordPoolName));
        DynamoDBQueryExpression<WordPool> queryExpression = new DynamoDBQueryExpression<WordPool>()
                .withIndexName(WordPool.WORD_POOL_NAME_INDEX)
                .withConsistentRead(false)
                .withKeyConditionExpression("userId = :userId AND wordPoolName = :wordPoolName")
                .withExpressionAttributeValues(valueMap)
                .withLimit(1);

        PaginatedQueryList<WordPool> queryResult = dynamoDbMapper.query(WordPool.class, queryExpression);

        if (!queryResult.isEmpty()) {
            return queryResult.get(0);
        } else {
            return null;
        }
    }
}
