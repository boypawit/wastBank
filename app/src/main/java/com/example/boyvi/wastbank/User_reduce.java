package com.example.boyvi.wastbank;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.internal.NavigationMenuPresenter;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class User_reduce extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String Pref = "PrefWasteBank";
    SharedPreferences shareimage ;
    private String sharepicture;
    ProgressDialog prg ;
    private String TAG = User_reduce.class.getSimpleName();
    private  String URL =  "http://wastebank.ilab-ubu.net/android/use_reduce.php";
    private ImageView ImgGlass , ImgBottle ;
    private String userID,paysave ;
    private String type = "0";
    private EditText price;

    private String status_String = "";
    SharedPreferences share ;
    SharedPreferences.Editor editor;
    private ImageView noimage;

    private Button button;
    private String encode_string="",image_name;
    Bitmap bitmap;
    File file;
    Uri file_uri;

    private ImageView Demo_button;
   private ImageView second_button;

    private TextView textView;
    private static int REQUEST_IMAGE_CAPTURE=1;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(User_reduce activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    
    public void findID(){
            price = (EditText) findViewById(R.id.editText5);
        noimage = (ImageView) findViewById(R.id.noImageView);

    }

    private void click (){

        if (price.getText().toString()!="") {
            paysave = price.getText().toString();
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
                        encode_string="";
                        noimage.setImageResource(R.drawable.noimage);
                        second_button.setImageResource(R.drawable.bottle1);
                        Demo_button.setImageResource(R.drawable.glass2);
                        price.setText("");

                        JSONObject j= new JSONObject(response.toString());
                        //   JSONObject j = response.getJSONObject("dataUser");
                        //  String userID = j.getString("id");
                        status_String = j.getString("result");
                      //  int status = Integer.parseInt(status_String);
                      //  int status = 0;

                        // String status = j.getString("result");
                        // String Privilege = j.getString("Privilege");
                        // Toast.makeText(Login.this, status, oast.LENGTH_SHORT).show();
                        //Toast.makeText(User_reduce.this, status_String, Toast.LENGTH_SHORT).show();//
                        // if(status_String == "OK") {
                          //  Toast.makeText(User_reduce.this, "111", Toast.LENGTH_SHORT).show();
                         /*   share = getSharedPreferences(Pref, Context.MODE_PRIVATE);
                            editor = share.edit();
                            editor.putString("glass",j.getString("glass"));
                            editor.putString("bottle",j.getString("bottle"));
                            editor.putString("paysave",j.getString("paysave"));
                       
                            editor.commit();*/
                         
                         /*   prg.hide();
                            Toast.makeText(getApplicationContext(),
                                    "บันทึกเสร็จสิ้น", Toast.LENGTH_LONG).show();
                        }*/
                         switch (status_String) {
                             case "OK" : prg.hide();
                                 Toast.makeText(getApplicationContext(),
                                         "บันทึกเสร็จสิ้น", Toast.LENGTH_LONG).show();
                                 break;
                             case "NO" : prg.hide();
                                 Toast.makeText(getApplicationContext(),
                                         "ไม่สามารถบันทึกได้", Toast.LENGTH_LONG).show();
                             default: Toast.makeText(getApplicationContext(),
                                     "มีปัญหาบางอย่าง!!! ไม่สามารถบันทึกได้", Toast.LENGTH_LONG).show();
                                 prg.hide();
                                 break;
                         }
                    }catch (JSONException e){
                        prg.hide();
                        //  textviewShow.setText(e.getMessage());
                        //  Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        Toast.makeText(User_reduce.this, e.toString(), Toast.LENGTH_LONG).show();
                        Log.d("Whats wrong?", e.toString());
                        Log.e("JSON Parser", "Error parsing data " + e.toString());

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG,error.toString());
                    //  textviewShow.setText("Error");
                    Toast.makeText(User_reduce.this, error.toString(), Toast.LENGTH_LONG).show();
                    prg.hide();
                }
            }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("userID",userID);
                    params.put("type",type);
                    params.put("price",paysave);
                    if(encode_string!="") {
                        params.put("encoded_string", encode_string);
                    }
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

        verifyStoragePermissions(this);

        share = getSharedPreferences("PrefWasteBank",Context.MODE_PRIVATE);
        userID = share.getString("id","No value") ;


        shareimage = getSharedPreferences("imageprofile", Context.MODE_PRIVATE);
        sharepicture = shareimage.getString("image_data","");


        findID();
        //noimage.setRotation(0);

        ///////Edit by pawit//////////

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main2);
        TextView name = (TextView) headerView.findViewById(R.id.nav_name);

        ImageView image =(ImageView) headerView.findViewById(R.id.imageProfileUser);

        if( !sharepicture.equalsIgnoreCase("")){
            byte[] b = Base64.decode(sharepicture, Base64.DEFAULT);
            Bitmap bitm = BitmapFactory.decodeByteArray(b, 0, b.length);
            image.setImageBitmap(bitm);
        }


        name.setText(share.getString("name","No"));
        navigationView.setNavigationItemSelectedListener(this);

        ImageButton buttoncamera = (ImageButton) findViewById(R.id.imageButton2);
        buttoncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                image_name = "picture.jpg";
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+
                        File.separator+image_name);
                file_uri = Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,file_uri);
                startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);///error api 24 REQUEST_IMAGE_CAPTURE

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

                        if(type != "0"){
                            click();
                            /*if (encode_string!=""){
                                makeRequest();
                            }*/

                       }
                        else{
                            Toast.makeText(getApplicationContext(),"กรุณาเลือกชนิดของขยะ",Toast.LENGTH_SHORT).show();
                            prg.hide();
                        }
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



        Demo_button = (ImageView )findViewById(R.id.imgGlass);
        second_button = (ImageView )findViewById(R.id.imgBut);

// when you click this demo button
                Demo_button.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        Demo_button.setImageResource(R.drawable.glass_stroke1);
                        second_button.setImageResource(R.drawable.bottle1);
                        type= "1";
                                           }
                                       });

                second_button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Demo_button.setImageResource(R.drawable.glass2);
                        second_button.setImageResource(R.drawable.bottle_stroke);

                        type = "2";
                    }
                                                 });
        //////////////end////////////////


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==REQUEST_IMAGE_CAPTURE&&resultCode==RESULT_OK) {

            bitmap = BitmapFactory.decodeFile(file_uri.getPath());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,10,stream);

            noimage.setImageBitmap(bitmap);
            byte[] array = stream.toByteArray();
            encode_string = Base64.encodeToString(array,0);
        }
    }

/////////////// photorequestTest////////////////
   /* private void makeRequest(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,
                "https://boyvinai.000webhostapp.com/connection.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("encoded_string",encode_string);
                map.put("image_name",image_name);
                return map;
            }
        };
        requestQueue.add(request);

    }*/

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
