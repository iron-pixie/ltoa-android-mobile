package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class deleteIndividual extends Activity {
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.delete_individual);

    final String memberName = getIntent().getStringExtra("memberName");
    final String memberAddress = getIntent().getStringExtra("memberAddress");
    final String username = getIntent().getStringExtra("username");
    final String guestName = getIntent().getStringExtra("guestName");

    TextView guestText = findViewById(R.id.deleteText);
    guestText.setText("Would you like to delete "+guestName+"?");

    Button noButton = findViewById(R.id.noButton);
    noButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getBaseContext(), removeGuest.class);
        intent.putExtra("memberName", memberName);
        intent.putExtra("memberAddress", memberAddress);
        intent.putExtra("username",username);
        startActivity(intent);
      }
    });

    Button yesButton = findViewById(R.id.yesButton);
    yesButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        String[] keys = new String[]{"guestName"};
        String[] values = new String[]{guestName};

        PostRequest login = new PostRequest("https://d1jq46p2xy7y8u.cloudfront.net/guest/delete",keys,values);
        login.execute();
        Long wait = null;
        try {
          wait = login.get();
        } catch (Exception e) {
          e.printStackTrace();
        }
        String resp = login.getResponse();
        System.out.println(resp);
        Intent intent = new Intent(getBaseContext(), removeGuest.class);
        intent.putExtra("memberName", memberName);
        intent.putExtra("memberAddress", memberAddress);
        intent.putExtra("username",username);
        startActivity(intent);
      }
    });

  }
}
