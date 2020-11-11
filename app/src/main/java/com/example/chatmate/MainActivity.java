package com.example.chatmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAuth mAuth;

    private ViewPager viewPager;
    private PageTabAdapter mainTabAdaptar;

    private TabLayout tabLayoutHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.toolbarMainPage);


        mAuth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ChatMate");

        //working with tab layout
        viewPager=(ViewPager)findViewById(R.id.tabPager);
        tabLayoutHome=(TabLayout)findViewById(R.id.tabLayout1);
        mainTabAdaptar=new PageTabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainTabAdaptar);
        tabLayoutHome.setupWithViewPager(viewPager);
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            Intent startIntent= new Intent(MainActivity.this,StartActivity.class);
            startActivity(startIntent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.main_menu_logout_button){
            FirebaseAuth.getInstance().signOut();
            Intent logoutIntent=new Intent(MainActivity.this,StartActivity.class);
            startActivity(logoutIntent);
            finish();
        }
        if(item.getItemId()==R.id.main_menu_settings){
            Intent settingsIntent=new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(settingsIntent);
        }
        return true;
    }
}