package com.example.administrator.csmap2.bus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.administrator.csmap2.R;
import com.example.administrator.csmap2.bus.busdetail;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class SubActivity2 extends AppCompatActivity {
    StringBuffer[] data;
    String[] data2;
    String[] busRouteId;
    private ListView mListView;
    String str;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItems=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub2);
        Intent intent = new Intent(this.getIntent());
        str=intent.getStringExtra("text2");
        if(mListView ==null){
            mListView = (ListView) findViewById(R.id.textview2);
        }

        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listItems);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                data= getXmlData();//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기
                //UI Thread(Main Thread)를 제외한 어떤 Thread도 화면을 변경할 수 없기때문에
                //runOnUiThread()를 이용하여 UI Thread가 TextView 글씨 변경하도록 함
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        for(int i = 0; i<100; i++){
                            if(data[i]==null){
                                break;
                            }
                            adapter.add(data[i].toString());
                        }
                        mListView.setAdapter(adapter);
                        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                Intent intent = new Intent(
                                        getApplicationContext(),busdetail.class);

                                intent.putExtra("bus", busRouteId[position]);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        }).start();

    }
    protected ListView getListView() {
        if (mListView == null) {
            mListView = (ListView) findViewById(R.id.textview2);
        }
        return mListView;
    }
    protected void setListAdapter(ListAdapter adapter) {
        getListView().setAdapter(adapter);
    }

    protected ListAdapter getListAdapter() {
        ListAdapter adapter = getListView().getAdapter();
        if (adapter instanceof HeaderViewListAdapter) {
            return ((HeaderViewListAdapter)adapter).getWrappedAdapter();
        } else {
            return adapter;
        }
    }
    public StringBuffer[] getXmlData(){
        StringBuffer[] buffer=new StringBuffer[100];
        busRouteId = new String[100];
        String arsId = str;
        String queryUrl="http://ws.bus.go.kr/api/rest/stationinfo/getStationByUid?"
                + "ServiceKey=vyLHMUmRFVa0oDbYG5LY8zGFg5rvRjZ1SKT7QPmd3PKVHfdwiEbO920ydibB%2F4C3LukkRYDZB069jAyGSEqoLw%3D%3D"
                + "&arsId="+arsId;

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
            while( eventType != XmlPullParser.END_DOCUMENT){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        //buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기
                        if(tag.equals("itemList")){
                            buffer[++count] = new StringBuffer();
                            busRouteId[count] = new String();
                        }
                        else if(tag.equals("rtNm")){
                            xpp.next();
                            buffer[count].insert(0,"버스 번호 : "+ xpp.getText() + "\n");
                        }
                        else if(tag.equals("busRouteId")){
                            xpp.next();
                            busRouteId[count] = xpp.getText();
                        }
                        else if(tag.equals("arrmsg1")){
                            buffer[count].append("첫번째 버스 : ");
                            xpp.next();
                            buffer[count].append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer[count].append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("arrmsg2")){
                            buffer[count].append("두번째 버스 : ");
                            xpp.next();
                            buffer[count].append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer[count].append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("lastTm")){
                            buffer[count].append("막차 시간 : ");
                            xpp.next();
                            buffer[count].append(xpp.getText().substring(0,2));//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer[count].append(" : ");//줄바꿈 문자 추가
                            buffer[count].append(xpp.getText().substring(2,4));//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer[count].append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("isLast1")){
                            if(xpp.getName() == "1") {
                                buffer[count].append("현재 버스는 막차입니다.");
                                xpp.next();
                                buffer[count].append("\n");//줄바꿈 문자 추가
                            }
                        }

                        else if(tag.equals("isLast2")){
                            if(xpp.getName() == "1") {
                                buffer[count].append("다음 버스는 막차입니다.");
                                xpp.next();
                                buffer[count].append("\n");//줄바꿈 문자 추가
                            }
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기
                        if(tag.equals("itemList")) {
                        }// 첫번째 검색결과종료..줄바꿈
                        break;
                }
                eventType= xpp.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
        //Log.e("test",buffer[1].toString());
        return buffer;//StringBuffer 문자열 객체 반환

    }//getXmlData method....
}