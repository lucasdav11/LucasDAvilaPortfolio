package com.repo.borrowme.borrow_me;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Result extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    String key = "", key2 ="", key3="", key4="",key5="", key6="", key0= "";
    int totalUsers = 0;
    ProgressDialog pd;
    Firebase referenceUser, reference;
    Iterator i,j,k, a, b, c;
    HashSet<String> mySet = new HashSet<>();
    DatabaseReference ref;
    GridView gridView;
    TextView userMainName;
    EditText isbn, bookName;
    Button goBack;
    String bookN, isb;
    ListView listResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        usersList = (ListView)findViewById(R.id.usersList);
        noUsersText = (TextView)findViewById(R.id.noUsersText);
        goBack = (Button)findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Result.this, Search.class));
            }
        });
        //Grid
        gridView = (GridView) findViewById(R.id.activity_gridView);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(this);
        //---

        Firebase.setAndroidContext(this);

        pd = new ProgressDialog(Result.this);
        pd.setMessage("Loading...");
        pd.show();

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
        RequestQueue rQueue = Volley.newRequestQueue(Result.this);
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
        //Initialize A Current user query
        String searchKey = "";
        try {
            JSONObject u1 = new JSONObject(s);
            a = u1.keys();
            while(a.hasNext()){
                key4 = a.next().toString(); // Find users
                if(key4.equals(UserDetails.username)) {
                    try {
                        JSONObject u2 = new JSONObject(u1.get(key4).toString());
                        b = u2.keys();
                        while (b.hasNext()) {
                            key5 = b.next().toString();//Find querySearch
                            if (key5.equals("querySearchBook")) {
                                try {
                                    JSONObject u3 = new JSONObject(u2.get(key5).toString());
                                    c = u3.keys();
                                    while(c.hasNext()){
                                        key6 = c.next().toString(); //Find a single query
                                        searchKey = key6;
                                        reference.child(UserDetails.username).child("querySearchBook").removeValue(); // Remove after store key
                                        reference.child(UserDetails.username).child("querySearchISBN").removeValue();
                                        break;
                                    }
                                }catch(JSONException e){}
                            }
                        }
                    } catch (JSONException e) {
                    }
                }
            }
        }catch(JSONException e){}


        //Initialize A Current user query
        String searchISBN = "";
        try {
            JSONObject u1 = new JSONObject(s);
            a = u1.keys();
            while(a.hasNext()){
                key4 = a.next().toString(); // Find users
                if(key4.equals(UserDetails.username)) {
                    try {
                        JSONObject u2 = new JSONObject(u1.get(key4).toString());
                        b = u2.keys();
                        while (b.hasNext()) {
                            key5 = b.next().toString();//Find querySearch
                            if (key5.equals("querySearchISBN")) {
                                try {
                                    JSONObject u3 = new JSONObject(u2.get(key5).toString());
                                    c = u3.keys();
                                    while(c.hasNext()){
                                        key6 = c.next().toString(); //Find a single query
                                        searchISBN = key6;
                                        System.out.println("(*^&%*&^$*&^$*^%$*^%$^*$^%$&%^#&^%&^*%&*^"+key6);
                                        reference.child(UserDetails.username).child("querySearchBook").removeValue();
                                        reference.child(UserDetails.username).child("querySearchISBN").removeValue();
                                        break;
                                    }
                                }catch(JSONException e){}
                            }
                        }
                    } catch (JSONException e) {
                    }
                }
            }
        }catch(JSONException e){}



        System.out.println("SEARCH KEY: " + searchKey);
        System.out.println("SEARCH ISBN: " + searchISBN);
        //Match Key
        try {
            JSONObject obj = new JSONObject(s);
            i = obj.keys();
            while(i.hasNext()){
                key = i.next().toString();
                if(!key.equals(UserDetails.username)) {
                    try {
                        JSONObject xx = new JSONObject(obj.get(key).toString());
                        j = xx.keys();
                        while(j.hasNext()){
                            key2 = j.next().toString();
                            try{
                                JSONObject xxx = new JSONObject(xx.get(key2).toString());
                                k = xxx.keys();
                                while(k.hasNext()) {
                                    key0 = k.next().toString();
                                    System.out.println(">>>"+key0);
                                    System.out.println("KKKK: "+key0+">>"+xxx.getJSONObject(key0).getString("ISBN"));
                                    if(searchKey.equals(key0) ||
                                            (xxx.getJSONObject(key0).getString("ISBN").equals(searchISBN) && searchISBN.length()>0)) {
                                        System.out.println("********" + key0);
                                        al.add(key);
                                    }
                                }

                            }catch (JSONException e){}
                        }
                    }
                    catch(JSONException e){}


                }

                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        }
        pd.dismiss();
    }

}