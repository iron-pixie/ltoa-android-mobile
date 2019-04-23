package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class PostRequest extends AsyncTask<URL, Integer, Long> {

  String urlString;
  String[] keys;
  String[] values;
  String response ="";
  Context myContext;

  PostRequest(String urlString ,String[] keys, String[] values) {
    this.urlString=urlString;
    this.keys=keys;
    this.values=values;
  }

  public void postData() throws IOException {

    URL url = new URL(this.urlString);
    HttpURLConnection client = null;
    try {
      client = (HttpURLConnection) url.openConnection();
      client.setRequestMethod("POST");
      client.setRequestProperty("Content-Type", "application/json; charset=utf-8");
      client.setDoInput(true);
      client.setDoOutput(true);

      OutputStream os = client.getOutputStream();
      BufferedWriter writer = new BufferedWriter(
        new OutputStreamWriter(os, "UTF-8"));
      String postBody=null;
      try{
        JSONObject json =new JSONObject();
        for(int i=0;i<this.keys.length;i++){
            json.put(keys[i],values[i]);
        }
        postBody=json.toString();
      }
      catch(Exception e){
        System.out.print("JSON Object creation error");
      }
       if(postBody!=null){
         writer.write(postBody);

         writer.flush();
         writer.close();
         os.close();
         int responseCode = client.getResponseCode();
         if (responseCode == HttpsURLConnection.HTTP_OK) {
           String line;
           BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
           while ((line = br.readLine()) != null) {
             response += line;
           }
         }
       }
    }
    catch (MalformedURLException e){
      e.printStackTrace();
    }
    finally {
      if(client != null) // Make sure the connection is not null.
        client.disconnect();
    }
  }

  @Override
  protected Long doInBackground(URL... params) {
    try {
      postData();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // This counts how many bytes were downloaded
    final byte[] result = response.getBytes();
    Long numOfBytes = Long.valueOf(result.length);
    return numOfBytes;
  }

  protected void onPostExecute(Long result) {
    System.out.println("Downloaded " + result + " bytes");
    System.out.println("response:"+response);
  }

  protected String getResponse(){
    return this.response;
  }
}
