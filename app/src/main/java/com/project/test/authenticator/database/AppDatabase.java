package com.project.test.authenticator.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Ivica on 4.2.2018..
 */

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = "AppDatabase";

    public static final int VERSION = 1;
}
