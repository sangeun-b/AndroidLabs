package com.example.androidlabs1;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class TestToolbar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar tBar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(tBar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        //Look at your menu XML file. Put a case for every id in that file:
        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.transItem:
                message = "You clicked on the translate";
                break;
            case R.id.photoItem:
                message = "You clicked on the search";
                break;
            case R.id.searchItem:
                message = "You clicked on help";
                break;
            case R.id.textItem:
                message = "You clicked on the overflow menu";
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        String message = null;

        switch(item.getItemId())
        {
            case R.id.chatPage:
                Intent goChatPage = new Intent(TestToolbar.this, ChatRoomActivity.class);
                startActivity(goChatPage);
                break;
            case R.id.weatherPage:
                Intent goWeatherPage = new Intent(TestToolbar.this, WeatherForecast.class);
                startActivity(goWeatherPage);
                break;
            case R.id.loginPage:
                Intent goLoginPage = new Intent(TestToolbar.this, MainActivity.class);
                setResult(500);
                finish();
                //startActivity(goLoginPage);
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        //Toast.makeText(this, "NavigationDrawer: " + message, Toast.LENGTH_LONG).show();
        return false;
    }

}
