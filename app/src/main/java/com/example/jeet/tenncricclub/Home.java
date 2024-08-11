package com.example.jeet.tenncricclub;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    public static Context c;
    Profile_Fragment pf;
    ImageButton editdp;
    ImageView dp;
    String title, type;
    ClipData.Item participated;
    private BottomNavigationView bottomnavigationview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        c = getApplicationContext();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);
        Tournaments_Fragment tf = new Tournaments_Fragment();
        fragmentTransaction.replace(R.id.fragment_container, tf, "Home");
        fragmentTransaction.commit();


        bottomnavigationview = findViewById(R.id.bottomnavigationview);

        dp = findViewById(R.id.dp);


        bottomnavigationview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                  if (item.getItemId() == R.id.Tournaments) {
                    android.app.FragmentManager fragmentManager = getFragmentManager();
                    android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);
                    Tournaments_Fragment tf = new Tournaments_Fragment();
                    fragmentTransaction.replace(R.id.fragment_container, tf, "Gallery");
                    fragmentTransaction.commit();
                } else if (item.getItemId() == R.id.profile) {

                    android.app.FragmentManager fragmentManager = getFragmentManager();

                    android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);
                    Profile_Fragment pf = new Profile_Fragment();
                    fragmentTransaction.replace(R.id.fragment_container, pf, "Profile");
                    fragmentTransaction.commit();
                }
                else if (item.getItemId() == R.id.home) {
                    android.app.FragmentManager fragmentManager = getFragmentManager();
                    android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);
                    Home_Fragment hf = new Home_Fragment();
                    fragmentTransaction.replace(R.id.fragment_container, hf, "Home");
                    fragmentTransaction.commit();

                }else if (item.getItemId() == R.id.galley) {

                    android.app.FragmentManager fragmentManager = getFragmentManager();

                    android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);
                    Gallery_Fragment pf1 = new Gallery_Fragment();
                    fragmentTransaction.replace(R.id.fragment_container, pf1, "Gallery");
                    fragmentTransaction.commit();
                }
                return true;
            }
        });
        bottomnavigationview.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

                Fragment currentFragment = getFragmentManager().findFragmentById(R.id.fragment_container);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(currentFragment).attach(currentFragment).commit();

                Toast.makeText(Home.this, "Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                }).setNegativeButton("No", null).show();
    }

}
