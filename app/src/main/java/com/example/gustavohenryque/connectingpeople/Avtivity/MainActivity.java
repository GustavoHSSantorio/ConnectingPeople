package com.example.gustavohenryque.connectingpeople.Avtivity;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gustavohenryque.connectingpeople.Fragment.MapsFragment;
import com.example.gustavohenryque.connectingpeople.Fragment.TranslateFragment;
import com.example.gustavohenryque.connectingpeople.R;

public class MainActivity extends BaseActivityConnection
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private FragmentTransaction transaction;
    private TranslateFragment translate;
    private MapsFragment maps;
    private Integer actualId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.translate = new TranslateFragment();
        this.maps = new MapsFragment();

        this.transaction = getFragmentManager().beginTransaction();
        this.transaction.replace(R.id.main_content, translate);
        this.transaction.commit();

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        this.navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (this.actualId == null)
            this.actualId = id;

        if (id != this.actualId) {
            this.transaction = getFragmentManager().beginTransaction();
            switch (id) {
                case R.id.nav_camera:
                    this.transaction.replace(R.id.main_content, this.translate);
                    this.transaction.commit();
                    break;
                case R.id.nav_gallery:
                    Toast.makeText(this, "Ainda nao disponível", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_manage:
                    Toast.makeText(this, "Ainda nao disponível", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_send:
                    Toast.makeText(this, "Ainda nao disponível", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        this.actualId = id;
        return true;
    }

}
