package com.harishram.anplatra;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Add_Event_Handler extends AsyncTask<String,Void,Void> {
    String name,date,source,destination,time,mode;
    URL server_url;
    public Add_Event_Handler(String name,String date,String source,String destination,String time,String mode){
        this.name = name;
        this.date = date;
        this.source = source;
        this.destination = destination;
        this.time = time;
        this.mode = mode;
    }
    @Override
    protected Void doInBackground(String... strings) {
        String address = strings[0];
        try {
            server_url = new URL("http://"+address+"/anplatra_server/addevent.php");
            HttpURLConnection server_connection = (HttpURLConnection) server_url.openConnection();
            server_connection.setRequestMethod("POST");
            server_connection.setDoOutput(true);
            String params = "name="+name+"&date="+date+"&source="+source+"&destination="+destination+"&time="+time+"&mode="+mode;
            BufferedWriter request_params = new BufferedWriter(new OutputStreamWriter(server_connection.getOutputStream()));
            request_params.write(params);
            request_params.flush();
            request_params.close();
            BufferedReader response = new BufferedReader(new InputStreamReader(server_connection.getInputStream()));
            System.out.println(response.readLine());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
