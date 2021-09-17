package com.example.foodsaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class RegisterActivity extends AppCompatActivity implements  FetchAddressTask.OnTaskCompleted{
    public static final String MyPREFERENCES = "MyPrefs" ;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String TRACKING_LOCATION_KEY = "tracking_location";
    private static final String TAG = "";
    private boolean mTrackingLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private Button mLocationButton1;
    private Location mLastLocation;
    private TextView mLocationTextView;
    Map<String, Object> userMap = new HashMap<>();

    EditText mUserName;
    EditText mPassword;
    EditText mPhone;
    EditText mStore;

    String mLat;
    String mLong;
    EditText mStreet;
    EditText mCity;

    EditText mState;
    EditText mZip;
    EditText mCountry;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = FirebaseFirestore.getInstance();

        //displaying back button
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        setTitle(R.string.register);
        // OR You can also use the line below
        // setTitle("MyTitle")
        setContentView(R.layout.activity_register);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(
                this);
        mLocationTextView = (TextView) findViewById(R.id.editTextCity);
        mLocationButton1 = findViewById(R.id.buttonRegister);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (savedInstanceState != null) {
            mTrackingLocation = savedInstanceState.getBoolean(
                    TRACKING_LOCATION_KEY);
        }

//        mLocationButton1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getLocation();
//            }
//        });

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (mTrackingLocation) {
                    new FetchAddressTask(RegisterActivity.this, RegisterActivity.this)
                            .execute(locationResult.getLastLocation());
                }
            }
        };
        getLocation();
    }

    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
        else{
            mTrackingLocation = true;
            mFusedLocationClient.requestLocationUpdates
                    (getLocationRequest(),
                            mLocationCallback,
                            null /* Looper */);
          //mLocationTextView.setText(R.string.textview_hint);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                // If the permission is granted, get the location,
                // otherwise, show a Toast
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(this,
                            R.string.location_permission_denied,
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    Double arr[] = new Double[2];
    @Override
    public void onTaskCompleted(String result) {
        if (mTrackingLocation) {
            // Update the UI
            String[] tokens = result.split(",");
//            arr[0] = tokens[0];
//            arr[1] = tokens[1];

            for (int i=0;i<2;i++) {
                arr[i] = Double.parseDouble(tokens[i]);
            }
            String hash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(arr[0], arr[1]));
            userMap.put("geohash", hash);
            userMap.put("Lat", arr[0]);
            userMap.put("Long", arr[1]);

//            double dis = calculateDistance(arr[0],arr[1],startlat1, startlong1);
//            dis = dis*0.00062137119224;

//            mLocationTextView.setText(getString(R.string.newtest,
//                    dis, System.currentTimeMillis()));
        }
    }
    double startlat1 = 37.274200;
    double startlong1 = -121.868240;

    public static double calculateDistance(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
        float[] results = new float[3];
        Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results);
        return results[0];
    }

    private void startTrackingLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            mTrackingLocation = true;
            mFusedLocationClient.requestLocationUpdates
                    (getLocationRequest(),
                            mLocationCallback,
                            null /* Looper */);
        }
    }

    @Override
    protected void onResume() {
        if (mTrackingLocation) {
            getLocation();
        }
        super.onResume();
    }


    public void goToHomePage(){
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
    }
    //implementation of back click:
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void updateProfile(View v) {
//        getLocation();

        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        mUserName = findViewById(R.id.editTextPersonUserName);
        mPassword = findViewById(R.id.editTextPassword);
        mPhone = findViewById(R.id.editTextPhone);
        mStore = findViewById(R.id.editTextStore);

        mStreet = findViewById(R.id.editTextAddress);
        mCity = findViewById(R.id.editTextCity);

        mState = findViewById(R.id.editTextState);
        mZip = findViewById(R.id.editTextZip);
        mCountry = findViewById(R.id.editTextCountry);

        userMap.put("User", mUserName.getText().toString());
        userMap.put("Password", mPassword.getText().toString());
        userMap.put("Phone", mPhone.getText().toString());
        userMap.put("Store", mStore.getText().toString());

        userMap.put("Street", mStreet.getText().toString());
        userMap.put("City", mCity.getText().toString());
        userMap.put("State", mState.getText().toString());
        userMap.put("Zip", mZip.getText().toString());
        userMap.put("Country", mCountry.getText().toString());

        editor.putString("username", mUserName.getText().toString());
        editor.commit();



        db.collection("users").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(RegisterActivity.this, "Login info added",
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getMessage();
                Toast.makeText(RegisterActivity.this, "Error: " + error,
                        Toast.LENGTH_SHORT).show();
            }
        });
        goToHomePage();
    }

}