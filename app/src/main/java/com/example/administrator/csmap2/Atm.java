package com.example.administrator.csmap2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.csmap2.bus.GTSTracker;
import com.example.administrator.csmap2.fragment.ListViewAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Atm extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    GoogleMap mMap;
    ListView listview;
    ListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        listview = (ListView) findViewById(R.id.atm_detail);
    }

    @Override
    public void onMapReady ( final GoogleMap map){
        mMap = map;
        LatLng LocateNow = new LatLng(37.611244,126.996924);
        LatLng bookak = new LatLng(37.612238,126.996820);
        LatLng science = new LatLng(37.611760, 126.999217);
        LatLng seven = new LatLng(37.610008,126.997071);
        LatLng engineering = new LatLng(37.611750, 126.993917);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(bookak)
                .title("북악관");
        mMap.addMarker(markerOptions);
        markerOptions
                .position(science)
                .title("과학관");
        mMap.addMarker(markerOptions);
        markerOptions
                .position(seven)
                .title("7호관");
        mMap.addMarker(markerOptions);
        markerOptions
                .position(engineering)
                .title("공학관");
        mMap.addMarker(markerOptions);

        mMap.setOnMarkerClickListener(this);
        map.moveCamera(CameraUpdateFactory.newLatLng(LocateNow));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(LocateNow, 17), 1, null);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        adapter = new ListViewAdapter();
        listview.setAdapter(adapter);
        if(String.valueOf(marker.getTitle()).equals("과학관")) {
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.bank),"우리 은행");
        }
        else if(String.valueOf(marker.getTitle()).equals("북악관")) {
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.clock_icon),"매일 11:30 - 20:30연중무휴");
        }
        else if(String.valueOf(marker.getTitle()).equals("7호관")) {
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.clock_icon),"매일 11:30 - 20:30연중무휴");
        }
        else if(String.valueOf(marker.getTitle()).equals("공학관")) {
            adapter.addItem(ContextCompat.getDrawable(this,R.drawable.clock_icon),"매일 11:30 - 20:30연중무휴");
        }
        return true;
    }
}
