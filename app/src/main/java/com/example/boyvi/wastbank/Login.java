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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private ListView jsjonLisview;
    private ArrayList<String> exData;
    private ProgressDialog progressDialog;
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
    public  void  set(){
        email = editemail.getText().toString();
        password = editpassword.getText().toString();
    }
    private void click (){
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

                        prg.hide();
                        editemail.setText("");
                        editpassword.setText("");
                        JSONObject j= new JSONObject(response.toString());
                        //   JSONObject j = response.getJSONObject("dataUser");
                        String userID = j.getString("id");
                        // j.getJSONObject("dataUser");

                        String email  = j.getString("email");
                        String password = j.getString("password");
                        //  textviewShow.setText("OK" +email+password);
                        //textviewShow.setText("OK" + " "+userID + "  "+ email+"  "+password);
                        Intent intent = new Intent();
                        startActivity();
                    }catch (JSONException e){
                        prg.hide();
                        //  textviewShow.setText(e.getMessage());
                        //  Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("Whats wrong?", e.toString());
                        Log.e("JSON Parser", "Error parsing data " + e.toString());
                        textviewShow.setText(e.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG,error.toString());
                    //  textviewShow.setText("Error");
                    prg.hide();
                    textviewShow.setText(error.toString());

                }
            }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("username",username);
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


        getSupportActionBar().setIcon(R.mipmap.ic_toolbars);
        getSupportActionBar().setTitle("24Hours Waste Bank");


        Button loginBtn = (Button) findViewById(R.id.Loginbutton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set();

            }
        });
    }
}

