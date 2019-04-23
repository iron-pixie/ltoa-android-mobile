package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

public class loggedIn extends Activity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.logged_in);
    final String memberName= getIntent().getStringExtra("memberName");
    final String memberAddress= getIntent().getStringExtra("memberAddress");
    final String username= getIntent().getStringExtra("username");
    System.out.println(memberAddress);
    System.out.println(memberName);

    TextView emailValue = findViewById(R.id.emailValue);
    TextView phoneValue = findViewById(R.id.phoneValue);
    TextView nameValue = findViewById(R.id.nameValue);
    TextView addressValue = findViewById(R.id.addressValue);

    String email="";
    String phoneNumber="";

    String[] keys = new String[]{"memberName"};
    String[] values = new String[]{memberName};

    PostRequest login = new PostRequest("https://d1jq46p2xy7y8u.cloudfront.net/member/search",keys,values);
    login.execute();
    Long wait = null;
    try {
      wait = login.get();
    } catch (Exception e) {
      e.printStackTrace();
    }
    String resp = login.getResponse();
    try{
      JSONObject jsonResp = new JSONObject(resp);
      emailValue.setText(jsonResp.getString("email"));
      email= jsonResp.getString("email");
      phoneValue.setText(jsonResp.getString("contactNumber"));
      phoneNumber=jsonResp.getString("contactNumber");
      addressValue.setText(jsonResp.getString("memberAddress"));
      nameValue.setText(jsonResp.getString("memberName"));
    }
    catch(Exception e) {
      System.out.println(e.toString());
    }

    Button createGuestButton = findViewById(R.id.createGuestButton);
    createGuestButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getBaseContext(), createGuest.class);
        intent.putExtra("memberName", memberName);
        intent.putExtra("memberAddress", memberAddress);
        intent.putExtra("username",username);
        startActivity(intent);
      }
    });

    Button viewGuestsButton = findViewById(R.id.viewGuestsButton);
    viewGuestsButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        System.out.println("inside view guest click");
        Intent intent = new Intent(getBaseContext(), viewGuests.class);
        intent.putExtra("memberName", memberName);
        intent.putExtra("memberAddress", memberAddress);
        intent.putExtra("username",username);
        startActivity(intent);
      }
    });

    final String emailFinal=email;
    final String phoneFinal=phoneNumber;
    Button editProfileButton = findViewById(R.id.editProfileButton);
    editProfileButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        System.out.println("inside view profile click");
        Intent intent = new Intent(getBaseContext(), editProfile.class);
        intent.putExtra("memberName", memberName);
        intent.putExtra("memberAddress", memberAddress);
        intent.putExtra("username",username);
        intent.putExtra("email",emailFinal);
        intent.putExtra("phone",phoneFinal);
        startActivity(intent);
      }
    });

    Button removeGuestButton = findViewById(R.id.removeGuestButton);
    removeGuestButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getBaseContext(), removeGuest.class);
        intent.putExtra("memberName", memberName);
        intent.putExtra("memberAddress", memberAddress);
        intent.putExtra("username",username);
        startActivity(intent);
      }
    });

    Button logoutButton = findViewById(R.id.logoutButton);
    logoutButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
      }
    });
  }

}
