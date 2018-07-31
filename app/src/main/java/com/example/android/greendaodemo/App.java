package com.example.android.greendaodemo;

import android.app.Application;

import com.example.android.greendaodemo.bean.DaoMaster;
import com.example.android.greendaodemo.bean.DaoSession;
import com.facebook.stetho.Stetho;

import org.greenrobot.greendao.database.Database;

/**
 * Created by DuMingwei on 2018/7/31.
 * Description:
 */
public class App extends Application {

    private static final boolean ENCRYPTED = false;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        Stetho.initializeWithDefaults(this);
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
