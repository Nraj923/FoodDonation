package com.example.foodsaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    public void gotoHomePage(View v){
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
        Toast.makeText(EditProfileActivity.this, "Profile Updated Successfully",
                Toast.LENGTH_LONG).show();
    }
}