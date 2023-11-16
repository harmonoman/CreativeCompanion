package com.nashss.se.creativecompanion.activity;

//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.nashss.se.creativecompanion.dynamodb.WordPoolDao;
import com.nashss.se.creativecompanion.dynamodb.models.*;
import com.nashss.se.creativecompanion.requests.CreateWordPoolRequest;
import com.nashss.se.creativecompanion.results.CreateProjectResult;
import com.nashss.se.creativecompanion.results.CreateWordPoolResult;
import com.nashss.se.creativecompanion.utils.ServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;

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


        //newProject.setInventory(new HashSet<>()); // what should this be? what does a Project hold?

        wordPoolDao.saveWordPool(newWordPool);

        WordPoolModel wordPoolModel = new ModelConverter().toWordPoolModel(newWordPool);
        return CreateWordPoolResult.builder()
                .withWordPool(wordPoolModel)
                .build();
    }
}
