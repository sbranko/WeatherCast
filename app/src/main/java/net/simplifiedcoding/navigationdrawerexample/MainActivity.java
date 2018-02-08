package net.simplifiedcoding.navigationdrawerexample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Handler handler;
    private UpdateTimer updateTimer;
    private static long UPDATE_EVERY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       /* View header=navigationView.getHeaderView(0);*/
                    /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
      /*  TextView navText = (TextView)header.findViewById(R.id.navText);
        MyBroadcastReceiver mybr = new MyBroadcastReceiver();
        System.out.println("test:"+mybr.getCity());
        navText.setText(mybr.getCity());*/

      /*  Intent intent = new Intent("MyCustomIntent");

      //  EditText et = (EditText)findViewById(R.id.extraIntent);
        // add data to the Intent
        intent.putExtra("message", (CharSequence)navText.getText().toString());
        intent.setAction("net.simplifiedcoding.navigationdrawerexample.A_CUSTOM_INTENT");
        sendBroadcast(intent);*/

        handler = new Handler();
        updateTimer = new UpdateTimer(this);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                handler.postDelayed(updateTimer, UPDATE_EVERY);
            }
        });
        // Moves the current Thread into the background
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        thread.currentThread();
        thread.start();

        //add this line to display menu1 when the activity is loaded
        displaySelectedScreen(R.id.nav_menu1);
    }



    @Override
    protected void onPostResume() {
        super.onPostResume();
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
        getMenuInflater().inflate(R.menu.main, menu);
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

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_menu1:
                fragment = new Menu1();


                Bundle args = new Bundle();
                args.putString("VALUE", "sf");
                fragment.setArguments(args);
                break;
            case R.id.nav_menu2:
                fragment = new Menu2();
                break;
            case R.id.nav_menu3:
                fragment = new Menu3();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }

    public void getNavView(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
                    /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        TextView navText = (TextView)header.findViewById(R.id.navText);
        TextView navTemp = (TextView)header.findViewById(R.id.navTemp);
        ImageView navIcon = (ImageView)header.findViewById(R.id.iv_weather_icon_header);


        MyBroadcastReceiver mybr = new MyBroadcastReceiver();
        CityWeatherModel cityWeatherModel = mybr.getCityWeatherMode();
        navText.setText(cityWeatherModel.getCityName());
        navTemp.setText(String.valueOf(Math.round(cityWeatherModel.getCurrentTemp()-273.15))+" °C");

        Glide.with(getApplicationContext())
                .load(cityWeatherModel.getWeatherIcon())
                .into(navIcon);


    }

    // NOTE could be an anonymous inner class - easier to understand this way
    class UpdateTimer implements Runnable {

        Activity activity;

        public UpdateTimer(Activity activity) {
            this.activity = activity;
        }

        /**
         * Updates the counter display and vibrated if needed
         */
        @Override
        public void run() {
            //Log.d(CLASS_NAME, display);

            getNavView();

            if (handler != null) {
                handler.postDelayed(this, UPDATE_EVERY);
            }
        }
    }

    }
