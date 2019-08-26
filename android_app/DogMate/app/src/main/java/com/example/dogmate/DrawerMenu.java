package com.example.dogmate;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.dogmate.Add_Location.AddLocation;
import com.example.dogmate.Add_Review.AddReview;
import com.example.dogmate.Show_Location.ShowLocations;
import com.google.android.material.navigation.NavigationView;

public class DrawerMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_add_location);

        }
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent nextActivity;
        switch (id){
            case R.id.nav_add_location:
                navigationView.setCheckedItem(R.id.nav_add_location);
                nextActivity = new Intent(DrawerMenu.this, AddLocation.class);
                startActivity(nextActivity);
                break;
            case R.id.nav_show_location:
                navigationView.setCheckedItem(R.id.nav_show_location);
                nextActivity = new Intent(DrawerMenu.this, ShowLocations.class);
                startActivity(nextActivity);
                break;
            case R.id.nav_review:
                navigationView.setCheckedItem(R.id.nav_review);
                nextActivity = new Intent(DrawerMenu.this, AddReview.class);
                startActivity(nextActivity);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
