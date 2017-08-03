package com.example.boyvi.wastbank;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class User_Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //private static final String Pref = "PrefWasteBank";
    SharedPreferences share ;
    SharedPreferences.Editor editor;

    private String checkAlert ;
    private TextView glass,bottle,paysave;
    private ProgressDialog prg ;
    private String URL = "https://jirayuhe57.000webhostapp.com/android/use_reduce.php";
    private String TAG = User_Main.class.getSimpleName();

    private String id ;
    private String glass_text , bottle_text , paysave_text ;
    //private String email,password ;

    public void findID(){
        glass =  (TextView) this.findViewById(R.id.glass);
        bottle= (TextView) this.findViewById(R.id.bottle);
        paysave = (TextView) this.findViewById(R.id.paysave);

    }

    private void loadData (){

        /*share = getSharedPreferences(Pref, Context.MODE_PRIVATE);
        editor = share.edit();*/

        RequestQueue requestQueue = Volley.newRequestQueue(this);
            /* JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
            urlJsonObj, null, new Response.Listener<JSONObject>() */
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            //  JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL,null,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(String response) {
                Log.d(TAG,response.toString());
                try{



                    JSONObject j= new JSONObject(response.toString());
                    //   JSONObject j = response.getJSONObject("dataUser");
                    //  String userID = j.getString("id");
                    String status_String = j.getString("result");
                    Toast.makeText(User_Main.this,"aaaa้",Toast.LENGTH_SHORT).show();
                    switch(status_String) {
                        case "OK" :
                            Toast.makeText(User_Main.this,"bbb",Toast.LENGTH_SHORT).show();
                            prg.hide();
                            glass.setText(j.getString("glass").toString());
                            bottle.setText(j.getString("bottle").toString());
                            paysave.setText(j.getString("paysave").toString());
                            break ;
                        default:
                            Toast.makeText(User_Main.this,"ไม่สามารถโหลดข้อมูลการใช้งานได้",Toast.LENGTH_SHORT).show();
                            break;

                    }
                    // String status = j.getString("result");
                    // String Privilege = j.getString("Privilege");
                    // Toast.makeText(Login.this, status, Toast.LENGTH_SHORT).show();

                }catch (JSONException e){
                    prg.hide();
                    //  textviewShow.setText(e.getMessage());
                    //  Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    Toast.makeText(User_Main.this, e.toString(), Toast.LENGTH_SHORT).show();
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
                params.put("userID",id);

                return params;


            }
        };requestQueue.add(request);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        share = getSharedPreferences("PrefWasteBank", Context.MODE_PRIVATE);
        checkAlert = share.getString("checkAlert","");

        id = share.getString("id","");

        String checknumberAlert = "0";
        if(checknumberAlert == "1") {
            aleatDetail();
         /*  editor.putString("checkAlert","0");
            editor.commit();*/

         checknumberAlert = "1";
        }


        findID();
        loadData();
        prg = new ProgressDialog(User_Main.this);
        prg.setMessage("รอสักครู่...");
        prg.setCancelable(false);
        prg.show();


/*
        glass.setText(share.getString("glass","No"));
        bottle.setText(share.getString("bottle","No"));
        paysave.setText(share.getString("paysave","No"));
*/


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_toolbars);
        getSupportActionBar().setTitle("24Hrs Waste Bank");

       /* Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String user = intent.getStringExtra("username");

        TextView textUsername = (TextView) findViewById(R.id.username);
        textUsername.setText(user);
        
         /*
        ////////Zoom picture///////////
        int i=500;
        int j = 500;   //if
        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        imageView.getLayoutParams().width=i;
        imageView.getLayoutParams().height=j;
         */



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        // call header
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main1);
        // เรียก textview ผ่านหัว
        TextView name = (TextView) headerView.findViewById(R.id.nav_name);

        name.setText(share.getString("name","No"));
        navigationView.setNavigationItemSelectedListener(this);



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
            // ยังไม่ได้ส่งอะไรไป
            Intent intent = new Intent(User_Main.this,User_Main.class);
            startActivity(intent);
            // Handle the camera action
        }else if (id == R.id.nav_camera){
            Intent intent = new Intent(User_Main.this,User_reduce.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(User_Main.this,User_Statistics.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(User_Main.this,Login.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void aleatDetail(){
        AlertDialog.Builder builder = new AlertDialog.Builder(User_Main.this);
        builder.setTitle("                      รู้หรือไม่?");
        builder.setMessage("\nขวด 1 ขวด ลดคาร์บอนไดออกไซด์ได้ 10 กรัม \n" +
                "\nแก้ว 1 ใบ  ลดคาร์บอนไดออกไซด์ได้ 10 กรัม");

        builder.setPositiveButton("ตกลง", null);
        builder.show();


    }

}
