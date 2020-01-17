package com.example.admin.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class managermenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managermenu);
    }
    void staff(View view)
    {
        Intent p = new Intent(this,viewworking.class);
        startActivity(p);
    }
    void open1(View view)
    {
        Intent p = new Intent(this,inventory.class);
        startActivity(p);
    }
    void open2(View view)
    {
        Intent p = new Intent(this,editworking.class);
        startActivity(p);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {

        Intent setIntent = new Intent(this,Login.class);
        startActivity(setIntent);
    }

}
