package com.example.boyvi.wastbank;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class User_reduce extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




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
