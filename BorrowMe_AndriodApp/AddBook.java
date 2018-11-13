//AddBook.java
package com.repo.borrowme.borrow_me;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class AddBook extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    ArrayList<String> al = new ArrayList<>();
    Firebase referenceUser, reference;
    Iterator i;
    DatabaseReference ref;
    GridView gridView;
    EditText isbn, bookName;
    Button addBook;
    String bookN, isb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);
        //Grid
        gridView = (GridView) findViewById(R.id.activity_gridView);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(this);
        //---

        //Setting new book
        bookName = (EditText) findViewById(R.id.bookName);
        isbn = (EditText) findViewById(R.id.isbn);
        addBook = (Button) findViewById(R.id.addBook);
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookN = bookName.getText().toString();
                isb = isbn.getText().toString();
                reference = new Firebase("https://borrow-me-ad389.firebaseio.com/users");
                reference.child(UserDetails.username).child("books").child(bookN).child("ISBN").setValue(isb);
                reference.child(UserDetails.username).child("books").child(bookN).child("Rent").setValue(false);
                bookName.setText("");
                isbn.setText("");
                Toast.makeText(AddBook.this, "Book is added!", Toast.LENGTH_LONG).show();
            }
        });
        //---
        Firebase.setAndroidContext(this);


        ref = FirebaseDatabase.getInstance().getReference().child("users");

        reference = new Firebase("https://borrow-me-ad389.firebaseio.com/users");
        referenceUser = new Firebase("https://borrow-me-ad389.firebaseio.com/users/"+UserDetails.username+"/friends");
        String url = "https://borrow-me-ad389.firebaseio.com/users.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(AddBook.this);
        rQueue.add(request);

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
    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);
            i = obj.keys();

        }
        catch(JSONException e){

        }
    }

}