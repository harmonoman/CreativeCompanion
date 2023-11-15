package com.nashss.se.creativecompanion.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.nashss.se.creativecompanion.dynamodb.models.Project;
import com.nashss.se.creativecompanion.dynamodb.models.WordPool;
import com.nashss.se.creativecompanion.exceptions.ProjectNotFoundException;
import com.nashss.se.creativecompanion.exceptions.WordPoolNotFoundException;

import com.nashss.se.creativecompanion.metrics.MetricsConstants;
import com.nashss.se.creativecompanion.metrics.MetricsPublisher;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
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
}
