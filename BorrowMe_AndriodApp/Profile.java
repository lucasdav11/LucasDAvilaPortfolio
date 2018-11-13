package com.repo.borrowme.borrow_me;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Profile extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    GridView gridView;
    TextView userMainName,welcome, phoneNo, major;
    Button settingPage,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Grid
        gridView = (GridView) findViewById(R.id.activity_gridView);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(this);

        //Set information
        userMainName = (TextView) findViewById(R.id.userMainName);
        userMainName.setText(UserDetails.username);
        welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText("Welcome!");
        phoneNo = (TextView) findViewById(R.id.phoneNo);
        major = (TextView) findViewById(R.id.major);

        settingPage = (Button) findViewById(R.id.settingPage);
        /*setting button pressed*/
        settingPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, Setting.class));
            }
        });

        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, Login.class));
            }
        });
        //get some firebase data
        Firebase.setAndroidContext(this);
        StringRequest request = new StringRequest(Request.Method.GET, "https://borrow-me-ad389.firebaseio.com/users.json", new Response.Listener<String>(){
            @Override
            public void onResponse(String input) {
                try {
                    JSONObject obj1 = new JSONObject(input);
                    major.setText(obj1.getJSONObject(UserDetails.username).getJSONObject("info").getString("major"));
                    phoneNo.setText(obj1.getJSONObject(UserDetails.username).getJSONObject("info").getString("phoneNumber"));
                }catch (JSONException e) {e.printStackTrace();}
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(volleyError);
            }
        });/*End StringRequest Constructor Initialization*/
        RequestQueue rQueue = Volley.newRequestQueue(Profile.this);
        rQueue.add(request);
    }

    public void onResume(View view){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        /*do nothing on non-button clicks/ presses*/
    }

    @Override
    /*Switch to the page for whichever button is pressed*/
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()==R.id.activity_gridView){
            switch(i){
                case 0:
                    startActivity(new Intent(this,Profile.class));
                    finish();
                    break;
                case 1:
                    startActivity(new Intent(this,Search.class));
                    finish();
                    break;
                case 2:
                    startActivity(new Intent(this,AddBook.class));
                    finish();
                    break;
                case 3:
                    startActivity(new Intent(this,AddFriend.class));
                    finish();
                    break;
                case 4:
                    startActivity(new Intent(this,Users.class));
                    finish();
                    break;
            }
        }
    }
}
