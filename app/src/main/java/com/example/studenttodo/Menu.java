package com.example.studenttodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;


public class Menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;

    //Service code
    MyService kmkService;
    boolean isBound = true;

    //Rest Api code
    RequestQueue requests;
    String url = "https://official-joke-api.appspot.com/jokes/random";
    TextView question;
    TextView answer;
    TextView date_fhr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Rest Api code
        question = (TextView) findViewById(R.id.question);
        answer = (TextView) findViewById(R.id.answer);
        date_fhr = (TextView) findViewById(R.id.date_fhr);
        requests = Volley.newRequestQueue(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HourRegistrationFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_hour_reg);
        }

        //Service code
        Intent i = new Intent(this, MyService.class);
        bindService(i, kmkConnection, Context.BIND_AUTO_CREATE);

    }

    public Menu() {
    }

    //Service code
    private final ServiceConnection kmkConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.MyLocalBinder binder = (MyService.MyLocalBinder) iBinder;
            kmkService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

    public void showMemeData(){
        String currentTime = kmkService.getCurrentTime();
        if(currentTime != null) {
            date_fhr = (TextView) findViewById(R.id.date_fhr);
            date_fhr.setText(currentTime);
            sendJsonRequest();
        }
    }

    public void sendJsonRequest(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String setup = response.getString("setup");
                    String punchline = response.getString("punchline");

                    question = findViewById(R.id.question);
                    answer = findViewById(R.id.answer);
                    question.setText(setup);
                    answer.setText(punchline);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requests.add(jsonObjectRequest);
    }

    public void removeData(){
        question = (TextView) findViewById(R.id.question);
        answer = (TextView) findViewById(R.id.answer);
        date_fhr = (TextView) findViewById(R.id.date_fhr);

        question.setText("");
        answer.setText("");
        date_fhr.setText("");
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_hour_reg:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HourRegistrationFragment()).commit();
                break;
            case R.id.nav_reg_view:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RegistrationViewFragment()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                break;
            case R.id.nav_logout:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AboutFragment()).commit();
                return true;
            case R.id.help:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HelpFragment()).commit();
                return true;
            case R.id.toolbaremoji:
                showMemeData();
                return true;
            case R.id.toolbarremoveicon:
                removeData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
