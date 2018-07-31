package com.kentux.alertiumtracker;

import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kentux.alertiumtracker.fragments.AlertsFragment;
import com.kentux.alertiumtracker.fragments.CetusCycleFragment;
import com.kentux.alertiumtracker.fragments.InvasionsFragment;
import com.kentux.alertiumtracker.fragments.NewsFragment;
import com.kentux.alertiumtracker.fragments.SortiesFragment;
import com.kentux.alertiumtracker.fragments.SyndicateMissionsFragment;
import com.kentux.alertiumtracker.fragments.VoidFissuresFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NewsFragment.OnFragmentInteractionListener,
        AlertsFragment.OnFragmentInteractionListener,
        InvasionsFragment.OnFragmentInteractionListener,
        CetusCycleFragment.OnFragmentInteractionListener,
        SortiesFragment.OnFragmentInteractionListener,
        SyndicateMissionsFragment.OnFragmentInteractionListener,
        VoidFissuresFragment.OnFragmentInteractionListener {

    @Nullable
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    String myString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        //DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.primaryTextColor)));
        navigationView.setCheckedItem(R.id.nav_news);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_fragment, new NewsFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        Fragment fragment = null;
        Class fragmentClass;
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_news:
                fragmentClass = NewsFragment.class;
                break;
            case R.id.nav_alerts:
                fragmentClass = AlertsFragment.class;
                break;
            case R.id.nav_invasions:
                fragmentClass = InvasionsFragment.class;
                break;
            case R.id.nav_syndicate:
                fragmentClass = SyndicateMissionsFragment.class;
                break;
            case R.id.nav_void_fissures:
                fragmentClass = VoidFissuresFragment.class;
                break;
            case R.id.nav_sorties:
                fragmentClass = SortiesFragment.class;
                break;
            case R.id.nav_cetus_cycle:
                fragmentClass = CetusCycleFragment.class;
                break;
            default:
                fragmentClass = NewsFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_fragment, fragment).commit();

        item.setChecked(true);
        drawer.closeDrawers();
        return true;
    }

    @Override
    public void onFragmentInteraction(String string) {
        this.myString = string;
    }
}
