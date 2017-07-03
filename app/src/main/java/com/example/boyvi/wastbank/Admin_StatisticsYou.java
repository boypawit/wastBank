package com.example.boyvi.wastbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Admin_StatisticsYou extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Spinner spinnerday, spinnermonth,spinneryear;
    Spinner spinnertoday, spinnertomonth,spinnertoyear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_admin4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_index) {
            Intent intent = new Intent(Admin_StatisticsYou.this,Admin_Main.class);
            startActivity(intent);

        }else if (id == R.id.nav_reduce) {
            Intent intent = new Intent(Admin_StatisticsYou.this,Adnin_Reduce.class);
            startActivity(intent);

        } else if (id == R.id.nav_staticyou) {
            Intent intent = new Intent(Admin_StatisticsYou.this,Admin_StatisticsYou.class);
            startActivity(intent);

        } else if (id == R.id.nav_staticall) {
            Intent intent = new Intent(Admin_StatisticsYou.this,Admin_StatisticsAll.class);
            startActivity(intent);
        } else if (id == R.id.nav_search) {
            Intent intent = new Intent(Admin_StatisticsYou.this,Admin_Search.class);
            startActivity(intent);
        }else if (id == R.id.nav_logout) {
            Intent intent = new Intent(Admin_StatisticsYou.this,Login.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
