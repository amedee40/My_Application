package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import java.util.ArrayList;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap, mMap;
    private DatabaseReference locationRef;
    private LatLng currentLocation;
    LatLng bus_stop1 = new LatLng(-29.862930121241327, 31.01413785007026);
    LatLng bus_stop2 = new LatLng(-29.862930121241327, 31.01413785007026);
    LatLng bus_stop3 = new LatLng(-29.8621241327, 31.01413785007026);
    LatLng bus_stop4 = new LatLng(-29.81327, 32.01413785007026);

    // creating array list for adding all our locations.
    private ArrayList<LatLng> locationArrayList;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        // Get the SearchView from the layout


        // Initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        ///////////////////////////


// Initialize the SearchView
       /* searchView = view.findViewById(R.id.idSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Called when the user submits the query
                searchBus(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Called when the query text is changed
                return false;
            }
        });*/

        ////////////////////////////
        // in below line we are initializing our array list.
        locationArrayList = new ArrayList<>();

        // on below line we are adding our
        // locations in our array list.
        locationArrayList.add(bus_stop1);
        locationArrayList.add(bus_stop2);
        locationArrayList.add(bus_stop3);
        locationArrayList.add(bus_stop4);

        // Get carId from the Intent
        String carId = getActivity().getIntent().getStringExtra("carId");

        // Initialize Firebase
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getUid();

        if (uid != null && carId != null) {
            locationRef = FirebaseDatabase.getInstance().getReference("locations").child(uid).child(carId);
            // Retrieve car location from Firebase and update the map
            locationRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        double carLatitude = snapshot.child("latitude").getValue(Double.class);
                        double carLongitude = snapshot.child("longitude").getValue(Double.class);
                        LatLng carLocation = new LatLng(carLatitude, carLongitude);

                        // Update the map with the car's location
                        updateMapWithCarLocation(carLocation);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("MapsFragment", "Error retrieving car location from Firebase");
                }
            });
        } else {
            // Handle the case where the UID or carId is null
            Log.e("MapsFragment", "User or car not authenticated");
        }

        return view;
    }

    // method to update the map with the car's location
    private void updateMapWithCarLocation(LatLng carLocation) {
        if (googleMap != null) {
            // Add custom marker for the car's location
            addCustomMarker(carLocation, "Car Location", R.drawable.one);

            // Remove the following line if you don't want to draw a route
            // drawRoute(currentLocation, carLocation);
        }
    }

    //let's try to Modify our onMapReady method to include the route to bus stop 1
    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        //  guys try to Set default location (e.g., Durban, South Africa)
        currentLocation = new LatLng(-29.848615491775845, 30.999552338124875);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));

        // Add custom marker for the current location
        addCustomMarker(currentLocation, "Current Location", R.drawable.walk);

        // Zoom the camera to show all markers on the map
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18.0f));

        // Add custom markers for the bus stops
        for (int i = 0; i < locationArrayList.size(); i++) {
            LatLng busStop = locationArrayList.get(i);
            addCustomMarker(busStop, "Bus " + (i + 1), R.drawable.bus2);
        }

        // Draw route to bus stop 1
        drawRoute(currentLocation, bus_stop1);

        // searchView for the bus in the arraylist and draw2 routes????????
    }


    private void searchBus(String busNumber) {
        // Perform a search among the bus stops based on the bus number
        for (int i = 0; i < locationArrayList.size(); i++) {
            String busStopTitle = "Bus " + (i + 1);
            if (busStopTitle.equalsIgnoreCase(busNumber)) {
                // If the bus is found, we draw a route to the selected bus stop
                LatLng selectedBusStop = locationArrayList.get(i);
                drawRoute(currentLocation, selectedBusStop);
                break;
            }
        }
    }

    private void addCustomMarker(LatLng position, String title, int iconResId) {
        if (googleMap != null) {
            BitmapDescriptor customMarker = getBitmapDescriptor(iconResId);
            googleMap.addMarker(new MarkerOptions().position(position).title(title).icon(customMarker));
        }
    }

    private BitmapDescriptor getBitmapDescriptor(int id) {
        Drawable vectorDrawable = ContextCompat.getDrawable(requireContext(), id);

        if (vectorDrawable != null) {
            vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
            Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.draw(canvas);
            return BitmapDescriptorFactory.fromBitmap(bitmap);
        } else {
            return BitmapDescriptorFactory.defaultMarker();
        }
    }

    private void storeLocation(LatLng location) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getUid();

        if (uid != null) {
            DatabaseReference locationRef = FirebaseDatabase.getInstance().getReference("locations").child(uid);

            // we Create a LocationData object with the current latitude and longitude
            LocationData locationData = new LocationData(location.latitude, location.longitude);

            //We Push the LocationData object to Firebase
            locationRef.push().setValue(locationData);
        } else {
            // we Handle the case where the UID is null (e.g., user not authenticated)
            Log.e("MapsFragment", "User not authenticated");
        }
    }

    private void drawRoute(LatLng origin, LatLng destination) {
        try {
            new DirectionsTask().execute(origin, destination);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error drawing route.", Toast.LENGTH_SHORT).show();
            Log.e("MapsFragment", "Error drawing route", e);
        }
    }

    private class DirectionsTask extends AsyncTask<LatLng, Void, DirectionsResult> {

        @Override
        protected DirectionsResult doInBackground(LatLng... params) {
            try {
                GeoApiContext context = new GeoApiContext.Builder()
                        .apiKey("AIzaSyBgLPACq9USQ3QV9-6lMdsbzTeZ6smyqyg")
                        .build();

                return DirectionsApi.newRequest(context)
                        .origin(new com.google.maps.model.LatLng(params[0].latitude, params[0].longitude))
                        .destination(new com.google.maps.model.LatLng(params[1].latitude, params[1].longitude))
                        .mode(TravelMode.DRIVING)
                        .await();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(DirectionsResult result) {
            if (result != null && result.routes != null && result.routes.length > 0) {
                try {
                    new DrawRouteTask().execute(result.routes[0]);
                    displayETA(result.routes[0].legs[0].duration.humanReadable);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error drawing route.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Failed to get directions.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class DrawRouteTask extends AsyncTask<com.google.maps.model.DirectionsRoute, Void, PolylineOptions> {

        @Override
        protected PolylineOptions doInBackground(com.google.maps.model.DirectionsRoute... params) {
            com.google.maps.model.DirectionsRoute route = params[0];

            PolylineOptions polylineOptions = new PolylineOptions();
            for (com.google.maps.model.LatLng point : route.overviewPolyline.decodePath()) {
                polylineOptions.add(new LatLng(point.lat, point.lng));
            }

            return polylineOptions;
        }

        @Override
        protected void onPostExecute(PolylineOptions polylineOptions) {
            if (googleMap != null && polylineOptions != null) {
                // Run UI operations on the main thread, we must check guys
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        googleMap.addPolyline(polylineOptions);
                    }
                });
            }
        }
    }

    private void displayETA(String eta) {
        // Update UI with ETA information
        TextView textViewETA = getView().findViewById(R.id.idSearchView);
        textViewETA.setText("ETA: " + eta);
    }
}


