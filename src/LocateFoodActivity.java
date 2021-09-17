package com.example.foodsaver;
import java.util.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryBounds;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;


import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LocateFoodActivity extends AppCompatActivity implements  FetchAddressTask.OnTaskCompleted  {
        private static final int REQUEST_LOCATION_PERMISSION = 1;
        private static final String TRACKING_LOCATION_KEY = "tracking_location";
        private static final String TAG = "";
    private static FirebaseFirestore db;
    private boolean mTrackingLocation;
        private FusedLocationProviderClient mFusedLocationClient;
        private LocationCallback mLocationCallback;
        private Button mLocationButton;
        private Location mLastLocation;
        public TextView mLocationTextView;
        private ListView mDonatorsList;
        private ArrayAdapter<String> donatorArrayAdapter;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            TextView integerTextView,stringTextView;

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_locate_food);

            //displaying back button
            assert getSupportActionBar() != null;   //null check
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

            setTitle("Food Near You");
            // OR You can also use the line below
            // setTitle("MyTitle")
            setContentView(R.layout.activity_locate_food);

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(
                    this);

            mLocationButton = findViewById(R.id.buttonLocate);
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            if (savedInstanceState != null) {
                mTrackingLocation = savedInstanceState.getBoolean(
                        TRACKING_LOCATION_KEY);
            }
//        mLocationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getLocation();
//            }
//        });

            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (mTrackingLocation) {
                        new FetchAddressTask(com.example.foodsaver.LocateFoodActivity.this, com.example.foodsaver.LocateFoodActivity.this)
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


//        public void gotoLoginActivity(View v){
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//        }

//        public void gotoLocateFN(View v){
//            Intent intent = new Intent(this, LocateFoodActivity.class);
//            startActivity(intent);
//        }

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
//                mLocationTextView.setText(R.string.textview_hint);
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
   static double arr[] = new double[2];
        @Override
        public void onTaskCompleted(String result) {
            if (mTrackingLocation) {
                // Update the UI
                String[] tokens = result.split(",");
                for (int i=0;i<2;i++) {
                    arr[i] = Double.valueOf(tokens[i]);
                }
//                static double lat1 = arr[0];
//                static double long1 = arr[1];
                double dis = calculateDistance(arr[0],arr[1],startlat1, startlong1);
                dis = dis*0.00062137119224;

//                mLocationTextView.setText(getString(R.string.distanceto_text,
//                        dis, System.currentTimeMillis()));

            }

        }
        double startlat1 = 37.274200;
        double startlong1 = -121.868240;


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


    //implementation of back click:
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public static double calculateDistance(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
        float[] results = new float[3];
        Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results);
        return results[0];
    }
    public  void queryHashes(View v) {
        db = FirebaseFirestore.getInstance();
        final GeoLocation center = new GeoLocation(arr[0], arr[1]);
        final double radiusInM = 50 * 1000;

        List<GeoQueryBounds> bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM);
        final List<Task<QuerySnapshot>> tasks = new ArrayList<>();
        for (GeoQueryBounds b : bounds) {
            Query q = db.collection("users")
                    .orderBy("geohash")
                    .startAt(b.startHash)
                    .endAt(b.endHash);

            tasks.add(q.get());
        }
        Tasks.whenAllComplete(tasks)
                .addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Task<?>>> t) {
                        List<DocumentSnapshot> matchingDocs = new ArrayList<>();

                        for (Task<QuerySnapshot> task : tasks) {
                            QuerySnapshot snap = task.getResult();
                            for (DocumentSnapshot doc : snap.getDocuments()) {
                                double lat = doc.getDouble("Lat");
                                double lng = doc.getDouble("Long");

                                // We have to filter out a few false positives due to GeoHash
                                // accuracy, but most will match
                                GeoLocation docLocation = new GeoLocation(lat, lng);
                                double distanceInM = GeoFireUtils.getDistanceBetween(docLocation, center);
                                if (distanceInM <= radiusInM) {
                                    matchingDocs.add(doc);
                                }
                            }
                        }

                        mDonatorsList = findViewById(R.id.donatorList);

                        List<String> donators = new ArrayList<>();

                        for(int i=0; i < matchingDocs.size(); i++){

                            String donator;
                            donator = (matchingDocs.get(i).get("Store") + ", " + matchingDocs.get(i).get("Street")
                                    + " "+ matchingDocs.get(i).get("City")
                                    + ", " + matchingDocs.get(i).get("State")+ ", " + matchingDocs.get(i).get("Zip")+ ", " + matchingDocs.get(i).get("Country")+
                            " \nFoods Available: "+ matchingDocs.get(i).get("fooditems"));
                            donators.add(donator);
                        }

                        donatorArrayAdapter = new ArrayAdapter<String>(LocateFoodActivity.this, android.R.layout.simple_list_item_1, donators);
                        mDonatorsList.setAdapter(donatorArrayAdapter);

                    }
                });




    }




}