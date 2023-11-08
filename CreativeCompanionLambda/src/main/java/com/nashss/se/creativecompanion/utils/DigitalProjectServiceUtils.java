package com.nashss.se.creativecompanion.utils;

import org.apache.commons.lang3.RandomStringUtils;

public final class DigitalProjectServiceUtils {
    static final int GENERATED_ID_LENGTH = 5;

    private DigitalProjectServiceUtils() {
    }

    /**
     * Provides a generated project ID.
     * @return String of a generated ID.
     */
    public static String generateProjectId() {
        return RandomStringUtils.randomAlphanumeric(GENERATED_ID_LENGTH);
    }

    /**
     * Provides a generated WordPool ID.
     * @return String of a generated ID.
     */
    public static String generateWordPoolId() {
        return RandomStringUtils.randomAlphanumeric(GENERATED_ID_LENGTH);
    }

}
