package ga.asfanulla.shadier;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import android.provider.Settings;
import android.net.Uri;

import ga.asfanulla.shadier.SubClass.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    private GPSTracker gp;

    private Double Latitude = 0.00;
    private Double Longitude = 0.00;

    private MarkerOptions marker;

    private double ulat;
    private double ulong;

    private FloatingActionButton action;

    LatLng ll;

    private ArrayList<HashMap<String,String>> mark = new ArrayList<>();

    ArrayList<HashMap<String, String>> location = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map;

    private Boolean ready = false;
    private ArrayList<String> a =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        action = findViewById(R.id.fab);

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mark.size() ==0){
                    Toast.makeText(MapsActivity.this, "Select Location", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<String> hh = new ArrayList<>();
                    for (int i=0; i<mark.size(); i++){
                        HashMap<String,String> ae = mark.get(i);
                        hh.add(i, ae.get("vil"));
                        a.add(i,ae.get("vnm"));
                    }
                    SaveSharedPreference.setUserPrefLoc(MapsActivity.this, hh);
                    Intent i =new Intent(MapsActivity.this, NfeedMain.class);
                    i.putExtra("vil", hh);
                    i.putExtra("vnm", a);
                    startActivity(i);
                    finish();
                }
            }
        });


    }


    private void init(){
        gp = new GPSTracker(this);
        String state = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String result = null;
        try {
            List<Address> addressList = geocoder.getFromLocation(
                    gp.getLatitude(),gp.getLongitude(), 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");//adress
                }
                sb.append(address.getLocality()).append("\n");//village

                sb.append(address.getPostalCode()).append("\n");
                sb.append(address.getCountryName());
                sb.append(address.getAdminArea()).append("\n"); //state

                state = address.getAdminArea();

                sb.append(address.getSubAdminArea()).append("\n");//district

                sb.append(address.getSubLocality()).append("\n");

                result = sb.toString();
            }
        } catch (IOException e) {
            // Log.e(TAG, "Unable connect to Geocoder", e);
        }



        if(Geocoder.isPresent()){
            try {
                Geocoder gc = new Geocoder(this);
                List<Address> addresses= gc.getFromLocationName(state, 1); // get the found Address Objects

                for(Address a : addresses){
                    if(a.hasLatitude() && a.hasLongitude()){
                        ll = new LatLng(a.getLatitude(), a.getLongitude());
                        if (!ll.toString().isEmpty()){
                            mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(ll , 6f) );
                        }
                    }
                }
            } catch (IOException e) {
                // handle the exception
            }
        }

        getOLatLng();
    }

    private void getOLatLng() {

        String url = "http://ec2-35-154-222-185.ap-south-1.compute.amazonaws.com/sagy/lalng.php?loc";

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MapsActivity.this,"server Error",Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(MapsActivity.this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {

        String id = "";
        String name = "";
        String lat = "";
        String longi = "";


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("vloc");
            for (int i = 0; i < result.length(); i++) {
                JSONObject admin = result.getJSONObject(i);
                id = admin.getString("sl_no");
                name = admin.getString("name");
                lat = admin.getString("LAT");
                longi = admin.getString("LONG");

                map = new HashMap<String, String>();
                map.put("LocationID", id);
                map.put("Latitude", lat);
                map.put("Longitude", longi);
                map.put("LocationName", name);
                location.add(map);
            }
            drawmarker();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            Toast.makeText(this, "set permission", Toast.LENGTH_SHORT).show();
            // Show rationale and request permission.
        }

        googleMap.setOnMarkerClickListener(this);

        ready = true;
        drawmarker();
        init();

        // LatLngBounds.Builder builder = new LatLngBounds.Builder();
/*
        for (int i = 0; i < location.size(); i++) {
            Latitude = Double.parseDouble(location.get(i).get("Latitude"));
            Longitude = Double.parseDouble(location.get(i).get("Longitude"));
            String name = location.get(i).get("LocationName");
            String id = location.get(i).get("LocationID");
            marker = new MarkerOptions().position(new LatLng(Latitude, Longitude)).title(name).snippet(id);
            marker.icon(bitmapDescriptorFromVector(this,R.drawable.marker));
            mMap.addMarker(marker);
            //builder.include(marker.getPosition());
        }

        builder.include(ll);

        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.20);

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        mMap.animateCamera(cu);
*/
    }


    private void drawmarker(){

        if(ready) {
            for (int i = 0; i < location.size(); i++) {
                Latitude = Double.parseDouble(location.get(i).get("Latitude"));
                Longitude = Double.parseDouble(location.get(i).get("Longitude"));
                String name = location.get(i).get("LocationName");
                String id = location.get(i).get("LocationID");
                marker = new MarkerOptions().position(new LatLng(Latitude, Longitude)).title(name).snippet(id);
                //  marker.icon(bitmapDescriptorFromVector(this, R.drawable.marker));
                mMap.addMarker(marker);
            }
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {



       final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select This Village");
        builder.setMessage(marker.getTitle());

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                HashMap<String, String> tt = new HashMap<>();
                tt.put("vil", marker.getSnippet());
                tt.put("vnm", marker.getTitle());
                mark.add(tt);
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        return true;
    }
}
