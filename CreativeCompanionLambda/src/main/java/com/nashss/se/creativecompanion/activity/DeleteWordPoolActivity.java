package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.dynamodb.WordPoolDao;
import com.nashss.se.creativecompanion.activity.request.DeleteWordPoolRequest;
import com.nashss.se.creativecompanion.activity.result.DeleteWordPoolResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
public class DeleteWordPoolActivity {

    private final Logger log = LogManager.getLogger();
    private final WordPoolDao wordPoolDao;

    /**
     * Instantiates a new DeleteWordPoolActivity object.
     *
     * @param wordPoolDao WordPoolDao to access the wordPool table.
     */
    @Inject
    public DeleteWordPoolActivity(WordPoolDao wordPoolDao) {
        this.wordPoolDao = wordPoolDao;
    }

    /**
     * This method handles the incoming request by retrieving a specified user's WordPool from the database.
     * <p>
     * It then returns the matching wordPool.
     *
     * @param deleteWordPoolRequest request object containing the User ID & the WordPool Id
     * @return deleteWordPoolResult result object containing the wordPool requested that were created by that User ID
     */
    public DeleteWordPoolResult handleRequest(final DeleteWordPoolRequest deleteWordPoolRequest) {
        log.info("Received DeleteWordPoolRequest {}", deleteWordPoolRequest);

        Boolean result = wordPoolDao.deleteWordPool(deleteWordPoolRequest.getUserId(),
                deleteWordPoolRequest.getWordPoolId());

        return DeleteWordPoolResult.builder()
                .withSuccess(result)
                .build();
    }
}
