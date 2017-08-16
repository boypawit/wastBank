package com.example.boyvi.wastbank;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Admin_Search extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences share ;
    SharedPreferences shareimage ;
    private String sharepicture;
    Spinner spinnerGender;
    String spnselectGender;
    String selecetspn;
    ImageButton btnsearch;
    private TextView search,glass,bottle,save_price,reduce_waste,reduce_co2,name;
   private ImageView imageProfile ;
    String textsearch;
    private ProgressDialog prg ;
    private String URL = "http://wastebank.ilab-ubu.net/android/admin_search_user.php";


    ///// image search
    String imagesearch;
    String PrivilageProfile;
    String UrlImagesearch;

    private String TAG = Admin_Search.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        share = getSharedPreferences("PrefWasteBank", Context.MODE_PRIVATE);

        shareimage = getSharedPreferences("imageprofile", Context.MODE_PRIVATE);
        sharepicture = shareimage.getString("image_data","");

        findID();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main_admin4);
        TextView name = (TextView) headerView.findViewById(R.id.nav_name);
        ImageView imagepic =(ImageView) headerView.findViewById(R.id.pictureProfile4);
        if( !sharepicture.equalsIgnoreCase("")){
            byte[] b = Base64.decode(sharepicture, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            imagepic.setImageBitmap(bitmap);
        }

        name.setText(share.getString("name","No"));
        navigationView.setNavigationItemSelectedListener(this);


        ///////////////////gender spinner/////////////
        spinnerGender = (Spinner) findViewById(R.id.spinnerGender);
        final String[] gender = getResources().getStringArray(R.array.gender_arrays);
        ArrayAdapter<String> adaptertoday = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,gender);
        spinnerGender.setAdapter(adaptertoday);


        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prg = new ProgressDialog(Admin_Search.this);
                prg.setMessage("รอสักครู่...");
                prg.setCancelable(false);

                textsearch = search.getText().toString();
                spnselectGender = spinnerGender.getSelectedItem().toString();
                switch (spnselectGender) {
                    case "รหัสนักศึกษา" :
                        spnselectGender = "studentID" ;
                        break;
                    case "ชื่อ-สกุล" :
                        spnselectGender = "name" ;
                }
                if(textsearch.isEmpty()){
                    Toast.makeText(Admin_Search.this,"กรุณากรอกชื่อหรือรหัสที่จะใช้ค้นหา",Toast.LENGTH_SHORT).show();
                } else {
                    prg.show();
                    loadData();

                }



/*
                PrivilageProfile = "2";
                searchImage ();
*/

            }
        });
    }

    private void findID(){

        search = (TextView) findViewById(R.id.textSearch);
        btnsearch = (ImageButton) findViewById(R.id.buttonSearch);
        glass = (TextView) findViewById(R.id.static_glass);
        bottle = (TextView) findViewById(R.id.static_bottle);
        save_price= (TextView) findViewById(R.id.static_payprice);
        reduce_waste = (TextView) findViewById(R.id.static_weste);
        reduce_co2 = (TextView) findViewById(R.id.static_co2);
        imageProfile = (ImageView) findViewById(R.id.profileSearch) ;

        name = (TextView) findViewById(R.id.name);
    }

    ///////////////////////// searchimage By pqwit//////////
    private void searchImage (){
//  UrlPicture = "https://jirayuhe57.000webhostapp.com/android/images/"+jsonimageprofile;
        switch (PrivilageProfile){
            case "0": UrlImagesearch = "http://wastebank.ilab-ubu.net/profile/personal/"+imagesearch; break;
            case "1": UrlImagesearch = "http://wastebank.ilab-ubu.net/profile/personal/"+imagesearch; break;
  //          case "2": UrlImagesearch = "http://wastebank.ilab-ubu.net/profile/student/student1.jpg";  break;
            case "2": UrlImagesearch = "http://wastebank.ilab-ubu.net/profile/student/"+imagesearch;  break;
        }
        ImageRequest imageRequest = new ImageRequest(UrlImagesearch,new Response.Listener<Bitmap>(){
            @Override
            public void onResponse(Bitmap bitmap) {

                imageProfile.setImageBitmap(bitmap);

            }
        },0,0, ImageView.ScaleType.FIT_XY,null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(imageRequest);

    }



    private void loadData (){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

           @Override
            public void onResponse(String response) {
                Log.d(TAG,response);
                try{

                    JSONObject j= new JSONObject(response);
                    String status_String = j.getString("result");
                   // Toast.makeText(Admin_Search.this,status_String,Toast.LENGTH_SHORT).show();
                    prg.hide();


                    switch(status_String) {
                        case "OK" :
                            // Toast.makeText(User_Main.this,id,Toast.LENGTH_SHORT).show();
                            name.setText(j.getString("name"));
                            glass.setText(j.getString("glass"));
                            bottle.setText(j.getString("bottle"));
                            save_price.setText(j.getString("paysave"));
                            reduce_waste.setText(j.getString("waste_number"));
                            reduce_co2.setText(j.getString("countCo2"));

                            //////////// image//////////
                            PrivilageProfile = j.getString("Privilege");
                       //     PrivilageProfile = "2";
                            imagesearch = j.getString("pic_profi");
                        //    Toast.makeText(Admin_Search.this,j.getString("name"),Toast.LENGTH_SHORT).show();
                            searchImage ();



                            prg.hide();

                            break ;
                        default:
                            Toast.makeText(Admin_Search.this,"ไม่สามารถโหลดข้อมูลการใช้งานได้",Toast.LENGTH_SHORT).show();
                            break;

                    }

                }catch (JSONException e){
                    prg.hide();
                    //  textviewShow.setText(e.getMessage());
                    //  Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                   // Toast.makeText(Admin_Search.this, e.toString(), Toast.LENGTH_SHORT).show();

                    Toast.makeText(Admin_Search.this,spnselectGender + " " + textsearch, Toast.LENGTH_SHORT).show();
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
                params.put("key",spnselectGender);
                params.put("value",textsearch);


                return params;


            }
        };requestQueue.add(request);

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

        if (id == R.id.nav_index) {
            Intent intent = new Intent(Admin_Search.this,Admin_Main.class);
            startActivity(intent);

        }else if (id == R.id.nav_reduce) {
            Intent intent = new Intent(Admin_Search.this,Adnin_Reduce.class);
            startActivity(intent);

        } else if (id == R.id.nav_staticyou) {
            Intent intent = new Intent(Admin_Search.this,Admin_StatisticsYou.class);
            startActivity(intent);

        } else if (id == R.id.nav_staticall) {
            Intent intent = new Intent(Admin_Search.this,Admin_StatisticsAll.class);
            startActivity(intent);
        } else if (id == R.id.nav_search) {
            Intent intent = new Intent(Admin_Search.this,Admin_Search.class);
            startActivity(intent);
        }else if (id == R.id.nav_logout) {
            Intent intent = new Intent(Admin_Search.this,Login.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
