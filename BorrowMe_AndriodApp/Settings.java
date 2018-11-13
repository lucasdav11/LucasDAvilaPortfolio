package com.repo.borrowme.borrow_me;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Setting extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    Firebase reference;
    DatabaseReference ref;
    GridView gridView;
    EditText phoneNo, major;
    Button update;
    String phone, maj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //Grid
        gridView = (GridView) findViewById(R.id.activity_gridView);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(this);
        //---

        //Set information
        phoneNo = (EditText) findViewById(R.id.phoneNo);
        major = (EditText) findViewById(R.id.major);
        update = (Button) findViewById(R.id.update);
        //Set information

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = phoneNo.getText().toString();
                maj = major.getText().toString();
                reference = new Firebase("https://borrow-me-ad389.firebaseio.com/users");
                reference.child(UserDetails.username).child("info").child("phoneNumber").setValue(phone);
                reference.child(UserDetails.username).child("info").child("major").setValue(maj);
                startActivity(new Intent(Setting.this, Profile.class));
            }
        });

        Firebase.setAndroidContext(this);

        ref = FirebaseDatabase.getInstance().getReference().child("users");


    }



    @Override
    public void onClick(View view) {

    }

    @Override
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
