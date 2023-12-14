package com.nashss.se.creativecompanion.activity;

import com.nashss.se.creativecompanion.converters.ModelConverter;
import com.nashss.se.creativecompanion.dynamodb.WordPoolDao;
import com.nashss.se.creativecompanion.dynamodb.models.WordPool;
import com.nashss.se.creativecompanion.models.WordPoolModel;
import com.nashss.se.creativecompanion.activity.request.CreateWordPoolRequest;
import com.nashss.se.creativecompanion.activity.result.CreateWordPoolResult;
import com.nashss.se.creativecompanion.utils.ServiceUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import javax.inject.Inject;

/**
 * Implementation of the CreateWordPoolActivity for the CreativeCompanionService's CreateWordPool API.
 * <p>
 * This API allows the customer to create a new wordPool with no data.
 */
public class CreateWordPoolActivity {

    private final Logger log = LogManager.getLogger();
    private final WordPoolDao wordPoolDao;

    /**
     * Instantiates a new CreateWordPoolActivity object.
     *
     * @param wordPoolDao WordPoolDao to access the word-pools table.
     */
    @Inject
    public CreateWordPoolActivity(WordPoolDao wordPoolDao) {
        this.wordPoolDao = wordPoolDao;
    }

    /**
     * This method handles the incoming request by persisting a new project
     * with the provided project name and customer ID from the request.
     * <p>
     * It then returns the newly created project.
     * <p>
     * If the provided project name or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param createWordPoolRequest request object containing the project name and customer ID
     *                              associated with it
     * @return createWordPoolResult result object containing the API defined {@link WordPoolModel}
     */
    public CreateWordPoolResult handleRequest(final CreateWordPoolRequest createWordPoolRequest) {
        log.info("Received CreateWordPoolRequest {}", createWordPoolRequest);

        WordPool newWordPool = new WordPool();
        newWordPool.setUserId(createWordPoolRequest.getUserId());
        newWordPool.setWordPoolId(ServiceUtils.generateWordPoolId());
        newWordPool.setWordPoolName(createWordPoolRequest.getWordPoolName());
        newWordPool.setWordPool(new ArrayList<>());

        wordPoolDao.saveWordPool(newWordPool);

        WordPoolModel wordPoolModel = new ModelConverter().toWordPoolModel(newWordPool);
        return CreateWordPoolResult.builder()
                .withWordPool(wordPoolModel)
                .build();
    }
}
