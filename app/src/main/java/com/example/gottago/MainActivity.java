package com.example.gottago;

import android.annotation.SuppressLint;
import android.app.AlertDialog;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.example.gottago.Announces.AnnouncementFragment;
import com.example.gottago.Announces.UploadFragment;
import com.example.gottago.FirebaseAuth.LoginActivity;
import com.example.gottago.NavigationDrawer.AboutFragment;
import com.example.gottago.NavigationDrawer.HomeFragment;
import com.example.gottago.NavigationDrawer.SettingsFragment;
import com.example.gottago.SharedPrefs.SharedPrefsUtils;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private FirebaseAuth mAuth;
    private TextView navUsername;

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get an instance of the support action bar
        ActionBar actionBar = getSupportActionBar();

        // Enable the back arrow
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        SetSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Find the TextView that displays the user's name in the header layout
        View headerView = navigationView.getHeaderView(0);
        navUsername = headerView.findViewById(R.id.username);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        // listen for drawer state changes
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                // perform action when drawer is closed
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        // Hide certain drawer items when user is not logged in
        if (SharedPrefsUtils.getUid(this) == null) {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_announces).setVisible(false);
            menu.findItem(R.id.nav_login).setVisible(true);
            headerView.findViewById(R.id.usernameLogged).setVisibility(View.GONE);
            headerView.findViewById(R.id.usernameAnonim).setVisibility(View.VISIBLE);
        }
        // Show certain drawer items when user is logged in
        else {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.nav_login).setVisible(false);
            menu.findItem(R.id.nav_logout).setVisible(true);
            menu.findItem(R.id.nav_announces).setVisible(true);
            headerView.findViewById(R.id.usernameLogged).setVisibility(View.VISIBLE);
            headerView.findViewById(R.id.usernameAnonim).setVisibility(View.GONE);
        }

        // Set the user's name in the drawer
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            String[] parts = email.split("@");
            String username = parts[0];
            navUsername.setText(username);
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle the click event of the back arrow
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;


            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                break;


            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
                break;

            case R.id.nav_announces:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AnnouncementFragment()).commit();
                break;

            case R.id.nav_createAnnounce:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UploadFragment()).commit();
                break;

            case R.id.nav_login:
                Intent new3Intent = new Intent(this, LoginActivity.class);
                startActivity(new3Intent);
                finish();
                break;

            case R.id.nav_logout:
                showLogoutConfirmationDialog();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void SetSupportActionBar(Toolbar toolbar) {
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Logout the user
                mAuth.signOut();

                // Clear saved UID from SharedPreferences
                SharedPrefsUtils.clearUid(MainActivity.this);

                // Start the LoginActivity and clear back stack
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                Utility.showToast(MainActivity.this, "Logout Successful");


                // Reload the app
//                Intent intent = getIntent();
//                finish();
//                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Dismiss the dialog
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}