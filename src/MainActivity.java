package com.example.foodsaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void gotoLoginActivity(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void gotoLocateFN(View v){
        Intent intent = new Intent(this, LocateFoodActivity.class);
        startActivity(intent);
    }


}