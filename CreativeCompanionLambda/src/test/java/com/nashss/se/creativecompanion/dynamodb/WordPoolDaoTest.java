package com.nashss.se.creativecompanion.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.creativecompanion.dynamodb.ProjectDao;
import com.nashss.se.creativecompanion.dynamodb.WordPoolDao;
import com.nashss.se.creativecompanion.dynamodb.models.Project;
import com.nashss.se.creativecompanion.dynamodb.models.WordPool;
import com.nashss.se.creativecompanion.exceptions.ProjectNotFoundException;
import com.nashss.se.creativecompanion.exceptions.WordPoolNotFoundException;
import com.nashss.se.creativecompanion.metrics.MetricsConstants;
import com.nashss.se.creativecompanion.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

public class WordPoolDaoTest {

    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @Mock
    private MetricsPublisher metricsPublisher;


    private WordPoolDao wordPoolDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        wordPoolDao = new WordPoolDao(dynamoDBMapper, metricsPublisher);
    }

    @Test
    public void getWordPool_withUserIdAndWordPoolId_callsMapperWithPartitionKey() {
        // GIVEN
        String userId = "userId";
        String wordPoolId = "wordPoolId";
        when(dynamoDBMapper.load(WordPool.class, userId,wordPoolId)).thenReturn(
                new WordPool());

        // WHEN
        WordPool wordPool = wordPoolDao.getWordPool(userId, wordPoolId);

        // THEN
        assertNotNull(wordPool);
        verify(dynamoDBMapper).load(WordPool.class, userId, wordPoolId);
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETWORDPOOL_WORDPOOLNOTFOUND_COUNT),
                anyDouble());

    }

    @Test
    public void getWordPool_projectIdNotFound_throwsWordPoolNotFoundException() {
        // GIVEN
        String nonexistentUserId = "NotReal";
        String nonexistentWordPoolId = "NotReal";
        when(dynamoDBMapper.load(WordPool.class,
                nonexistentUserId,nonexistentWordPoolId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(WordPoolNotFoundException.class, () -> wordPoolDao.getWordPool(
                nonexistentUserId, nonexistentWordPoolId));
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETWORDPOOL_WORDPOOLNOTFOUND_COUNT),
                anyDouble());
    }

    @Test
    public void saveWordPool_callsMapperWithWordPool() {
        // GIVEN
        WordPool wordPool = new WordPool();

        // WHEN
        WordPool result = wordPoolDao.saveWordPool(wordPool);

        // THEN
        verify(dynamoDBMapper).save(wordPool);
        assertEquals(wordPool, result);
    }

}
