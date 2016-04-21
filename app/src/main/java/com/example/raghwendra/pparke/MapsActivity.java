package com.example.raghwendra.pparke;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.raghwendra.pparke.R.drawable;
import static com.example.raghwendra.pparke.R.id;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mainactivity_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle("Pparke");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng fortis = new LatLng(12.895318,77.598119);
        LatLng mainRoad = new LatLng(12.900569,77.601059 );
        LatLng chinmayaMansion = new LatLng(12.903639,77.598105);
        LatLng junction = new LatLng(12.907155,77.600163);
        LatLng university = new LatLng(12.906722,77.608003);
        LatLng add1 = new LatLng(12.905634,77.603579);
        LatLng add2 = new LatLng(12.906134,77.600436);
        LatLng iim = new LatLng(12.894798,77.602695);
        LatLng menna = new LatLng(12.891425,77.595025);


        mMap.addMarker(new MarkerOptions().position(fortis).title("Marker near Fortis").icon(BitmapDescriptorFactory.fromResource(drawable.hospital)));
        mMap.addMarker(new MarkerOptions().position(mainRoad).title("Marker near baneerghata Main road").icon(BitmapDescriptorFactory.fromResource(drawable.yellow_marker)));
        mMap.addMarker(new MarkerOptions().position(junction).title("Marker in Junction").icon(BitmapDescriptorFactory.fromResource(drawable.junctionpoint)));
        mMap.addMarker(new MarkerOptions().position(university).title("Marker in Building").icon(BitmapDescriptorFactory.fromResource(drawable.layer_24)));
        mMap.addMarker(new MarkerOptions().position(chinmayaMansion).title("Marker in Layout").icon(BitmapDescriptorFactory.fromResource(drawable.layers_99)));
        mMap.addMarker(new MarkerOptions().position(add1).title("Marker in Hospital").icon(BitmapDescriptorFactory.fromResource(drawable.hospital)));
        mMap.addMarker(new MarkerOptions().position(iim).title("Marker in IIM-B").icon(BitmapDescriptorFactory.fromResource(drawable.iim)));
        mMap.addMarker(new MarkerOptions().position(add2).title("Marker in Layout").icon(BitmapDescriptorFactory.fromResource(drawable.hospital)));
        mMap.addMarker(new MarkerOptions().position(menna).title("Marker in Collage").icon(BitmapDescriptorFactory.fromResource(drawable.opticalworld)));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mainRoad)      // Sets the center of the map to Mountain View
                .zoom(16)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(90)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mMap.clear();
                System.out.println("Result Succesful!!");
                Place fromPlace = PlaceAutocomplete.getPlace(this, data);
                LatLng var = fromPlace.getLatLng();
                String place = fromPlace.getName().toString();
                mMap.addMarker(new MarkerOptions().position(var).title("Marker in " +place).icon(BitmapDescriptorFactory.fromResource(drawable.yellow_marker)));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(var)      // Sets the center of the map to Mountain View
                        .zoom(15)                   // Sets the zoom
                        .bearing(0)                // Sets the orientation of the camera to east
                        .tilt(90)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                System.out.println(status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
