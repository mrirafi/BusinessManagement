package com.dev.businessmanagement;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    NavigationView navigationView;
    TextView headerTitle, headerSubTitle;
    ImageView headerImage;
    View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        materialToolbar = findViewById(R.id.materialToolbar);
        navigationView = findViewById(R.id.navigationView);
        headerView = navigationView.getHeaderView(0);
        headerTitle = headerView.findViewById(R.id.headerTitle);
        headerSubTitle = headerView.findViewById(R.id.headerSubTitle);
        headerImage = headerView.findViewById(R.id.headerImage);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, materialToolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();

        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                if(item.getItemId() == R.id.share){
                    Toast.makeText(MainActivity.this, "Share", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.home){
                    //Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();
                    drawerLayout.close();
                } else if (item.getItemId() == R.id.profile){
                    ProfileFragment.WEB_URL = "";
                    //Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProfileFragment()).commit();
                    drawerLayout.close();
                }else if (item.getItemId() == R.id.gallery){
                    Toast.makeText(MainActivity.this, "Gallery", Toast.LENGTH_SHORT).show();
                    //drawerLayout.close();
                }else if (item.getItemId() == R.id.setting){
                    //Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();

                    drawerLayout.close();
                }

                return true;
            }
        });


    }
}