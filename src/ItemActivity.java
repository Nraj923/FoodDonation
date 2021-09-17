package com.example.foodsaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class ItemActivity extends AppCompatActivity {
    private static FirebaseFirestore db;
    Map<String, String> items = new HashMap<>();
    EditText mItem ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        db = FirebaseFirestore.getInstance();
    }

    public void addItems(View v){
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

//        String n = (String) b.get("username");

        mItem = findViewById(R.id.editTextTextPersonName);
        items.put("fooditems", mItem.getText().toString());

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String name = sharedPref.getString("username", "not found");
        CollectionReference citiesRef = db.collection("users");
        Query query = citiesRef.whereEqualTo("User", name);


        db.collection("users").document("nzn2yLZOEipfWK6jlhaX").set(items, SetOptions.merge());
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Toast.makeText(ItemActivity.this, "Food Items added",
//                        Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                String error = e.getMessage();
//                Toast.makeText(ItemActivity.this, "Error: " + error,
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
    }

}