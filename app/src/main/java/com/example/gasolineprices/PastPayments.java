package com.example.gasolineprices;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
//import static com.example.gasolineprices.json.readJsonFromUrl;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import org.json.*;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gasolineprices.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.*;
public class PastPayments extends AppCompatActivity  {
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    ActivityMainBinding binding;
    GridView gridView;
    Button goCart,goPast;
    List<cartItem> cartList;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pastpayment);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        Bundle extras = getIntent().getExtras();
        String mail="";
        if (extras != null) {
            mail = extras.getString("mail");}

getFuelData();
    }


    private List<List<cartItem>> getFuelData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        DatabaseReference ref = database.getReference(firebaseUser.getEmail().split("@", 2)[0]).child("Payments");
        List<List<cartItem>> newListList= new ArrayList<>();
List<String> mCodes=new ArrayList<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int index = 0;
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    newListList.add(new ArrayList<>());
mCodes.add(postSnapshot.getKey());
                    for (DataSnapshot postSnapshot2: postSnapshot.getChildren()) {
                        cartItem value = postSnapshot2.getValue(cartItem.class);
                        newListList.get(index).add(value);
                    }
                    index++;
                }
                RecyclerView myrv=(RecyclerView)findViewById(R.id.rectyy);
                PastPaymentAdapter myRecyclerViewAdapter=new PastPaymentAdapter(PastPayments.this,newListList,mCodes);
                myrv.setLayoutManager(new GridLayoutManager(PastPayments.this,1));
                myrv.setAdapter(myRecyclerViewAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return newListList;
    }
    private List<String> getCode() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        DatabaseReference ref = database.getReference(firebaseUser.getEmail().split("@", 2)[0]).child("Payments");
        // calling add value event listener method
        // for getting the values from database.

        List<String> newList = null;
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.


                for (DataSnapshot postSnapshot: snapshot.getChildren()) {

                    newList.add(postSnapshot.getKey());




                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return newList;
    }

    private void getdata(String mail) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(mail);
        // calling add value event listener method
        // for getting the values from database.
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot: snapshot.child("Payments").getChildren()) {
                cartItem value = postSnapshot.getValue(cartItem.class);

                    cartList.add(value);
                }
        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PastPayments.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }});}



    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);

            JSONArray array = new JSONArray(jsonText);


            return array;
        } finally {
            is.close();
        }
    }
}