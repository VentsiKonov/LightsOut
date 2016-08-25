package com.vkonov.lightsout;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TileAdapter extends BaseAdapter {
    private Context context;
    private boolean[] matrix;
    private boolean state;

    TileAdapter(Context context, boolean[] matrix){
        this.context = context;
        this.matrix = matrix;
        this.state = true;
    }

    @Override
    public int getCount() {
        return matrix.length;
    }

    @Override
    public Boolean getItem(int position) {
        return matrix[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if(convertView == null) {
            v = LayoutInflater.from(context).inflate(R.layout.tile, parent, false);
        }
        else{
            v = convertView;
        }

        TextView block = (TextView) v.findViewById(R.id.tv);
        block.setBackgroundColor(matrix[position] == state ? Color.RED : Color.GREEN);

        return v;
    }


    public void reinit(boolean[] matrix){
        this.matrix = matrix;
    }
}
