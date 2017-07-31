package com.example.boyvi.wastbank;

import android.content.Intent;
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

public class Admin_Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



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
