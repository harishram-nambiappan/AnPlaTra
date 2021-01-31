package com.harishram.anplatra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    String name,email,username,password,re_password,address;
    EditText name_text,email_text,username_text,password_text,re_password_text,address_text;
    TextView register, back;
    Intent back_intent,login_intent;
    FirebaseDatabase user_db;
    DatabaseReference user_dbr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        name_text = (EditText) findViewById(R.id.editText);
        email_text = (EditText) findViewById(R.id.editText6);
        username_text = (EditText) findViewById(R.id.editText7);
        password_text = (EditText) findViewById(R.id.editText8);
        re_password_text = (EditText) findViewById(R.id.editText9);
        address_text = (EditText) findViewById(R.id.editText10);
        register = (TextView) findViewById(R.id.textView11);
        back = (TextView) findViewById(R.id.textView12);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password_text.getText().toString().equals(re_password_text.getText().toString())){
                    name = name_text.getText().toString();
                    email = email_text.getText().toString();
                    username = username_text.getText().toString();
                    password = password_text.getText().toString();
                    address = address_text.getText().toString();
                    User_Info new_user = new User_Info(name,email,username,password,address);
                    user_db = FirebaseDatabase.getInstance();
                    user_dbr = user_db.getReference();
                    user_dbr.child("User").child(username).setValue(new_user);
                    login_intent = new Intent(getApplicationContext(),Login.class);
                    startActivity(login_intent);
                }
                else{
                    Toast error = Toast.makeText(getApplicationContext(),"Passwords do not match", Toast.LENGTH_SHORT);
                    error.show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_intent = new Intent(getApplicationContext(),Login.class);
                startActivity(back_intent);
            }
        });


    }
}
