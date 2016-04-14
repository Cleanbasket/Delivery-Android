package kr.co.cleanbasket.cleanbasketdelivererandroid.activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.fragment.MyOrderFragment;
import kr.co.cleanbasket.cleanbasketdelivererandroid.fragment.ViewAllOrderFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");

        // set DrawerLayout
        drawer = (DrawerLayout) findViewById(R.id.drawer);

        // set NavigationView
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Set ActionBar
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.open, R.string.close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_my_order);

        fragment = new MyOrderFragment(this);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_view, fragment).commit();
    }

    private void drawUserProfile(String UserID){

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = new MyOrderFragment(this);

        int id = item.getItemId();

        switch (id){
            case R.id.nav_my_order:
                fragment = new MyOrderFragment(this);
                toolbar.setTitle("내 주문");
                break;
            case R.id.nav_all_order:
                fragment = new ViewAllOrderFragment(this);
                toolbar.setTitle("전체 주문");
                break;
            case R.id.nav_pickup:
//                fragment = new MyOrderFragment();
//                setTitle("내 주문");
                break;
            case R.id.nav_deliver:
//                fragment = new MyOrderFragment();
//                setTitle("내 주문");
                break;
            case R.id.nav_notice:
//                fragment = new MyOrderFragment();
//                setTitle("내 주문");
                break;
            case R.id.nav_settings:
//                fragment = new MyOrderFragment();
//                setTitle("내 주문");
                break;
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_view, fragment).commit();

        drawer.closeDrawers();

        return true;
    }
}
