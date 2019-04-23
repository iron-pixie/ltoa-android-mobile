package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class viewProfile extends Activity {
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.view_profile);

    final String memberName= getIntent().getStringExtra("memberName");
    final String memberAddress= getIntent().getStringExtra("memberAddress");
    final String username= getIntent().getStringExtra("username");

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

    final String emailFinal = email;
    final String phoneFinal = phoneNumber;


    final Button editButton= findViewById(R.id.editButton);
    editButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getBaseContext(), editProfile.class);
        intent.putExtra("memberName", memberName);
        intent.putExtra("memberAddress", memberAddress);
        intent.putExtra("username",username);
        intent.putExtra("email",emailFinal);
        intent.putExtra("phone",phoneFinal);
        startActivity(intent);
      }
    });

  }
}
