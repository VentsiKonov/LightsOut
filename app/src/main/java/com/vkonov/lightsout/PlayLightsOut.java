package com.vkonov.lightsout;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class PlayLightsOut extends AppCompatActivity {

    private boolean[] matrix;
    private int size;
    private TileAdapter adapter;
    private GridView grid;
    private TextView hit_count;
    private static Random rand = new Random();

    private boolean checkWin(){
        for (int i = 1; i < matrix.length; i++){
            if(matrix[0] != matrix[i])
                return false;
        }
        return true;
    }

    private void flip(int position){
        matrix[position] = !matrix[position];
    }

    private void flipNeighbours(int position){
        if (position % size != 0) {
            flip(position - 1);
        }

        if (position > (size - 1)) {
            flip(position - size);
        }

        if (position < size*size - size) {
            flip(position + size);
        }

        if ((position + 1) % size != 0) {
            flip(position + 1);
        }
    }

    private boolean isSolvable() {
//        int lightsRow = 0;
//        int lightsCol = 0;
//        for(int row=0; row<size; ++row){
//            for(int col=0; col < size; ++col){
//                if(matrix[size*row + col]){
//                    ++lightsRow;
//                }
//                if(matrix[size*col + row]){
//                    ++lightsCol;
//                }
//            }
//            if(lightsRow % 2 != 0 || lightsCol % 2 != 0){
//                return false;
//            }
//        }
        return true;
    }

    private void init(){
        do {
            matrix = new boolean[size*size];
            for (int i = 0; i < matrix.length; i++){
                matrix[i] = rand.nextBoolean();
            }
        } while(!isSolvable());
    }

    private void reset(){
        init();
        adapter.reinit(matrix);
        grid.setNumColumns(size);
        grid.invalidateViews();
        hit_count.setText("0");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_lights_out);

        grid = (GridView) findViewById(R.id.gridView);
        hit_count = (TextView) findViewById(R.id.hit_count);
        int defaultSize = getResources().getInteger(R.integer.size);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String sizeString = prefs.getString(getString(R.string.gridSizeKey), String.valueOf(defaultSize));
        size = Integer.parseInt(sizeString);
        init();

        adapter = new TileAdapter(this, matrix);
        grid.setAdapter(adapter);
        grid.setNumColumns(size);
        grid.setOnItemClickListener((parent, view, position, id) -> {
            flip(position);
            flipNeighbours(position);
            adapter.notifyDataSetChanged();
            hit_count.setText(String.valueOf(Integer.parseInt(hit_count.getText().toString()) + 1));

            if (checkWin()) {
                Toast toast = Toast.makeText(PlayLightsOut.this, R.string.win_toast, Toast.LENGTH_LONG);
                toast.show();
                HighscoresSQLite highscore = new HighscoresSQLite(getApplicationContext());

                ContentValues values = new ContentValues();
                String playerName = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                        .getString(getString(R.string.playerNameKey), getString(R.string.default_player_name));
                int gridsize = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(getString(R.string.gridSizeKey), "5"));
                values.put(HighscoresSQLite.PLAYER_SCORE_KEY, Integer.parseInt(hit_count.getText().toString()));
                values.put(HighscoresSQLite.PLAYER_NAME_KEY, playerName);
                values.put(HighscoresSQLite.GRID_SIZE_KEY, gridsize);
                SQLiteDatabase db = highscore.getWritableDatabase();
                db.beginTransaction();
                db.insert(highscore.HIGHSCORES_TABLE_NAME, null, values);
                db.setTransactionSuccessful();
                db.endTransaction();

                parent.setOnItemClickListener((p, v, po, i) -> {
                    // no-op
                });
            }
        });

        Button reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(v -> reset());
    }
}
