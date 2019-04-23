package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONObject;

public class editProfile extends Activity {
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.edit_profile);

    final String memberName = getIntent().getStringExtra("memberName");
    final String memberAddress = getIntent().getStringExtra("memberAddress");
    final String username = getIntent().getStringExtra("username");
    final String email = getIntent().getStringExtra("email");
    final String phone = getIntent().getStringExtra("phone");

    final EditText emailText = findViewById(R.id.emailText);
    emailText.setText(email);
    final EditText addressText = findViewById(R.id.addressText);
    addressText.setText(memberAddress);
    final EditText phoneText = findViewById(R.id.phoneText);
    phoneText.setText(phone);

    final Button submitButton= findViewById(R.id.submitButton);
    submitButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {

        String[] keys = new String[]{"memberName","memberAddress","contactNumber","email"};
        String[] values = new String[]{memberName,addressText.getText().toString(),phoneText.getText().toString(),emailText.getText().toString()};

        PostRequest login = new PostRequest("https://d1jq46p2xy7y8u.cloudfront.net/member/update",keys,values);
        login.execute();
        Long wait = null;
        try {
          wait = login.get();
        } catch (Exception e) {
          e.printStackTrace();
        }
        String resp = login.getResponse();
        System.out.println(resp);
        Intent intent = new Intent(getBaseContext(), viewProfile.class);
        intent.putExtra("memberName", memberName);
        intent.putExtra("memberAddress", addressText.getText().toString());
        intent.putExtra("username",username);
        startActivity(intent);
      }
    });

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
