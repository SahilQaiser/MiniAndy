package com.sq.myfb2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {
private TextView username;
private long timeBackPressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        username = findViewById(R.id.userDashboard);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Intent i = getIntent();
        String uname = i.getStringExtra("USERNAME");
        username.setText(uname);
        //Toast.makeText(this.getBaseContext(),"Welcome "+uname,Toast.LENGTH_LONG).show();
        Snackbar.make(findViewById(R.id.userDashboard),"Welcome "+uname,Snackbar.LENGTH_LONG)
                .setAction("Action",null).show();
    }

    @Override
    public void onBackPressed() {
        if(timeBackPressed +2000 > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(Dashboard.this,"Press Back Again to exit",Toast.LENGTH_SHORT).show();
        }
        timeBackPressed = System.currentTimeMillis();
    }
}
