package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.dynamodb.WordPoolDao;
import com.nashss.se.creativecompanion.dynamodb.models.*;
import com.nashss.se.creativecompanion.metrics.MetricsPublisher;
import com.nashss.se.creativecompanion.requests.UpdateWordPoolRequest;

import com.nashss.se.creativecompanion.results.UpdateWordPoolResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the UpdateWordPoolActivity for the CreativeCompanionService's UpdateWordPool API.
 *
 * This API allows the customer to update their saved wordPool's information.
 */
public class UpdateWordPoolActivity {

    private final Logger log = LogManager.getLogger();
    private final WordPoolDao wordPoolDao;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a new UpdateWordPoolActivity object.
     *
     * @param wordPoolDao
     * @param metricsPublisher
     */
    @Inject
    public UpdateWordPoolActivity(WordPoolDao wordPoolDao, MetricsPublisher metricsPublisher) {
        this.wordPoolDao = wordPoolDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * This method handles the incoming request by retrieving the wordPool, updating it,
     * and persisting the wordPool.
     * <p>
     * It then returns the updated wordPool.
     * <p>
     * If the wordPool does not exist, this should throw a WordPoolNotFoundException.
     * <p>
     *
     * @param updateWordPoolRequest request object containing the wordPool ID, wordPool name, and user ID
     *                              associated with it
     * @return updateWordPoolResult result object containing the API defined {@link WordPoolModel}
     */
    public UpdateWordPoolResult handleRequest(final UpdateWordPoolRequest updateWordPoolRequest) {
        log.info("Received UpdateWordPoolRequest {}", updateWordPoolRequest);

        WordPool wordPool = new WordPool();
        wordPool.setUserId(updateWordPoolRequest.getUserId());
        wordPool.setWordPoolId(updateWordPoolRequest.getWordPoolId());
        wordPool.setWordPoolName(updateWordPoolRequest.getWordPoolName());
        wordPool.setWordPool(updateWordPoolRequest.getWordPool());
        wordPool = wordPoolDao.saveWordPool(wordPool);

        WordPoolModel wordPoolModel = new ModelConverter().toWordPoolModel(wordPool);

        return UpdateWordPoolResult.builder()
                .withWordPool(wordPoolModel)
                .build();
    }





}
