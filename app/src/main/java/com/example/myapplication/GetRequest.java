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

public class GetRequest extends AsyncTask<URL, Integer, Long> {

  String urlString;
  String response ="";
  Context myContext;

  GetRequest(String urlString) {
    this.urlString=urlString;
  }

  public void postData() throws IOException {

    URL url = new URL(this.urlString);
    HttpURLConnection client = null;
    try {
      client = (HttpURLConnection) url.openConnection();
      client.setRequestMethod("GET");
      client.setRequestProperty("Content-Type", "application/json; charset=utf-8");
      client.setDoInput(true);
        int responseCode = client.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
          String line;
          BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
          while ((line = br.readLine()) != null) {
            response += line;
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
