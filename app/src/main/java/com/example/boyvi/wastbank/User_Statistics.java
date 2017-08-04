package com.example.boyvi.wastbank;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class User_Statistics extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences shareimage ;
    private String sharepicture;

    Spinner spinnerday, spinnermonth,spinneryear;
    Spinner spinnertoday, spinnertomonth,spinnertoyear;
    Button buttonOk;
    private ProgressDialog prg ;
    String date;
    String todate;

    String spnDay,spnMonth,spnYear,spnToDay,SpnToMonth,SpnToYear;

    private TextView glass,bottle,save_price,reduce_waste,reduce_co2;

    private static final String Pref = "PrefWasteBank";
    SharedPreferences share ;
    private static final String URL = "https://jirayuhe57.000webhostapp.com/android/showall_stat.php";
    private String TAG = Login.class.getSimpleName();
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        share = getSharedPreferences("PrefWasteBank", Context.MODE_PRIVATE);
        userID = share.getString("id","No");

        shareimage = getSharedPreferences("imageprofile", Context.MODE_PRIVATE);
        sharepicture = shareimage.getString("image_data","");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main3);
        TextView name = (TextView) headerView.findViewById(R.id.nav_name);

        ImageView image =(ImageView) headerView.findViewById(R.id.imageProStatic);

        if( !sharepicture.equalsIgnoreCase("")){
            byte[] b = Base64.decode(sharepicture, Base64.DEFAULT);
            Bitmap bitm = BitmapFactory.decodeByteArray(b, 0, b.length);
            image.setImageBitmap(bitm);
        }

        name.setText(share.getString("name","No"));

        navigationView.setNavigationItemSelectedListener(this);

        click ();
        findID();
        prg = new ProgressDialog(User_Statistics.this);
        prg.setMessage("รอสักครู่...");
        prg.setCancelable(false);
        prg.show();


      ///////////////////Spinner date///////////////////////////////
        spinnerday = (Spinner) findViewById(R.id.spinner_day);
        final String[] day = getResources().getStringArray(R.array.day_arrays);
        ArrayAdapter<String> adapterday = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,day);
        spinnerday.setAdapter(adapterday);

        spinnermonth = (Spinner) findViewById(R.id.spinner_month);
        final String[] mount = getResources().getStringArray(R.array.month_arrays);
        ArrayAdapter<String> adaptermount = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,mount);
        spinnermonth.setAdapter(adaptermount);

        spinneryear = (Spinner) findViewById(R.id.spinner_year);
        final String[] year = getResources().getStringArray(R.array.year_arrays);
        ArrayAdapter<String> adapteryear = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,year);
        spinneryear.setAdapter(adapteryear);

        ////////////////////////dath to day///////////////////



        spinnertoday = (Spinner) findViewById(R.id.spinner_today);
        final String[] today = getResources().getStringArray(R.array.day_arrays);
        ArrayAdapter<String> adaptertoday = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,today);
        spinnertoday.setAdapter(adaptertoday);

        spinnertomonth = (Spinner) findViewById(R.id.spinner_tomonth);
        final String[] tomount = getResources().getStringArray(R.array.month_arrays);
        ArrayAdapter<String> adaptertomonth = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,tomount);
        spinnertomonth.setAdapter(adaptertomonth);

        spinnertoyear = (Spinner) findViewById(R.id.spinner_toyear);
        final String[] toyear = getResources().getStringArray(R.array.year_arrays);
        ArrayAdapter<String> adaptertoyear = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,toyear);
        spinnertoyear.setAdapter(adaptertoyear);


       buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnDay = spinnerday.getSelectedItem().toString();
                spnMonth = spinnermonth.getSelectedItem().toString();
                spnYear = spinneryear.getSelectedItem().toString();
                spnToDay = spinnertoday.getSelectedItem().toString();
                SpnToMonth = spinnertomonth.getSelectedItem().toString();
                SpnToYear = spinnertoyear.getSelectedItem().toString();


               /////////// ต่อสติงวันที่เเล้ว
                date = spnYear+spnMonth+spnDay;
                todate = SpnToYear+SpnToMonth+spnToDay;
                /*Toast.makeText(User_Statistics.this, date, Toast.LENGTH_SHORT).show();
                Toast.makeText(User_Statistics.this, todate, Toast.LENGTH_SHORT).show();*/



            }
        });



    }


    public void findID(){

        buttonOk = (Button) findViewById(R.id.btn_Ok_mount);
        glass = (TextView) findViewById(R.id.user_glass);
        bottle = (TextView) findViewById(R.id.user_bottle);
        save_price= (TextView) findViewById(R.id.user_price);
        reduce_waste = (TextView) findViewById(R.id.user_reduce_west);
        reduce_co2 = (TextView) findViewById(R.id.user_co2);
    }


    private void click (){
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG,response.toString());
                    try {
                        JSONObject j= new JSONObject(response.toString());

                        glass.setText(j.getString("glass").toString());
                        bottle.setText(j.getString("bottle").toString());
                        save_price.setText(j.getString("paysave").toString());
                        reduce_waste.setText(j.getString("waste_number").toString());
                        prg.hide();

                      // re =  j.getString("bottle").toString();

                        //Toast.makeText(User_Statistics.this, re, Toast.LENGTH_SHORT).show();



                    } catch (JSONException e) {
                        e.printStackTrace();
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

        if (id == R.id.nav_Main) {
            // Handle the camera action
            Intent intent = new Intent(User_Statistics.this,User_Main.class);
            startActivity(intent);
        }else if (id == R.id.nav_camera){
            Intent intent = new Intent(User_Statistics.this,User_reduce.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(User_Statistics.this,User_Statistics.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(User_Statistics.this,Login.class);
            startActivity(intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
