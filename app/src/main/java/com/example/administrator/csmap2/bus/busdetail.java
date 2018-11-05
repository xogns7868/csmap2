package com.example.administrator.csmap2.bus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.csmap2.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class busdetail extends AppCompatActivity {
    StringBuffer[] data;
    private ListView mListView;
    String str;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItems=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busdetail);
        Intent intent = new Intent(this.getIntent());
        str=intent.getStringExtra("bus");
        Log.e("test1",str);
        if(mListView ==null){
            mListView = (ListView) findViewById(R.id.busdetail);
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
                    }
                });
            }
        }).start();
    }
    public StringBuffer[] getXmlData(){
        StringBuffer[] buffer=new StringBuffer[100];
        String BusRouteId = str;
        String queryUrl="http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute?serviceKey="
                + "gNr%2BHcBWbm2LTezFMq4SQ%2F5Kq0rMvmmkZCFocT5UvkzoVoxwOChmDLlGEhnjO1V0hCuHkFCij4FlvRe5DpIcVQ%3D%3D"
                + "&busRouteId="+BusRouteId;

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
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기
                        if(tag.equals("itemList")){
                            buffer[++count] = new StringBuffer();
                        }
                        else if(tag.equals("beginTm")){
                            xpp.next();
                            buffer[count].append("첫 차 : "+ xpp.getText() + "\n");
                        }
                        else if(tag.equals("lastTm")){
                            xpp.next();
                            buffer[count].append("막 차 : "+ xpp.getText() + "\n");
                        }
                        else if(tag.equals("stationNm")){
                            xpp.next();
                            buffer[count].insert(0,xpp.getText()+"\n");
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
