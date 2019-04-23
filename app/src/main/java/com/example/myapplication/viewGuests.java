package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class viewGuests extends Activity {
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.view_guest);

    final String memberName= getIntent().getStringExtra("memberName");
    final String memberAddress= getIntent().getStringExtra("memberAddress");
    final String username= getIntent().getStringExtra("username");

    TableLayout guestTable = findViewById(R.id.guestTable);

    GetRequest login = new GetRequest("https://d1jq46p2xy7y8u.cloudfront.net/guest/all");
    login.execute();
    Long wait = null;
    try {
      wait = login.get();
    } catch (Exception e) {
      e.printStackTrace();
    }
    String resp = login.getResponse();
    System.out.println(resp);
    ;
    try {
      JSONArray guests = new JSONArray(resp);
      JSONArray guestsOfResident = new JSONArray();
      for(int i=0;i<guests.length();i++){
        JSONObject tempObj = guests.getJSONObject(i);
        if(tempObj.get("residentName").equals(memberName)){
          guestsOfResident.put(tempObj);
          TextView tempTableView = new TextView(this);
          tempTableView.setText(tempObj.getString("guestName"));
          tempTableView.setTextSize(20);
          guestTable.addView(tempTableView);
        }

      }
    } catch (JSONException e) {
      e.printStackTrace();
    }

    final Button backButton= findViewById(R.id.backButton);
    backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getBaseContext(), loggedIn.class);
        intent.putExtra("memberName", memberName);
        intent.putExtra("memberAddress", memberAddress);
        intent.putExtra("username",username);
        startActivity(intent);
      }
    });

  }
}
