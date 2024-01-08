package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.converters.ModelConverter;
import com.nashss.se.creativecompanion.dynamodb.WordPoolDao;
import com.nashss.se.creativecompanion.dynamodb.models.WordPool;
import com.nashss.se.creativecompanion.models.WordPoolModel;
import com.nashss.se.creativecompanion.activity.request.GetWordPoolByNameRequest;
import com.nashss.se.creativecompanion.activity.result.GetWordPoolByNameResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the GetWordPoolByNameActivity for the CreativeCompanionService's GetWordPoolByName API.
 * <p>
 * This API allows the customer to get a specific wordPool by name and see it's details.
 */
public class GetWordPoolByNameActivity {

    private final Logger log = LogManager.getLogger();
    private final WordPoolDao wordPoolDao;

    /**
     * Instantiates a new GetWordPoolByNameActivity object.
     *
     * @param wordPoolDao WordPoolDao to access the wordPool table.
     */
    @Inject
    public GetWordPoolByNameActivity(WordPoolDao wordPoolDao) {
        this.wordPoolDao = wordPoolDao;
    }

    /**
     * This method handles the incoming request by retrieving a specified user's wordPool from the database by Name.
     * <p>
     * It then returns the matching wordPool.
     *
     * @param getWordPoolByNameRequest request object containing the User ID & the Word Pool Id
     * @return getWordPoolResult result object containing the wordPool requested that were created by that User ID
     */
    public GetWordPoolByNameResult handleRequest(final GetWordPoolByNameRequest getWordPoolByNameRequest) {
        log.info("Received GetWordPoolByNameRequest {}", getWordPoolByNameRequest);

        WordPool result = wordPoolDao.getWordPoolByName(getWordPoolByNameRequest.getUserId(),
                getWordPoolByNameRequest.getWordPoolName());
        WordPoolModel wordPoolModel = new ModelConverter().toWordPoolModel(result);
        System.out.println("***** inside GetWordPoolByNameActivity *****: " + result);
        return GetWordPoolByNameResult.builder()
                .withWordPoolName(wordPoolModel)
                .build();
    }
}

