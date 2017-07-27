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
import android.widget.TextView;
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

    private String TAG = Login.class.getSimpleName();
    private Map<String, String> params;
    private EditText editusername , editpassword ;
    private TextView textviewShow ;
    private Button button ;
    private static final String URL = "https://boyvinai.000webhostapp.com/test.php";
    private String username,password ;
    private ProgressDialog prg ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewID();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prg = new ProgressDialog(Login.this);
                prg.setMessage("Wait...");
                prg.setCancelable(false);
                prg.show();
                Set();
                click();




            }
        });
    }


    private void findViewID(){

        editusername = (EditText) findViewById(R.id.editText);
        editpassword = (EditText) findViewById(R.id.Edit_password);
        button = (Button) findViewById(R.id.Loginbutton);
        textviewShow = (TextView) findViewById(R.id.textView2);
    }

    private void Set(){
        username = editusername.getText().toString();
        password= editpassword.getText().toString();
    }

    private void click (){
        if (!username.isEmpty() && !password.isEmpty()){
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            /* JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
            urlJsonObj, null, new Response.Listener<JSONObject>() */
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                //  JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL,null,new Response.Listener<JSONObject>(){
                @Override
                public void onResponse(String response) {///// respose ค่าที่ส่งไปกลับมา
                    Log.d(TAG,response.toString());
                    try{

                        prg.hide();
                        editusername.setText("");
                        editpassword.setText("");
                        JSONObject j= new JSONObject(response.toString());
                        //   JSONObject j = response.getJSONObject("dataUser");
                        //   String userID = j.getString("id");
                        // j.getJSONObject("dataUser");

                        String id  = j.getString("id");
                        String permiss  = j.getString("permis").toString();
                        String user = j.getString("user");
                        //  textviewShow.setText("OK" +email+password);
                        int test = Integer.parseInt(permiss);
                        textviewShow.setText("OK" +id+ permiss+user+test);

                        if (test==0){
                            Intent intent = new Intent(Login.this,User_Main.class);
                            intent.putExtra("id",id);
                            intent.putExtra("username",user);
                            startActivity(intent);


                        }
                        else if(test==1){
                            Intent intent = new Intent(Login.this,Admin_Main.class);
                            intent.putExtra("id",id);
                            intent.putExtra("username",user);
                            startActivity(intent);
                        }



                    }catch (JSONException e){
                        prg.hide();
                        //  textviewShow.setText(e.getMessage());
                        //  Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("Whats wrong?", e.toString());
                        Log.e("JSON Parser", "Error parsing data " + e.toString());
                        //textviewShow.setText(e.toString());
                        textviewShow.setText("รหัสผิด");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG,error.toString());
                    //  textviewShow.setText("Error");
                    prg.hide();
                    //textviewShow.setText(error.toString());
                    textviewShow.setText("No connect Internet");

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
}

