package com.nashss.se.creativecompanion.dependency;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.creativecompanion.dynamodb.DynamoDBClientProvider;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Dagger Module providing dependencies for DAO classes.
 */
@Module
public class DaoModule {
    /**
     * Provides a DynamoDBMapper singleton instance.
     *
     * @return DynamoDBMapper object
     */
    @Singleton
    @Provides
    public DynamoDBMapper provideDynamoDBMapper() {
        return new DynamoDBMapper(DynamoDBClientProvider.getDynamoDBClient(Regions.US_EAST_2));
    }
}
