package com.repo.borrowme.borrow_me;

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
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

public class Search extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    ArrayList<String> al = new ArrayList<>();
    GridView gridView;
    EditText isbn, bookName;
    Button searchBook;
    String sBookName, sISBN;
    ListView usersList;
    TextView noUsersText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Firebase.setAndroidContext(this);

        //Grid
        gridView = (GridView) findViewById(R.id.activity_gridView);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(this);
        noUsersText = (TextView)findViewById(R.id.noUsersText);

        //Setting new book views
        bookName = (EditText) findViewById(R.id.bookName);
        isbn = (EditText) findViewById(R.id.isbn);
        usersList = (ListView)findViewById(R.id.usersList);
        searchBook = (Button) findViewById(R.id.searchBook);

        searchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sBookName = bookName.getText().toString();
                sISBN = isbn.getText().toString();
                if (sBookName.length() > 0 || sISBN.length() > 0) {
                    searchBook();
                }

            }
        }); /*end button initialization*/
    }

    public void searchBook(){ //will get working for name first screw isbn for now
        String url = "https://borrow-me-ad389.firebaseio.com/users.json"; //all users
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            //response from firebase
            public void onResponse(String input) {
                try { //iterates each user and their book JSON for the searched book title
                    String keyName, keyBook, keyTitle;
                    JSONObject jsonNames = new JSONObject(input), jsonBooks, jsonTitles;
                    Iterator i = jsonNames.keys(), j, k, l;
                    al = new ArrayList<>();

                    while(i.hasNext()){ //go through each user
                        keyName = i.next().toString();

                        if(!keyName.equals(UserDetails.username)) { //don't search your own books
                            jsonBooks = new JSONObject(jsonNames.get(keyName).toString());
                            j = jsonBooks.keys();

                            while(j.hasNext()){ //find the book subcategory
                                keyBook = j.next().toString();
                                if (keyBook.equals("books")){
                                    jsonTitles = new JSONObject(jsonBooks.get(keyBook).toString());
                                    k = jsonTitles.keys();

                                    while(k.hasNext()) { //scan the owned books
                                        keyTitle = k.next().toString();
                                        if(sBookName.equals(keyTitle) || jsonTitles.getJSONObject(keyTitle).getString("ISBN").equals(sISBN)) {
                                            boolean borrowed = jsonTitles.getJSONObject(keyTitle).getBoolean("Rent");
                                            String display;
                                            if(borrowed){
                                                display = "Borrowed : Owned by   " + keyName;
                                            }
                                            else{
                                                display = "Unborrowed : Owned by " + keyName;
                                            }
                                            al.add(display);
                                            break;
                                        }
                                    }//end 3rd while
                                    break;
                                }//end if
                            }//end 2nd while
                        }//end if

                    }//*end 1st while
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(volleyError);
            }
        }); /*End StringRequest Constructor Initialization*/

        RequestQueue rQueue = Volley.newRequestQueue(Search.this);
        rQueue.add(request);/*add the request object to the Volley's queue to send request to firebase*/

        if(al.isEmpty()){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        /*Do nothing on non-button clicks*/
    }

    /*Switch to the page for whichever button is pressed*/
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
