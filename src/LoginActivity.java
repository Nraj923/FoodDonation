package com.example.foodsaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    EditText userName;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //displaying back button
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        setTitle(R.string.login);
        // OR You can also use the line below
        // setTitle("MyTitle")
        setContentView(R.layout.activity_login);

    }

    public void gotoRegisterActivity (View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

            @Override
        public boolean onSupportNavigateUp () {
            finish();
            return true;
        }
            public void gotoHomePage () {
            Intent intent = new Intent(this, Homepage.class);
            startActivity(intent);
        }



    FirebaseFirestore db;

    public void onClick(View v) {
        userName = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        switch (v.getId()) {
            case R.id.buttonLogin:
                db = FirebaseFirestore.getInstance();
                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                        String a = doc.getString("User");
                                        String b = doc.getString("Password");
                                        String a1 = userName.getText().toString().trim();
                                        String b1 = password.getText().toString().trim();
                                        if (a.equalsIgnoreCase(a1) && b.equalsIgnoreCase(b1)) {
                                            Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                             gotoHomePage();
                                             break;
                                        } //else
                                         //   Toast.makeText(LoginActivity.this, "Cannot login,incorrect Email and Password", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            }
                        });
                break;

        }

//    public void validateUser(View v) {
//        db = FirebaseFirestore.getInstance();
//        db.collection("users")
//                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                userName = findViewById(R.id.editTextUsername);
//                password = findViewById(R.id.editTextPassword);
//
//                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
//                    if (snapshot.getString("User").equals(userName.getText().toString())) {
//                        if (snapshot.getString("Password").equals(password.getText().toString())) {
//                            gotoHomePage();
//                        } else {
//                            Toast.makeText(LoginActivity.this, "Password Incorrect",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Username Not Found",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//    }

        //when user click register text


        //when user click login
//        public void gotoHomePage () {
//            Intent intent = new Intent(this, Homepage.class);
//            startActivity(intent);
//        }

        //implementation of back click:
//        @Override
//        public boolean onSupportNavigateUp () {
//            finish();
//            return true;
//        }

    }
    }