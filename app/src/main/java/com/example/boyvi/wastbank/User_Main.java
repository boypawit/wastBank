package com.example.boyvi.wastbank;

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
import android.view.Menu;
import android.view.MenuItem;

public class User_Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        aleatDetail();


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_toolbars);
        getSupportActionBar().setTitle("24Hrs Waste Bank");



         /*
        ////////Zoom picture///////////
        int i=500;
        int j = 500;
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
