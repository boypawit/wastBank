package com.example.boyvi.wastbank;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
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
import java.util.HashMap;
import java.util.Map;

public class Admin_Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences share ;
    SharedPreferences shareimage ;
    SharedPreferences.Editor editorimage;
    TextView name;

    private ProgressDialog prg ;
    private TextView glass,bottle,paysave;
    private String id ;

   // private String URL = "https://jirayuhe57.000webhostapp.com/android/admin_showall_stat_main.php";
   private static final String URL = "http://wastebank.ilab-ubu.net/android/admin_showall_stat_main.php";
    private String UrlPicture;
    private String jsonimageprofile;
    ImageView imageProfile;

    private String PrivilageProfile ;

    private String TAG = Admin_Main.class.getSimpleName();
    public void findID(){
        glass =  (TextView) this.findViewById(R.id.glass);
        bottle= (TextView) this.findViewById(R.id.bottle);
        paysave = (TextView) this.findViewById(R.id.paysave);
        //imageProfile = (ImageView) this.findViewById(R.id.imageAdmin);

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

                        PrivilageProfile = j.getString("Privilege");
                        jsonimageprofile = j.getString("pic_profi");
                            if (jsonimageprofile!="") {
                                     loadimage();//////imafeView
                            }else {
                                loadimageNullProfile();
                            }

                        loadimageNullProfile();
                        switch(status_String) {
                            case "OK" :
                            prg.hide();
                            glass.setText(j.getString("glass"));
                            bottle.setText(j.getString("bottle"));
                            paysave.setText(j.getString("paysave"));

                            break ;
                            default:
                                Toast.makeText(Admin_Main.this,"ไม่สามารถโหลดข้อมูลการใช้งานได้",Toast.LENGTH_SHORT).show();
                                break;

                        }
                        // String status = j.getString("result");
                        // String Privilege = j.getString("Privilege");
                        // Toast.makeText(Login.this, status, Toast.LENGTH_SHORT).show();

                    }catch (JSONException e){
                        prg.hide();
                        //  textviewShow.setText(e.getMessage());
                        //  Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        Toast.makeText(Admin_Main.this, e.toString(), Toast.LENGTH_SHORT).show();
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


    private void loadimage(){
      //  UrlPicture = "https://jirayuhe57.000webhostapp.com/android/images/"+jsonimageprofile;
       switch (PrivilageProfile){
           case "0": UrlPicture = "http://wastebank.ilab-ubu.net/profile/personal/"+jsonimageprofile; break;
           case "1": UrlPicture = "http://wastebank.ilab-ubu.net/profile/personal/"+jsonimageprofile; break;
           case "2": UrlPicture = "http://wastebank.ilab-ubu.net/profile/student/"+jsonimageprofile;  break;
       }
        // UrlPicture = "http://wastebank.ilab-ubu.net/profile/null.jpg";
        ImageRequest imageRequest = new ImageRequest(UrlPicture,new Response.Listener<Bitmap>(){
            @Override
            public void onResponse(Bitmap bitmap) {
              imageProfile.setImageBitmap(bitmap);
               /* shareimage = getSharedPreferences("imageprofile", Context.MODE_PRIVATE);
                editorimage = share.edit();
*/
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                byte[] b = baos.toByteArray();

                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                //Toast.makeText(getApplicationContext(),encodedImage.toString(),Toast.LENGTH_LONG).show();
               // textEncode.setText(encodedImage);

                shareimage = getSharedPreferences("imageprofile", Context.MODE_PRIVATE);
                editorimage = shareimage.edit();
                editorimage.putString("image_data",encodedImage);
                editorimage.commit();
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

    private void loadimageNullProfile(){
        UrlPicture = "http://wastebank.ilab-ubu.net/profile/null.jpg";
        // UrlPicture = "http://wastebank.ilab-ubu.net/profile/null.jpg";
        ImageRequest imageRequest = new ImageRequest(UrlPicture,new Response.Listener<Bitmap>(){
            @Override
            public void onResponse(Bitmap bitmap) {


                imageProfile.setImageBitmap(bitmap);
               /* shareimage = getSharedPreferences("imageprofile", Context.MODE_PRIVATE);
                editorimage = share.edit();
*/
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                byte[] b = baos.toByteArray();

                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                //Toast.makeText(getApplicationContext(),encodedImage.toString(),Toast.LENGTH_LONG).show();
                // textEncode.setText(encodedImage);

          /*      shareimage = getSharedPreferences("imageprofile", Context.MODE_PRIVATE);
                editorimage = shareimage.edit();
                editorimage.putString("image_data",encodedImage);
                editorimage.commit();*/
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        aleatDetail();


        share = getSharedPreferences("PrefWasteBank", Context.MODE_PRIVATE);
        id = share.getString("id","no") ;
        findID();
        loadData();
        prg = new ProgressDialog(Admin_Main.this);
        prg.setMessage("รอสักครู่...");
        prg.setCancelable(false);
        prg.show();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main_admin);
        TextView name = (TextView) headerView.findViewById(R.id.nav_name);
        imageProfile = (ImageView) headerView.findViewById(R.id.pic);

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
        // Handle navigation view item clicks here.nav_main
        int id = item.getItemId();

        if (id == R.id.nav_index) {
            Intent intent = new Intent(Admin_Main.this,Admin_Main.class);
            startActivity(intent);

        }else if (id == R.id.nav_reduce) {
            Intent intent = new Intent(Admin_Main.this,Adnin_Reduce.class);
            startActivity(intent);

        } else if (id == R.id.nav_staticyou) {
            Intent intent = new Intent(Admin_Main.this,Admin_StatisticsYou.class);
            startActivity(intent);

        } else if (id == R.id.nav_staticall) {
            Intent intent = new Intent(Admin_Main.this,Admin_StatisticsAll.class);
            startActivity(intent);
        } else if (id == R.id.nav_search) {
            Intent intent = new Intent(Admin_Main.this,Admin_Search.class);
            startActivity(intent);
        }else if (id == R.id.nav_logout) {
            Intent intent = new Intent(Admin_Main.this,Login.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void aleatDetail(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Main.this);
        builder.setTitle("                      รู้หรือไม่?");
        builder.setMessage("\nขวด 1 ขวด ลดคาร์บอนไดออกไซด์ได้ 10 กรัม\n" +
                "\nแก้ว 1 ใบ  ลดคาร์บอนไดออกไซด์ได้ 10 กรัม");
        builder.setNegativeButton("ตกลง", null);
        builder.show();


    }
}
