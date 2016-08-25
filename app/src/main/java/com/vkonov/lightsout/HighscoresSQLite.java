package com.vkonov.lightsout;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;

public class HighscoresSQLite extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "LightOutDB";
    static final String HIGHSCORES_TABLE_NAME = "highscores";
    static final int DATABASE_VERSION = 2;
    static final String PLAYER_NAME_KEY = "PLAYER_NAME";
    static final String PLAYER_SCORE_KEY = "SCORE";
    static final String GRID_SIZE_KEY = "GRIDSIZE";
    static final String _ID = "_id";
    private static final String HIGHSCORES_TABLE_CREATE =
            "CREATE TABLE " + HIGHSCORES_TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ", " +
                    PLAYER_NAME_KEY + " TEXT NOT NULL" + ", " +
                    PLAYER_SCORE_KEY + " INT NOT NULL" + ", " +
                    GRID_SIZE_KEY + " INT NOT NULL" + ");";

    HighscoresSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HIGHSCORES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // noop
    }
}