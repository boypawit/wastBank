package com.example.boyvi.wastbank;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class User_reduce extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String Pref = "PrefWasteBank";
    ProgressDialog prg ;
    private String TAG = Login.class.getSimpleName();
    private  String URL =  "https://notenonthawat.000webhostapp.com/use_reduce.php";
    private ImageView ImgGlass , ImgBottle ;
    private String userID,type,paysave ;

    private EditText price;

    SharedPreferences share ;
    SharedPreferences.Editor editor;

    public void findID(){
            ImgGlass = (ImageView) findViewById(R.id.imageGlass);
          //  ImgGlass.isSelected();
            ImgBottle = (ImageView) findViewById(R.id.imageBottle);

            price = (EditText) findViewById(R.id.editText5);
    }

    private void click (){

        paysave = price.getText().toString();

        if (ImgGlass.isSelected()) {
            type = "1" ;
        }
        if (ImgBottle.isSelected()){
            type = "2" ;
        }

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

                        JSONObject j= new JSONObject(response.toString());
                        //   JSONObject j = response.getJSONObject("dataUser");
                        //  String userID = j.getString("id");
                        String status_String = j.getString("result");

                        // String status = j.getString("result");
                        // String Privilege = j.getString("Privilege");
                        // Toast.makeText(Login.this, status, Toast.LENGTH_SHORT).show();
                        if(status_String == "OK") {
                            share = getSharedPreferences(Pref, Context.MODE_PRIVATE);
                            editor = share.edit();
                            //editor.putString("");
                            Toast.makeText(getApplicationContext(),
                                    "บันทึกเสร็จสิ้น", Toast.LENGTH_SHORT).show();
                        }
                    }catch (JSONException e){
                        prg.hide();
                        //  textviewShow.setText(e.getMessage());
                        //  Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        Toast.makeText(User_reduce.this, e.toString(), Toast.LENGTH_SHORT).show();
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
                    params.put("userID",userID);
                    params.put("type",type);
                    params.put("price",paysave);
                   // params.put("image");
                    return params;


                }
            };requestQueue.add(request);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        share = getSharedPreferences("PrefWasteBank",Context.MODE_PRIVATE);
        String userID = share.getString("id","No value") ;

        findID();

        price.setText("0");

        ///////Edit by pawit//////////

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageButton buttoncamera = (ImageButton) findViewById(R.id.imageButton2);
        buttoncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(Intent.createChooser(intent
                        , "Take a picture with"), 0);
            }
        });

        Button button = (Button) findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   /* String fake = "Invalid Username or Password";
                    Toast.makeText(User_reduce.this,fake,Toast.LENGTH_LONG).show();*/

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(User_reduce.this);
                builder.setMessage("คุณต้อการจะบันทึกข้อมูลทั้งหมดหรือไม่?");
                builder.setPositiveButton("บันทึก", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        prg = new ProgressDialog(User_reduce.this);
                        prg.setMessage("รอสักครู่...");
                        prg.setCancelable(false);
                        prg.show();

                        click();
                        Toast.makeText(getApplicationContext(),
                                "บันทึกเสร็จสิ้น", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("ไม่บันทึก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dialog.dismiss();
                    }
                });
                builder.show();

            }
        });



        final ImageView Demo_button = (ImageView )findViewById(R.id.imgGlass);
        final ImageView second_button = (ImageView )findViewById(R.id.imgBut);

// when you click this demo button
        Demo_button.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        Demo_button.setImageResource(R.drawable.glass_stroke);
                        second_button.setImageResource(R.drawable.bottle1);
                                           }
                                       });

                second_button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Demo_button.setImageResource(R.drawable.glass2);
                        second_button.setImageResource(R.drawable.bottle_stroke);
                                                     }
                                                 });
        //////////////end////////////////





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Main) {
            // Handle the camera action
            Intent intent = new Intent(User_reduce.this,User_Main.class);
            startActivity(intent);
        }else if (id == R.id.nav_camera){
            Intent intent = new Intent(User_reduce.this,User_reduce.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(User_reduce.this,User_Statistics.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(User_reduce.this,Login.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
