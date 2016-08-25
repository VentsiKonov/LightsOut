package com.vkonov.lightsout;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ((Button) findViewById(R.id.btnNewGame)).setOnClickListener(this);
        ((Button) findViewById(R.id.highscoresBtn)).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.preferences:
                Intent intent = new Intent(MainMenu.this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){
        Intent i = null;
        switch (v.getId()){
            case R.id.btnNewGame:
                i = new Intent(MainMenu.this, PlayLightsOut.class);
                break;
            case R.id.highscoresBtn:
                i = new Intent(MainMenu.this, Highscores.class);
        }
        if(i != null){
            startActivity(i);
        }

    }
}
