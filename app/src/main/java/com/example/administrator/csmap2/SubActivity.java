package com.example.administrator.csmap2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class SubActivity extends AppCompatActivity {
    String data;
    TextView textView;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent intent = new Intent(this.getIntent());
        str = intent.getStringExtra("text");
        textView = (TextView) findViewById(R.id.textview);
        //Android 4.0 이상 부터는 네트워크를 이용할 때 반드시 Thread 사용해야 함
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

                        textView.setText(data);
                    }
                });
            }
        }).start();

    }


    String getXmlData(){
        StringBuffer buffer=new StringBuffer();
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
            while( eventType != XmlPullParser.END_DOCUMENT){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        //buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기
                        if(tag.equals("itemList"));
                        else if(tag.equals("arrmsg1")){
                            buffer.append("첫번째 버스 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("arrmsg2")){
                            buffer.append("두번째 버스 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("rtNm")){
                            xpp.next();
                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append(" 번 버스 ");
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("lastTm")){
                            buffer.append("막차 시간 : ");
                            xpp.next();
                            buffer.append(xpp.getText().substring(0,2));//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append(" : ");//줄바꿈 문자 추가
                            buffer.append(xpp.getText().substring(2,4));//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("isLast1")){
                            if(xpp.getName() == "1") {
                                buffer.append("현재 버스는 막차입니다.");
                            }
                            buffer.append("현재 버스는 막차가 아닙니다.");
                            xpp.next();
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("isLast2")){
                            if(xpp.getName() == "1") {
                                buffer.append("다음 버스는 막차입니다.");
                            }
                            buffer.append("다음 버스는 막차가 아닙니다.");
                            xpp.next();
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("itemList")) {
                            buffer.append("\n");
                        }// 첫번째 검색결과종료..줄바꿈
                        break;
                }
                eventType= xpp.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
        //Log.e("test",buffer[1].toString());
        return buffer.toString();//StringBuffer 문자열 객체 반환

    }//getXmlData method....
}