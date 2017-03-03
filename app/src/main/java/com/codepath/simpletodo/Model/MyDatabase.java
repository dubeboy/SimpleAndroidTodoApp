package com.codepath.simpletodo.Model;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by divine on 3/3/17.
 */
@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {
    public static final  String NAME = "MyDatabase";
    public static final int VERSION = 1;
}
