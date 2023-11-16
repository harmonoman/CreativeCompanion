package com.nashss.se.creativecompanion.dependency;

//import com.nashss.se.musicplaylistservice.activity.*;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.creativecompanion.activity.CreateProjectActivity;

import com.nashss.se.creativecompanion.activity.CreateWordPoolActivity;
import com.nashss.se.creativecompanion.activity.GetProjectActivity;
import com.nashss.se.creativecompanion.activity.GetWordPoolActivity;
import com.nashss.se.creativecompanion.requests.GetProjectRequest;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the Music Playlist Service.
 */
@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    /**
     * Provides the relevant activity.
     * @return CreatePantryActivity
     */
    CreateProjectActivity provideCreateProjectActivity();

    CreateWordPoolActivity provideCreateWordPoolActivity();

    GetProjectActivity provideGetProjectActivity();

    GetWordPoolActivity provideGetWordPoolActivity();

}
