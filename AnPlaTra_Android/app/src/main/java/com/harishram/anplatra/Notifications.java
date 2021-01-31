package com.harishram.anplatra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Notifications extends AppCompatActivity {

    Thread notification_request;
    URL notification_url;
    Bundle user_detail;
    String address,notifications;
    String result_notify;
    TextView notification_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        getSupportActionBar().hide();
        user_detail = getIntent().getBundleExtra("user_detail");
        notification_text = (TextView) findViewById(R.id.textView14);
        notification_request = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    address = user_detail.getString("address");
                    notification_url = new URL("http://"+address+"/anplatra_server/notifications.php");
                    HttpURLConnection notification_conn = (HttpURLConnection) notification_url.openConnection();
                    notification_conn.setDoOutput(true);
                    notification_conn.setRequestMethod("GET");
                    BufferedReader br = new BufferedReader(new InputStreamReader(notification_conn.getInputStream()));
                    notifications = br.readLine();
                    result_notify = "";
                    String[] notification_list = notifications.split(";");
                    for(int i=0;i<notification_list.length;i++){
                        result_notify += notification_list[i]+"\n";
                        result_notify += "\n";
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        notification_request.start();
        try{
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notification_text.setText(result_notify);

    }

    @Override
    public void onBackPressed(){
        Intent menu_intent = new Intent(getApplicationContext(),Menu.class);
        menu_intent.putExtra("user_detail",user_detail);
        startActivity(menu_intent);
    }
}
