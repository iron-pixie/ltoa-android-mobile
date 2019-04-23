package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class multipleGuests extends Activity {
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.multiple_guest);

    final String memberName = getIntent().getStringExtra("memberName");
    final String memberAddress = getIntent().getStringExtra("memberAddress");
    final String username = getIntent().getStringExtra("username");

    final EditText multipleGuestText = findViewById(R.id.multipleGuestText);

    final Button backButton= findViewById(R.id.backButton);
    backButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        Intent intent = new Intent(getBaseContext(), loggedIn.class);
        intent.putExtra("memberName", memberName);
        intent.putExtra("memberAddress", memberAddress);
        intent.putExtra("username",username);
        startActivity(intent);
      }
    });

    final Button submitButton= findViewById(R.id.submitButton);
    submitButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        String text = multipleGuestText.getText().toString();
        String[] textArray = text.split(",");
        for (int i=0;i<textArray.length;i++){
          String[] keys = new String[]{"guestName","residentAddress","allowedStartTime","allowedEndTime","reason","residentName","userName"};
            String[] values = new String[]{
              textArray[i].trim(),
              memberAddress,
              "multi",
              "multi",
              "party",
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
        }
        Intent intent = new Intent(getBaseContext(), loggedIn.class);
        intent.putExtra("memberName", memberName);
        intent.putExtra("memberAddress", memberAddress);
        intent.putExtra("username",username);
        startActivity(intent);
      }
    });

  }
}
