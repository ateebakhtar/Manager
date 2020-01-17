package com.example.admin.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class mainmenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
    }
    void open1(View view)
    {
        Intent p = new Intent(this,inventory.class);
        startActivity(p);
    }
    void open2(View view)
    {
        Intent p = new Intent(this,workinglist.class);
        startActivity(p);
    }
    void open3(View view)
    {
        Intent p = new Intent(this,viewaccount.class);
        startActivity(p);
    }
}
