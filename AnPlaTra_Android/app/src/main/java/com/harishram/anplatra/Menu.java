package com.harishram.anplatra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    TextView add_event, view_event, notification, logout;
    Bundle user_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();
        add_event = (TextView) findViewById(R.id.textView3);
        view_event = (TextView) findViewById(R.id.textView5);
        notification = (TextView) findViewById(R.id.textView6);
        logout = (TextView) findViewById(R.id.textView7);
        user_detail = getIntent().getBundleExtra("user_detail");
        add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_event_intent = new Intent(getApplicationContext(),Add_Event.class);
                add_event_intent.putExtra("user_detail",user_detail);
                startActivity(add_event_intent);
            }
        });
        view_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast load_msg = Toast.makeText(getApplicationContext(),"Loading....",Toast.LENGTH_SHORT);
                load_msg.show();
                Intent view_event_intent = new Intent(getApplicationContext(),View_Event.class);
                view_event_intent.putExtra("user_detail",user_detail);
                startActivity(view_event_intent);
            }
        });
        notification.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent notification_intent = new Intent(getApplicationContext(),Notifications.class);
                notification_intent.putExtra("user_detail",user_detail);
                startActivity(notification_intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent logout_intent = new Intent(getApplicationContext(),Login.class);
                startActivity(logout_intent);
            }
        });
    }
}
