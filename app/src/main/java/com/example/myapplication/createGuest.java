package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.Date;

public class createGuest extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.create_guest);
    final String memberName = getIntent().getStringExtra("memberName");
    final String memberAddress= getIntent().getStringExtra("memberAddress");
    final String username = getIntent().getStringExtra("username");
    System.out.println(memberName);
    System.out.println(memberAddress);
    System.out.println(username);

    final CheckBox indefiniteBox = findViewById(R.id.indefiniteCheckBox);
    final EditText endDateText= findViewById(R.id.endDateText);
    final EditText startDateText= findViewById(R.id.startDateText);
    final EditText guestNameText= findViewById(R.id.guestNameText);
    final EditText reasonText= findViewById(R.id.reasonText);
    final Button backButton= findViewById(R.id.backButton);
    indefiniteBox.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(indefiniteBox.isChecked()){
          endDateText.setEnabled(false);
          startDateText.setEnabled(false);
        }
        else{
          endDateText.setEnabled(true);
          startDateText.setEnabled(true);
        }
      }
    });

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

    final Button submitButton = findViewById(R.id.submitButton);
    submitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String[] keys = new String[]{"guestName","residentAddress","allowedStartTime","allowedEndTime","reason","residentName","userName"};
        System.out.println(guestNameText.getText().toString());
        if(!guestNameText.getText().toString().isEmpty() && !startDateText.getText().toString().isEmpty() && !endDateText.getText().toString().isEmpty() && !reasonText.getText().toString().isEmpty()) {
          String[] values = new String[]{
            guestNameText.getText().toString(),
            memberAddress,
            startDateText.getText().toString(),
            endDateText.getText().toString(),
            reasonText.getText().toString(),
            memberName,
            username
          };

          PostRequest guest = new PostRequest("https://d1jq46p2xy7y8u.cloudfront.net/guest/add", keys, values);
          guest.execute();
          Long wait = null;
          try {
            wait = guest.get();
          } catch (Exception e) {
            e.printStackTrace();
          }
          String resp = guest.getResponse();
          Snackbar.make(view, "Guest Added", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
          Intent intent = new Intent(getBaseContext(), loggedIn.class);
          intent.putExtra("memberName", memberName);
          intent.putExtra("memberAddress", memberAddress);
          intent.putExtra("username",username);
          startActivity(intent);
        }
        else if(!guestNameText.getText().toString().isEmpty() && !reasonText.getText().toString().isEmpty() && indefiniteBox.isChecked()){
          String[] values = new String[]{
            guestNameText.getText().toString(),
            memberAddress,
            (new Date()).toString(),
            "2020-12-12",
            reasonText.getText().toString(),
            memberName,
            username
          };

          PostRequest guest = new PostRequest("https://d1jq46p2xy7y8u.cloudfront.net/guest/add", keys, values);
          guest.execute();
          Long wait = null;
          try {
            wait = guest.get();
          } catch (Exception e) {
            e.printStackTrace();
          }
          String resp = guest.getResponse();
          Snackbar.make(view, "Guest Added", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
          Intent intent = new Intent(getBaseContext(), loggedIn.class);
          intent.putExtra("memberName", memberName);
          intent.putExtra("memberAddress", memberAddress);
          intent.putExtra("username",username);
          startActivity(intent);
        }
        else{
          Snackbar.make(view, "Data Missing", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
        }
      }
    });

    final Button multipleGuestButton = findViewById(R.id.multipleGuestButton);
    multipleGuestButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getBaseContext(), multipleGuests.class);
        intent.putExtra("memberName", memberName);
        intent.putExtra("memberAddress", memberAddress);
        intent.putExtra("username",username);
        startActivity(intent);
      }
    });
  }
}
