package com.harishram.anplatra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    String username,password;
    EditText username_text,password_text;
    TextView login,register;
    Intent menu_intent,register_intent;
    Bundle user_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        username_text = (EditText) findViewById(R.id.editText2);
        password_text = (EditText) findViewById(R.id.editText3);
        login = (TextView) findViewById(R.id.textView);
        register = (TextView) findViewById(R.id.textView2);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = username_text.getText().toString();
                password = password_text.getText().toString();
                FirebaseDatabase user_db = FirebaseDatabase.getInstance();
                try {
                    DatabaseReference user_dbr = user_db.getReference("User/" + username);
                    user_dbr.addValueEventListener(new ValueEventListener(){

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User_Info user_data = snapshot.getValue(User_Info.class);
                            try {
                                if ((username.equals(user_data.username)) && (password.equals(user_data.password))) {
                                    user_detail = new Bundle();
                                    Toast welcome_msg = Toast.makeText(getApplicationContext(), "Login successful. Welcome " + username, Toast.LENGTH_SHORT);
                                    welcome_msg.show();
                                    menu_intent = new Intent(getApplicationContext(), Menu.class);
                                    user_detail.putString("username", username);
                                    user_detail.putString("name", user_data.name);
                                    user_detail.putString("address", user_data.address);
                                    menu_intent.putExtra("user_detail", user_detail);
                                    startActivity(menu_intent);
                                } else {
                                    Toast error = Toast.makeText(getApplicationContext(), "Username or password is invalid. Try Again", Toast.LENGTH_SHORT);
                                    error.show();
                                }
                            }catch(NullPointerException e){
                                Toast error = Toast.makeText(getApplicationContext(),"Username or password is invalid. Try Again",Toast.LENGTH_SHORT);
                                error.show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }catch(NullPointerException e){
                    Toast error = Toast.makeText(getApplicationContext(),"Username or password is invalid. Try Again",Toast.LENGTH_SHORT);
                    error.show();
                }

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_intent = new Intent(getApplicationContext(), Register.class);
                startActivity(register_intent);
            }
        });
    }
}
