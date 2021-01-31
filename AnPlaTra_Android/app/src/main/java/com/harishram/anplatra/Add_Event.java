package com.harishram.anplatra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Add_Event extends AppCompatActivity {

    String name,date,source,destination,time,mode;
    RadioGroup group_mode;
    RadioButton select_mode;
    int select_id;
    EditText name_text,date_text,source_text,destination_text,time_text;
    TextView add_event,back;
    Bundle user_detail;
    Add_Event_Handler enter_event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        getSupportActionBar().hide();
        user_detail = getIntent().getBundleExtra("user_detail");
        name_text = (EditText) findViewById(R.id.editText5);
        date_text = (EditText) findViewById(R.id.editText11);
        source_text = (EditText) findViewById(R.id.editText12);
        destination_text = (EditText) findViewById(R.id.editText13);
        time_text = (EditText) findViewById(R.id.editText14);
        group_mode = (RadioGroup) findViewById(R.id.radioGroup);
        add_event = (TextView) findViewById(R.id.textView8);
        back = (TextView) findViewById(R.id.textView9);
        add_event.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                name = name_text.getText().toString();
                date = date_text.getText().toString();
                source = source_text.getText().toString();
                destination = destination_text.getText().toString();
                time = time_text.getText().toString();
                select_id = group_mode.getCheckedRadioButtonId();
                select_mode = (RadioButton) findViewById(select_id);
                mode = select_mode.getText().toString();
                enter_event = new Add_Event_Handler(name,date,source,destination,time,mode);
                enter_event.execute(user_detail.getString("address"));
                Intent back_intent = new Intent(getApplicationContext(),Menu.class);
                back_intent.putExtra("user_detail",user_detail);
                startActivity(back_intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent back_intent = new Intent(getApplicationContext(),Menu.class);
                back_intent.putExtra("user_detail",user_detail);
                startActivity(back_intent);
            }
        });

    }
}
