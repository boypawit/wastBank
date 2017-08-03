package com.example.boyvi.wastbank;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private static final String Pref = "PrefWasteBank";
    private String id,name,glass,bottle,paysave ;
    SharedPreferences share ;
    SharedPreferences.Editor editor;
    private static final String URL = "https://boyvinai.000webhostapp.com/login.php";
    private  String Privilege;
    private String user,pass;
    private String loginState;

    private ProgressDialog prg ;
    private String TAG = Login.class.getSimpleName();
    private EditText editemail,editpassword ;
    private String email,password ;

    public void findID(){
        editemail = (EditText) this.findViewById(R.id.editText);
        editpassword = (EditText) this.findViewById(R.id.Edit_password);
    }
    public  void  Set(){
        email = editemail.getText().toString();
        password = editpassword.getText().toString();
    }
    private void click (){

        share = getSharedPreferences(Pref, Context.MODE_PRIVATE);
        editor = share.edit();
        if (!email.isEmpty() && !password.isEmpty()){
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            /* JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
            urlJsonObj, null, new Response.Listener<JSONObject>() */
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                //  JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL,null,new Response.Listener<JSONObject>(){
                @Override
                public void onResponse(String response) {
                    Log.d(TAG,response.toString());
                    try{


                        editemail.setText("");
                        editpassword.setText("");
                        JSONObject j= new JSONObject(response.toString());
                        //   JSONObject j = response.getJSONObject("dataUser");
                      //  String userID = j.getString("id");
                        String status_String = j.getString("result");
                        int status = Integer.parseInt(status_String);
                       // String status = j.getString("result");
                       // String Privilege = j.getString("Privilege");
                       // Toast.makeText(Login.this, status, Toast.LENGTH_SHORT).show();
                        if(status == 1){
                            Privilege = j.getString("Privilege");
                            switch (Privilege) {

                                case "0" : {
                                    Intent intent = new Intent(Login.this, Admin_Main.class);
                                  /*  intent.putExtra("id",j.getString("id"));
                                    intent.putExtra("name",j.getString("name"));
                                    intent.putExtra("paysave",j.getString("paysave"));
                                    intent.putExtra("bottle",j.getString("bottle"));
                                    intent.putExtra("glass",j.getString("glass"));*/

                                    id = j.getString("id").toString();
                                    name = j.getString("name");
                                    glass = j.getString("glass").toString();
                                    bottle = j.getString("bottle");
                                    paysave = j.getString("paysave");

                                    editor.putString("id",id);
                                    editor.putString("name",name);
                                    editor.putString("Privilege",Privilege);
                                    editor.putString("paysave",paysave);
                                    editor.putString("bottle",bottle);
                                    editor.putString("glass",glass);
                                    editor.commit();

                                   /* Bundle  bundle = getIntent().getExtras();
                                    if (bundle != null){

                                    }*/
                                    startActivity(intent);
                                    finish();
                                    break ;
                                }

                                case "1" : {
                                    Intent intent = new Intent(Login.this, User_Main.class);
                                  /*  intent.putExtra("id",j.getString("name"));
                                    intent.putExtra("name",j.getString("name"));
                                    intent.putExtra("paysave",j.getString("paysave"));
                                    intent.putExtra("bottle",j.getString("bottle"));
                                    intent.putExtra("glass",j.getString("glass"));*/

                                    id = j.getString("id");
                                    name = j.getString("name");
                                    editor.putString("Privilege",Privilege);
                                    glass = j.getString("glass");
                                    bottle = j.getString("bottle");
                                    paysave = j.getString("paysave");

                                    editor.putString("id",id);
                                    editor.putString("name",name);
                                    editor.putString("paysave",paysave);
                                    editor.putString("bottle",bottle);
                                    editor.putString("glass",glass);
                                    editor.commit();

                                    startActivity(intent);
                                    finish();
                                    break ;
                                }

                                case "2" : {
                                    Intent intent = new Intent(Login.this, User_Main.class);
                                 /*   intent.putExtra("id",j.getString("name"));
                                    intent.putExtra("name",j.getString("name"));
                                    intent.putExtra("paysave",j.getString("paysave"));
                                    intent.putExtra("bottle",j.getString("bottle"));
                                    intent.putExtra("glass",j.getString("glass"));*/
                                   id = j.getString("id");
                                    name = j.getString("name");
                                    editor.putString("Privilege",Privilege);
                                    glass = j.getString("glass");
                                    bottle = j.getString("bottle");
                                    paysave = j.getString("paysave");

                                    editor.putString("id",id);
                                    editor.putString("name",name);
                                    editor.putString("paysave",paysave);
                                    editor.putString("bottle",bottle);
                                    editor.putString("glass",glass);
                                    editor.commit();

                                    startActivity(intent);
                                    finish();
                                    break ;
                                }
                            }


                        }else if(status == 2){
                            String message = j.getString("message");
                            Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                            prg.hide();
                        }
                    }catch (JSONException e){
                        prg.hide();
                        //  textviewShow.setText(e.getMessage());
                        //  Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        Toast.makeText(Login.this, e.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("Whats wrong?", e.toString());
                        Log.e("JSON Parser", "Error parsing data " + e.toString());

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG,error.toString());
                    //  textviewShow.setText("Error");
                    prg.hide();


                }
            }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("username",email);
                    params.put("password",password);
                    return params;


                }
            };requestQueue.add(request);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findID();


       /* SharedPreferences share = getSharedPreferences(Pref, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = share.edit();*/



        getSupportActionBar().setIcon(R.mipmap.ic_toolbars);
        getSupportActionBar().setTitle("24Hours Waste Bank");


        Button loginBtn = (Button) findViewById(R.id.Loginbutton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prg = new ProgressDialog(Login.this);
                prg.setMessage("รอสักครู่...");
                prg.setCancelable(false);
                prg.show();
                Set();
                click();

            }
        });
    }
}

