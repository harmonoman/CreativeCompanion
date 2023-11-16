package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.dynamodb.WordPoolDao;
import com.nashss.se.creativecompanion.dynamodb.models.*;
import com.nashss.se.creativecompanion.requests.GetWordPoolRequest;
import com.nashss.se.creativecompanion.results.GetWordPoolResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the GetWordPoolActivity for the CreativeCompanionService's GetWordPool API.
 * <p>
 * This API allows the customer to see the details of a specific wordPool.
 */
public class GetWordPoolActivity {

    private final Logger log = LogManager.getLogger();
    private final WordPoolDao wordPoolDao;

    /**
     * Instantiates a new GetWordPoolActivity object.
     *
     * @param wordPoolDao WordPoolDao to access the word-pool table.
     */
    @Inject
    public GetWordPoolActivity(WordPoolDao wordPoolDao) {
        this.wordPoolDao = wordPoolDao;
    }

    /**
     * This method handles the incoming request by retrieving a specified user's WordPool from the database.
     * <p>
     * It then returns the matching wordPool.
     *
     * @param getWordPoolRequest request object containing the User ID & the WordPool Id
     * @return getWordPoolResult result object containing the wordPool requested that were created by that User ID
     */
    public GetWordPoolResult handleRequest(final GetWordPoolRequest getWordPoolRequest) {
        log.info("Received GetWordPoolRequest {}", getWordPoolRequest);

        WordPool result = wordPoolDao.getWordPool(getWordPoolRequest.getUserId(), getWordPoolRequest.getWordPoolId());
        WordPoolModel wordPoolModel = new ModelConverter().toWordPoolModel(result);

        return GetWordPoolResult.builder()
                .withWordPool(wordPoolModel)
                .build();
    }
}
