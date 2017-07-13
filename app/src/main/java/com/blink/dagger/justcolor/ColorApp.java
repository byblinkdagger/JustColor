package com.blink.dagger.justcolor;

import android.app.Application;

import com.litesuits.orm.LiteOrm;

/**
 * Created by lucky on 2017/7/12.
 */
public class ColorApp extends Application {

    private static final String DB_NAME = "color.db";
    public static LiteOrm db;

    @Override
    public void onCreate() {
        super.onCreate();
        if (db == null){
            db = LiteOrm.newSingleInstance(this,DB_NAME);
        }
        db.setDebugged(true);
    }
}
