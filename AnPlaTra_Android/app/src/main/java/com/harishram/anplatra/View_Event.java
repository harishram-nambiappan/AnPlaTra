package com.harishram.anplatra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class View_Event extends AppCompatActivity {

    Thread event_request;
    URL view_url;
    Bundle user_detail;
    String address,events_result,events;
    LinearLayout event_list;
    TextView event_text;
    Toast load_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        getSupportActionBar().hide();
        user_detail = getIntent().getBundleExtra("user_detail");
        address = user_detail.getString("address");
        //event_list = (LinearLayout) findViewById(R.id.event_list);
        event_text = (TextView) findViewById(R.id.textView13);
        event_request = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    view_url = new URL("http://"+address+"/anplatra_server/viewevent.php");
                    HttpURLConnection view_url_conn = (HttpURLConnection) view_url.openConnection();
                    view_url_conn.setDoOutput(true);
                    view_url_conn.setRequestMethod("GET");
                    BufferedReader view_br = new BufferedReader(new InputStreamReader(view_url_conn.getInputStream()));
                    events = view_br.readLine();
                    System.out.println(events);
                    String[] events_list = events.split(";");
                    events_result = "";
                    for(int i=0;i<events_list.length;i++){
                        events_result += events_list[i]+"\n";
                        events_result += "\n";
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        event_request.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        event_text.setText(events_result);
    }

    @Override
    public void onBackPressed(){
        Intent menu_intent = new Intent(getApplicationContext(),Menu.class);
        menu_intent.putExtra("user_detail",user_detail);
        startActivity(menu_intent);
    }
}
