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

public class etc extends FragmentActivity {
    GoogleMap mMap;
    ExpandableListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etc);
        Display newDisplay = getWindowManager().getDefaultDisplay();
        int width = newDisplay.getWidth();
        ArrayList<myGroup> DataList = new ArrayList<myGroup>();
        listView = (ExpandableListView) findViewById(R.id.com_list);
        myGroup temp = new myGroup("헬스");
        temp.child.add("위치 : 7호관 2층");
        temp.child.add("웰니스 센터");
        DataList.add(temp);
        temp = new myGroup("지원센터");
        temp.child.add("위치 : 복지관 5층");
        temp.child.add("학생지원 센터\n병무지원 센터\n진로지원 센터\n취업지원 센터\n현장실습지원 센터");
        DataList.add(temp);
        temp = new myGroup("식당");
        temp.child.add("위치 : 북악관 1층");
        temp.child.add("서브웨이, 바르다 김선생");
        temp.child.add("위치 : 법학관");
        temp.child.add("청향(5층), 한울식당(지하1층)");
        temp.child.add("위치 : 복지관");
        temp.child.add("학생 식당, 교직원 식당, 델리버스(1층), Place N 빵집(2층)");
        temp.child.add("위치 : 공학관 1층");
        temp.child.add("맘스터치");
        temp.child.add("위치 : 기숙사");
        temp.child.add("생활관 식당");
        DataList.add(temp);
        temp = new myGroup("휴게실");
        temp.child.add("위치 : 북악관 1층");
        temp.child.add("여학생 휴게실");
        temp.child.add("위치 : 복지관 3층");
        temp.child.add("남/여학생 휴게실");
        DataList.add(temp);
        ExpandAdapter adapter = new ExpandAdapter(getApplicationContext(), R.layout.group_row, R.layout.child_row, DataList);
        listView.setIndicatorBounds(width - 50, width);
        listView.setAdapter(adapter);
    }
}
