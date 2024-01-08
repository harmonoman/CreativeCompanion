package com.nashss.se.creativecompanion.dependency;

import com.nashss.se.creativecompanion.activity.CreateProjectActivity;
import com.nashss.se.creativecompanion.activity.CreateWordPoolActivity;
import com.nashss.se.creativecompanion.activity.DeleteProjectActivity;
import com.nashss.se.creativecompanion.activity.DeleteWordPoolActivity;
import com.nashss.se.creativecompanion.activity.GetProjectActivity;
import com.nashss.se.creativecompanion.activity.GetProjectByNameActivity;
import com.nashss.se.creativecompanion.activity.GetProjectListActivity;
import com.nashss.se.creativecompanion.activity.GetWordPoolActivity;
import com.nashss.se.creativecompanion.activity.GetWordPoolByNameActivity;
import com.nashss.se.creativecompanion.activity.GetWordPoolListActivity;
import com.nashss.se.creativecompanion.activity.UpdateProjectActivity;
import com.nashss.se.creativecompanion.activity.UpdateWordPoolActivity;

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

    /**
     * Provides the relevant activity.
     * @return CreateWordPoolActivity
     */
    CreateWordPoolActivity provideCreateWordPoolActivity();

    /**
     * Provides the relevant activity.
     * @return GetProjectActivity
     */
    GetProjectActivity provideGetProjectActivity();

    /**
     * Provides the relevant activity.
     * @return GetWordPoolActivity
     */
    GetWordPoolActivity provideGetWordPoolActivity();

    /**
     * Provides the relevant activity.
     * @return GetProjectListActivity
     */
    GetProjectListActivity provideGetProjectListActivity();

    /**
     * Provides the relevant activity.
     * @return GetWordPoolListActivity
     */
    GetWordPoolListActivity provideGetWordPoolListActivity();

    /**
     * Provides the relevant activity.
     * @return UpdateProjectActivity
     */
    UpdateProjectActivity provideUpdateProjectActivity();

    /**
     * Provides the relevant activity.
     * @return UpdateWordPoolActivity
     */
    UpdateWordPoolActivity provideUpdateWordPoolActivity();

    /**
     * Provides the relevant activity.
     * @return DeleteProjectActivity
     */
    DeleteProjectActivity provideDeleteProjectActivity();

    /**
     * Provides the relevant activity.
     * @return DeleteWordPoolActivity
     */
    DeleteWordPoolActivity provideDeleteWordPoolActivity();

    /**
     * Provides the relevant activity.
     * @return GetProjectByNameActivity
     */
    GetProjectByNameActivity provideGetProjectByNameActivity();

    /**
     * Provides the relevant activity.
     * @return GetWordPoolByNameActivity
     */
    GetWordPoolByNameActivity provideGetWordPoolByNameActivity();
}
