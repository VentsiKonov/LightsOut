package com.vkonov.lightsout;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by vkonov on 7/6/16.
 */
public class HighscoresCursorAdapter extends CursorAdapter
{
    private LayoutInflater inflater;
    public HighscoresCursorAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return inflater.from(context).inflate(R.layout.highscores_row, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView playerName = ((TextView) view.findViewById(R.id.playerName));
        TextView gridSize = ((TextView) view.findViewById(R.id.gridSize));
        TextView score = ((TextView) view.findViewById(R.id.score));

        playerName.setText(cursor.getString(cursor.getColumnIndex(HighscoresSQLite.PLAYER_NAME_KEY)));
        gridSize.setText(cursor.getString(cursor.getColumnIndex(HighscoresSQLite.GRID_SIZE_KEY)));
        score.setText(cursor.getString(cursor.getColumnIndex(HighscoresSQLite.PLAYER_SCORE_KEY)));
    }
}
