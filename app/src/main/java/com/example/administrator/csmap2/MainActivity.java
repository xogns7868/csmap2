package com.example.administrator.csmap2;

//ghoxa
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
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
import android.widget.Button;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{
    Button text;
    Button text2;
    Button text3;
    XmlPullParser xpp;
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
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        text = (Button) findViewById(R.id.text);
        text2 = (Button) findViewById(R.id.text2);
        text3 = (Button) findViewById(R.id.text3);
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
            gps = new GTSTracker(MainActivity.this,mHandler);
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
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                intent.putExtra("text", data2[0]);
                intent.putExtra("text2", data2[1]);
                intent.putExtra("text3", data2[2]);
                startActivity(intent);
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SubActivity2.class);
                intent.putExtra("text2", data2[1]);
                startActivity(intent);
            }
        });
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SubActivity3.class);
                intent.putExtra("text3", data2[2]);
                startActivity(intent);
            }
        });
    }
    public void makeNewGpsService(){
        if(gps == null) {
            gps = new GTSTracker(MainActivity.this,mHandler);
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
                                    text.setText(data[0].toString());
                                }
                                if (data[1] != null) {
                                    text2.setText(data[1].toString());
                                    double x = Double.parseDouble(lat[1]);
                                    double y = Double.parseDouble(lon[1]);
                                    LatLng SEOUL = new LatLng(y, x);
                                    MarkerOptions markerOptions = new MarkerOptions();
                                    markerOptions.position(SEOUL);
                                    markerOptions.title("두번째 정류장");
                                    markerOptions.snippet(locate[1]);
                                    mMap.addMarker(markerOptions);
                                }
                                if (data[2] != null) {
                                    text3.setText(data[2].toString());
                                    double x = Double.parseDouble(lat[2]);
                                    double y = Double.parseDouble(lon[2]) ;
                                    LatLng SEOUL = new LatLng(y,x);
                                    MarkerOptions markerOptions = new MarkerOptions();
                                    markerOptions.position(SEOUL);
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
        String gpsX = String.valueOf(latitude);
        String gpsY = String.valueOf(longitude);
        String queryUrl="http://ws.bus.go.kr/api/rest/stationinfo/getStationByPos?"
                + "ServiceKey=vyLHMUmRFVa0oDbYG5LY8zGFg5rvRjZ1SKT7QPmd3PKVHfdwiEbO920ydibB%2F4C3LukkRYDZB069jAyGSEqoLw%3D%3D"
                + "&tmX="+gpsY + "&tmY="+gpsX + "&radius=500";
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
                            buffer[count].append("주소 : ");
                            xpp.next();
                            locate[count] = xpp.getText();
                            buffer[count].append(xpp.getText());//statinNm 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer[count].append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("gpsX")){
                            buffer[count].append("위도 : ");
                            xpp.next();
                            lat[count] = xpp.getText();
                            buffer[count].append(xpp.getText());//gpsX 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer[count].append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("gpsY")){
                            buffer[count].append("경도 :");
                            xpp.next();
                            lon[count] = xpp.getText();
                            buffer[count].append(xpp.getText());//gpsY 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer[count].append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("arsId")) {
                            xpp.next();
                            data2[count]=xpp.getText();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기
                        if(tag.equals("itemList")) {
                            buffer[count].append("\n");
                        }// 첫번째 검색결과종료..줄바꿈
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
        LatLng LocateNow = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(LocateNow);
        markerOptions.title("현재 내 위치");
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(LocateNow));
        map.animateCamera(CameraUpdateFactory.zoomTo(17));
    }
}