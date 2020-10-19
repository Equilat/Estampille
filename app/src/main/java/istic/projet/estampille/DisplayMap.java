package istic.projet.estampille;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DisplayMap extends AppCompatActivity implements OnMapReadyCallback{

    double lon = 0;
    double lat = 0;
    String[] tab = new String[0];
    String address = "";
    String ciret = "";
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_map);

        Bundle mapBundle = getIntent().getExtras();
        if(mapBundle != null){
            tab = mapBundle.getStringArray("Infos");
            ciret = tab[2];
            name = tab[3];
            address = tab[4] + ", " + tab[5] + " " + tab[6];
            LatLng latLng = getCoords(address);
            lon = latLng.longitude;
            lat = latLng.latitude;
        }

        TextView view8 = (TextView) findViewById(R.id.textView8);
        TextView view9 = (TextView) findViewById(R.id.textView9);
        TextView view10 = (TextView) findViewById(R.id.textView10);

        view8.setText(name);
        view9.setText(address);
        view10.setText(ciret);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng coords = new LatLng(lat, lon);
        googleMap.addMarker(new MarkerOptions()
                .position(coords)
                .title(name));
        CameraPosition camPos = new CameraPosition.Builder().target(coords).zoom(8).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));
    }

    private LatLng getCoords(String address){
        Geocoder geocoder = new Geocoder(this.getApplicationContext());
        List<Address> addresses = new ArrayList<Address>();
        try {
            addresses = geocoder.getFromLocationName(address, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(addresses.size() > 0) {
            double latitude= addresses.get(0).getLatitude();
            double longitude= addresses.get(0).getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            return latLng;
        }
        else{
            LatLng latLng = new LatLng(0, 0);
            return latLng;
        }
    }
}