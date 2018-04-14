package b12app.vyom.com.bmwproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private TextView tvName, tvAddress, tvLat, tvLng, tvArrivaltime,titleMap;
    private GoogleMap mMap;
    private LocationInfo locationInfoMap;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        tvName = findViewById(R.id.tvTitle);
        tvAddress = findViewById(R.id.tvAddressMap);
        tvArrivaltime = findViewById(R.id.tvAT);
        toolbar = findViewById(R.id.toolBar);
        titleMap = findViewById(R.id.titleMap);
        tvLat = findViewById(R.id.tvLat);
        tvLng = findViewById(R.id.tvLng);

        Intent intent = getIntent();
        locationInfoMap = (LocationInfo) intent.getExtras().getSerializable("location_detail");
       mapView.getMapAsync(this);
        tvName.setText(locationInfoMap.getName());
        tvAddress.setText(locationInfoMap.getAddress());
        tvLat.setText(String.valueOf(locationInfoMap.getLatitude()));
        tvLng.setText(String.valueOf(locationInfoMap.getLongitude()));
        tvArrivaltime.setText(locationInfoMap.getArrivalTime());
        titleMap.setText(locationInfoMap.getName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng currentLocation = new LatLng(locationInfoMap.getLatitude(), locationInfoMap.getLongitude());

        mMap.addMarker(new MarkerOptions().position(currentLocation).title(locationInfoMap.getName()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,14));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    }
}
