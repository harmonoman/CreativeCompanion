package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.dynamodb.WordPoolDao;
import com.nashss.se.creativecompanion.dynamodb.models.ModelConverter;
import com.nashss.se.creativecompanion.dynamodb.models.WordPool;
import com.nashss.se.creativecompanion.dynamodb.models.WordPoolModel;
import com.nashss.se.creativecompanion.requests.GetWordPoolListRequest;
import com.nashss.se.creativecompanion.results.GetWordPoolListResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.inject.Inject;

/**
 * Implementation of the GetWordPoolListActivity for the CreativeCompanionService's GetWordPool API.
 * <p>
 * This API allows the customer to see all of their wordPools.
 */
public class GetWordPoolListActivity {

    private final Logger log = LogManager.getLogger();
    private final WordPoolDao wordPoolDao;

    /**
     * Instantiates a new GetWordPoolListActivity object.
     *
     * @param wordPoolDao WordPoolDao to access the word-pools table.
     */
    @Inject
    public GetWordPoolListActivity(WordPoolDao wordPoolDao) {
        this.wordPoolDao = wordPoolDao;
    }

    /**
     * This method handles the incoming request by retrieving List of a specified user's WordPools from the database.
     * <p>
     * It then returns the matching wordPools, or an empty result list if none are found.
     *
     * @param getWordPoolListRequest request object containing the User ID
     * @return getWordPoolListResult result object containing the wordPools that were created by that User ID
     */
    public GetWordPoolListResult handleRequest(final GetWordPoolListRequest getWordPoolListRequest) {
        log.info("Received GetWordPoolListRequest {}", getWordPoolListRequest);

        List<WordPool> results = wordPoolDao.getUserWordPools(getWordPoolListRequest.getUserId());
        List<WordPoolModel> wordPoolModels = new ModelConverter().toWordPoolModelList(results);

        return GetWordPoolListResult.builder()
                .withWordPools(wordPoolModels)
                .build();
    }

}
