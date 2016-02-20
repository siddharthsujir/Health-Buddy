package project.sreesh.healthbuddy;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity{

    private GoogleMap mMap;
    Location location;// Might be null if Google Play services APK is not available.
    double startLongitude;
    double startLatitude;
    double stopLongitude;
    double stopLatitude;
    float distance;
    double calories;
    EditText dispdist;
    EditText dispcal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        setUpMapIfNeeded();
        dispdist = (EditText)findViewById(R.id.editText5);
        dispcal = (EditText)findViewById(R.id.editText6);
    }

//    public void onClick(View v) {
//
//        switch (v.getId()) {
//            case R.id.button14:
//                startLongitude = location.getLongitude();
//                startLatitude = location.getLatitude();
//                break;
//            case R.id.button15:
//                stopLongitude = location.getLongitude();
//                stopLatitude = location.getLatitude();
//                distance = getDistance(startLongitude, startLatitude, stopLongitude, stopLatitude);
//                dispdist = (EditText)findViewById(R.id.editText5);
//                dispdist.setText(distance+"");
//                System.out.println("="+distance);
//                break;
//
//        }
//    }

    public void onStart(View v)
    {
        startLongitude = location.getLongitude();
        startLatitude = location.getLatitude();

    }

    public void onStop(View v) {
        try {
            stopLongitude = location.getLongitude();
            stopLatitude = location.getLatitude();
            distance = getDistance(startLongitude, startLatitude, stopLongitude, stopLatitude);
            dispdist.setText(distance + "");

            System.out.println("=" + startLongitude);
            System.out.println("=" + startLatitude);
            System.out.println("=" + stopLongitude);
            System.out.println("=" + stopLatitude);

            System.out.println("=" + distance);
            calories = getCalories(distance);
            dispcal.setText(calories + "");
        } catch (Exception e)
        {
            Toast.makeText(getBaseContext(), "Switch on GPS", Toast.LENGTH_SHORT).show();
        }

    }

    public double getCalories(float distance)
    {
        double calories;
        calories=distance*0.000621371*105;
        return calories;
    }

    public static float getDistance(double startLati, double startLongi, double goalLati, double goalLongi){
        float[] resultArray = new float[99];
        Location.distanceBetween(startLati, startLongi, goalLati, goalLongi, resultArray);
        return resultArray[0];
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    public void onSearch(View view)
    {
        try {
            EditText location = (EditText) findViewById(R.id.TFaddress);
            String loc = location.getText().toString();
            List<Address> addressList = null;

            if (loc != null || !loc.equals("")) {
                Geocoder geocoder = new Geocoder(this);
                try {
                    addressList = geocoder.getFromLocationName(loc, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }


    catch(Exception e)
    {
        Toast.makeText(getBaseContext(), "Please enter a location", Toast.LENGTH_SHORT).show();
    }
    }

    public void changeType(View view)
    {
        if(mMap.getMapType()==GoogleMap.MAP_TYPE_NORMAL)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        else
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }


    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.setMyLocationEnabled(true);
    }
}
