package com.example.administrator.csmap2.bus;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.administrator.csmap2.R;
import com.example.administrator.csmap2.fragment.ListViewAdapter;
import com.example.administrator.csmap2.fragment.ListViewItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements OnMapReadyCallback {
        ListView listView;
        ListViewAdapter adapter;
        StringBuffer[] data;
        String[] data2;
        GTSTracker gps = null;
        public Handler mHandler;
        String[] lat;
        String[] lon;
        String[] locate;
        public static int RENEW_GPS = 1;
        public static int SEND_PRINT = 2;
        double latitude = 0;
        double longitude = 0;
        GoogleMap mMap;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            listView = (ListView) findViewById(R.id.listview1);
            if ( Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions( this, new String[] {  Manifest.permission.ACCESS_FINE_LOCATION  },
                        0 );
            }
            mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg){
                    if(msg.what==RENEW_GPS){
                        makeNewGpsService();
                    }
                    if(msg.what==SEND_PRINT){
                    }
                }
            };

            // show location button click event
            // create class object
            if(gps == null) {
                gps = new GTSTracker(Main2Activity.this,mHandler);
            }else{
                gps.Update();
            }

            // check if GPS enabled
            if(gps.canGetLocation()){
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                // \n is for new line
            }else{
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }

        }
        public void makeNewGpsService(){
            if(gps == null) {
                gps = new GTSTracker(Main2Activity.this,mHandler);
            }else{
                gps.Update();
            }
        }
        public void mOnClick(View v){
            switch( v.getId() ) {
                case R.id.button:
                    //Android 4.0 이상 부터는 네트워크를 이용할 때 반드시 Thread 사용해야 함
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            data = getXmlData();//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기
                            //UI Thread(Main Thread)를 제외한 어떤 Thread도 화면을 변경할 수 없기때문에
                            //runOnUiThread()를 이용하여 UI Thread가 TextView 글씨 변경하도록 함
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter = new ListViewAdapter();
                                    listView.setAdapter(adapter);
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Intent intent = new Intent(getApplicationContext(),SubActivity2.class);

                                            intent.putExtra("arsId",data2[position]);
                                            startActivity(intent);
                                        }
                                    });
                                    // TODO Auto-generated method stub
                                    if (data[0] != null) {
                                        double x = Double.parseDouble(lat[0]);
                                        double y = Double.parseDouble(lon[0]);
                                        LatLng First = new LatLng(y, x);
                                        MarkerOptions markerOptions = new MarkerOptions();
                                        markerOptions.position(First);
                                        markerOptions.title("첫번째 정류장");
                                        markerOptions.snippet(locate[0]);
                                        mMap.addMarker(markerOptions);
                                        int length = data[0].length();
                                        String replace = data[0].toString().replace("\n","   ");
                                        adapter.addItem(data[0].toString().substring(0,6),data[0].toString().substring(6,length));
                                    }
                                    if (data[1] != null) {
                                        int length = data[1].length();
                                        adapter.addItem(data[1].toString().substring(0,6),data[1].toString().substring(6,length));
                                        double x = Double.parseDouble(lat[1]);
                                        double y = Double.parseDouble(lon[1]);
                                        LatLng Second = new LatLng(y, x);
                                        MarkerOptions markerOptions = new MarkerOptions();
                                        markerOptions.position(Second);
                                        markerOptions.title("두번째 정류장");
                                        markerOptions.snippet(locate[1]);
                                        mMap.addMarker(markerOptions);
                                    }
                                    if (data[2] != null) {
                                        int length = data[2].length();
                                        adapter.addItem(data[2].toString().substring(0,6),data[2].toString().substring(6,length));
                                        double x = Double.parseDouble(lat[2]);
                                        double y = Double.parseDouble(lon[2]) ;
                                        LatLng Third = new LatLng(y,x);
                                        MarkerOptions markerOptions = new MarkerOptions();
                                        markerOptions.position(Third);
                                        markerOptions.title("세번째 정류장");
                                        markerOptions.snippet(locate[2]);
                                        mMap.addMarker(markerOptions);
                                    }
                                }

                            });
                        }
                    }).start();
            }
        }
        //XmlPullParser를 이용하여 Naver 에서 제공하는 OpenAPI XML 파일 파싱하기(parsing)
        StringBuffer[] getXmlData(){
            StringBuffer[] buffer=new StringBuffer[3];
            data2 = new String[3];
            lat = new String[3];
            lon = new String[3];
            locate = new String[3];
            String gpsX = "37.609692";
            String gpsY = "126.997708";
            Log.e("test", gpsY);
            String queryUrl="http://ws.bus.go.kr/api/rest/stationinfo/getStationByPos?"
                    + "ServiceKey=vyLHMUmRFVa0oDbYG5LY8zGFg5rvRjZ1SKT7QPmd3PKVHfdwiEbO920ydibB%2F4C3LukkRYDZB069jAyGSEqoLw%3D%3D"
                    + "&tmX="+gpsY + "&tmY=" + gpsX + "&radius=500";
            try {
                URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
                InputStream is= url.openStream(); //url위치로 입력스트림 연결
                XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
                XmlPullParser xpp= factory.newPullParser();
                xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기
                String tag;
                xpp.next();
                int eventType= xpp.getEventType();
                int count = -1;
                while( eventType != XmlPullParser.END_DOCUMENT && count != 3 ){
                    switch( eventType ){
                        case XmlPullParser.START_DOCUMENT:
                            //buffer.append("파싱 시작...\n\n");
                            break;

                        case XmlPullParser.START_TAG:
                            tag= xpp.getName();//테그 이름 얻어오기
                            if(tag.equals("itemList")){
                                buffer[++count] = new StringBuffer();// 첫번째 검색결과
                                data2[count] = new String();
                                lat[count] = new String();
                                lon[count] = new String();
                                locate[count] = new String();
                            }
                            else if(tag.equals("stationNm")){
                                xpp.next();
                                locate[count] = xpp.getText();
                                buffer[count].append(xpp.getText());
                            }
                            else if(tag.equals("gpsX")){
                                xpp.next();
                                lat[count] = xpp.getText();
                                }
                            else if(tag.equals("gpsY")){
                                xpp.next();
                                lon[count] = xpp.getText();
                                }
                            else if(tag.equals("arsId")) {
                                xpp.next();
                                data2[count]=xpp.getText();
                                buffer[count].append(xpp.getText().substring(0,2));
                                buffer[count].append("-");
                                buffer[count].append(xpp.getText().substring(2,5));
                            }
                            break;

                        case XmlPullParser.TEXT:
                            break;

                        case XmlPullParser.END_TAG:
                            tag= xpp.getName(); //테그 이름 얻어오기
                            if(tag.equals("itemList")) {
                            }
                            break;
                    }
                    eventType= xpp.next();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch blocke.printStackTrace();
            }
            return buffer;//StringBuffer 문자열 객체 반환

        }//getXmlData method....
        @Override
        public void onMapReady ( final GoogleMap map){
            mMap = map;
            LatLng LocateNow = new LatLng(37.609692,126.997708);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(LocateNow);
            markerOptions.title("현재 내 위치");
            map.addMarker(markerOptions);
            map.moveCamera(CameraUpdateFactory.newLatLng(LocateNow));
            map.animateCamera(CameraUpdateFactory.zoomTo(13));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(LocateNow, 15.5f), 1, null);
        }

    }
