package kr.co.cleanbasket.cleanbasketdelivererandroid.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.fragment.MyOrderFragment;
import kr.co.cleanbasket.cleanbasketdelivererandroid.fragment.SearchOrderFragmnet;
import kr.co.cleanbasket.cleanbasketdelivererandroid.fragment.ViewAllOrderFragment;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.PdManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.SharedPreferenceBase;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Fragment fragment;
    private Boolean isManager;

    private String mMemberKey;
    final String mApiKey = "286f3e69922706fbdc907fc909760360ee60a037";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setBluetooth(true);
        PdManager.getInstance();
       // startCatchloc();

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
        isManager = SharedPreferenceBase.getSharedPreference("IsManager", false);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_view, fragment).commit();
    }

    public boolean setBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            return bluetoothAdapter.enable();
        }
        else if(!enable && isEnabled) {
            return bluetoothAdapter.disable();
        }
        // No need to change bluetooth state

        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, 1);

        return true;
    }
//
//    private void startCatchloc() {
//        mMemberKey = CatchlocSDK.getMemberKeyDefault(MainActivity.this);
//        Log.d("catchloc.sdk", "memberkey : " + mMemberKey);
//
//        class CatchLocAsyncTask extends AsyncTask<Void, Void, String[]> {
//            CatchLocResult catchlocResult = new CatchLocResult();
//
//            @Override
//            protected String[] doInBackground(Void... params) {
//                Log.d("catchloc.sdk", "CatchLocSDK doinBackground...");
//                catchlocResult = CatchlocSDK.startCatchLoc(MainActivity.this, mApiKey, mMemberKey);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(String[] result) {
//                Log.d("catchloc.sdk", "CatchLocSDK service starting...");
//                if (!catchlocResult.IsOk) {
//                    Toast toast = Toast.makeText(MainActivity.this, "CatchLocResult : " + catchlocResult.IsOk + "/" +
//                            catchlocResult.Message, Toast.LENGTH_LONG);
//                    toast.show();
//                }
//                CatchlocSDK.startLocationService(MainActivity.this, 1
//                );
//            }
//        }
//
//        CatchLocAsyncTask catchLocAsyncTask = new CatchLocAsyncTask();
//        catchLocAsyncTask.execute();
//        CatchlocLibs.setWifiEnableOption(this, 0);
//    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = new MyOrderFragment(this);

        int id = item.getItemId();

        switch (id) {
            case R.id.nav_my_order:
                fragment = new MyOrderFragment(this);
                toolbar.setTitle("내 주문");
                break;
            case R.id.nav_all_order:
                if (isManager) {
                    fragment = new ViewAllOrderFragment(this);
                    toolbar.setTitle("전체 주문");
                    break;
                }
            case R.id.nav_search_order:
                fragment = new SearchOrderFragmnet(this);
                toolbar.setTitle("주문 검색");
                break;
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_view, fragment).commit();

        drawer.closeDrawers();

        return true;
    }
}
