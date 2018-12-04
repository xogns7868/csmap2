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
import android.view.Display;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
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

import java.util.ArrayList;

public class computer extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    GoogleMap mMap;
    ExpandableListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Display newDisplay = getWindowManager().getDefaultDisplay();
        int width = newDisplay.getWidth();
        ArrayList<myGroup> DataList = new ArrayList<myGroup>();
        listView = (ExpandableListView)findViewById(R.id.com_list);
        myGroup temp = new myGroup("북악관");
        temp.child.add("509호, 511호");
        temp.child.add("이용시간 : 09:00 ~ 22:00");
        temp.child.add("1층 컬러 프린터 사용 가능");
        DataList.add(temp);
        temp = new myGroup("과학관");
        temp.child.add("321호");
        temp.child.add("이용시간 : 09:00 ~ 22:00");
        DataList.add(temp);
        temp = new myGroup("복지관");
        temp.child.add("복지관 1층 : 컬러 프린터 사용 가능");
        DataList.add(temp);
        temp = new myGroup("7호관");
        temp.child.add("449호");
        temp.child.add("이용시간 : 09:00 ~ 22:00");
        DataList.add(temp);
        temp = new myGroup("경상관");
        temp.child.add("510호");
        temp.child.add("이용시간 : 09:00 ~ 22:00");
        DataList.add(temp);
        temp = new myGroup("법학관");
        temp.child.add("102호");
        temp.child.add("이용시간 : 09:00 ~ 22:00");
        DataList.add(temp);
        temp = new myGroup("공학관");
        temp.child.add("B106호");
        temp.child.add("이용시간 : 09:00 ~ 22:00");
        DataList.add(temp);
        temp = new myGroup("예술관");
        temp.child.add("226호");
        temp.child.add("이용시간 : 09:00 ~ 22:00");
        DataList.add(temp);
        temp = new myGroup("조형관");
        temp.child.add("449호");
        temp.child.add("강의시간 외 개방");
        ExpandAdapter adapter = new ExpandAdapter(getApplicationContext(),R.layout.group_row,R.layout.child_row,DataList);
        listView.setIndicatorBounds(width-50, width); //이 코드를 지우면 화살표 위치가 바뀐다.
        listView.setAdapter(adapter);
    }

    @Override
    public void onMapReady ( final GoogleMap map){
        mMap = map;
        LatLng LocateNow = new LatLng(37.611244,126.996924);
        LatLng bookak = new LatLng(37.612238,126.996820);
        LatLng science = new LatLng(37.611760, 126.999217);
        LatLng seven = new LatLng(37.610008,126.997071);
        LatLng engineering = new LatLng(37.611750, 126.993917);
        LatLng art = new LatLng(37.610115, 126.997853);
        LatLng bokji = new LatLng(37.610453, 126.995992);
        LatLng law = new LatLng(37.611272, 126.998209);
        LatLng economic = new LatLng(37.610806, 126.997612);
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
        markerOptions
                .position(art)
                .title("예술관");
        mMap.addMarker(markerOptions);
        markerOptions
                .position(bokji)
                .title("복지관");
        mMap.addMarker(markerOptions);
        markerOptions
                .position(law)
                .title("법학관");
        mMap.addMarker(markerOptions);
        markerOptions
                .position(economic)
                .title("경상관");
        mMap.addMarker(markerOptions);

        mMap.setOnMarkerClickListener(this);
        map.moveCamera(CameraUpdateFactory.newLatLng(LocateNow));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(LocateNow, 17), 1, null);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return true;
    }
}
