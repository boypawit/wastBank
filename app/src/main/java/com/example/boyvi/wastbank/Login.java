package com.example.boyvi.wastbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Login extends AppCompatActivity {
    EditText UserOrAdmin;
    EditText password;
    private ListView jsjonLisview;
    private ArrayList<String> exData;
    private ProgressDialog progressDialog;
    private String user,pass;
    private String loginState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserOrAdmin = (EditText) this.findViewById(R.id.editText);
        password = (EditText) this.findViewById(R.id.Edit_password);

        getSupportActionBar().setIcon(R.mipmap.ic_toolbars);
        getSupportActionBar().setTitle("24Hours Waste Bank");


        Button buttonCountry = (Button) findViewById(R.id.Loginbutton);
        buttonCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ยังไม่ได้ส่งอะไรไป
                //User = UserOrAdmin.getText().toString();
               if(UserOrAdmin.getText().length()==4){
               // if(User==" user "){
                Intent intent = new Intent(Login.this,User_Main.class);
                startActivity(intent);
               }else if (UserOrAdmin.getText().length()==1){
                   String fake = "Invalid Username or Password";
                   Toast.makeText(Login.this,fake,Toast.LENGTH_LONG).show();

               }

               else{ Intent intent = new Intent(Login.this,Admin_Main.class);
                startActivity(intent);}
                finish();



            }
        });
    }
}

