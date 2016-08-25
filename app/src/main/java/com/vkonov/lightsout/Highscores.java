package com.vkonov.lightsout;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class Highscores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        ListView list = ((ListView) findViewById(R.id.highscores_table));
        HighscoresSQLite highscoresDb = new HighscoresSQLite(getApplicationContext());
        SQLiteDatabase db = highscoresDb.getReadableDatabase();
        db.beginTransaction();
        Cursor query = db.query(HighscoresSQLite.HIGHSCORES_TABLE_NAME,
                new String[]{HighscoresSQLite._ID,HighscoresSQLite.PLAYER_NAME_KEY, HighscoresSQLite.GRID_SIZE_KEY, HighscoresSQLite.PLAYER_SCORE_KEY},
                "", null, null, null, HighscoresSQLite.PLAYER_SCORE_KEY, "10");

        db.endTransaction();
        list.setAdapter(new HighscoresCursorAdapter(this, query, android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER));

    }
}
