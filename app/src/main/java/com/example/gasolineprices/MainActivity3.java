package com.example.gasolineprices;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Normalizer;
import java.util.List;
import java.util.Scanner;
//import static com.example.gasolineprices.json.readJsonFromUrl;
import java.lang.Object;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import org.json.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gasolineprices.databinding.ActivityMainBinding;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity3 extends AppCompatActivity implements CartAdapter.ICart {
List<cartItem> cartList;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
loadData();






                            RecyclerView myrv=(RecyclerView)findViewById(R.id.gridView);
                            CartAdapter myRecyclerViewAdapter=new CartAdapter(MainActivity3.this,cartList);
                            myrv.setLayoutManager(new GridLayoutManager(MainActivity3.this,1));
                            myrv.setAdapter(myRecyclerViewAdapter);
        double para=0;
                            for (int i = 0; i < cartList.size(); i++) {
                      para+=(cartList.get(i).Price*cartList.get(i).Count);
        }
        TextView Pricee=(TextView) findViewById(R.id.totalPrice);
                            Pricee.setText(para+"");
Button Payment=(Button)findViewById(R.id.Payment);
        Payment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                // Initialize firebase user
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference(firebaseUser.getEmail().split("@", 2)[0]).child("Payments").child(randomName());



                ref.setValue(cartList);
                cartList.clear();
saveData();
          ref = database.getReference(firebaseUser.getEmail().split("@", 2)[0]).child("Cart");
ref.removeValue();

                Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                startActivity(intent);
            }
        });






        androidx.lifecycle.Observer<Double> liveDataObserver=new androidx.lifecycle.Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double aDouble) {
                if(Pricee.getText().equals("0.0")) {
                    aDouble=0.0;
                    for (int i = 0; i < cartList.size(); i++) {

                        aDouble += (cartList.get(i).Price * cartList.get(i).Count);
                    }
                    Pricee.setText(aDouble + "");
                }else{
                        TextView Pricee = (TextView) findViewById(R.id.totalPrice);
                        Pricee.setText(aDouble + "");
                    }
                }

        };
        para=0;
        if(Pricee.getText().equals("0.0")) {
            for (int i = 0; i < cartList.size(); i++) {
                para += (cartList.get(i).Price * cartList.get(i).Count);
            }

            Pricee.setText(para + "");
        }

        CartAdapter.getLiveData().observe(this, (androidx.lifecycle.Observer<Double>) liveDataObserver);

if(Pricee.getText().equals("0.0")) {
    for (int i = 0; i < cartList.size(); i++) {
        para += (cartList.get(i).Price * cartList.get(i).Count);
    }

    Pricee.setText(para + "");
}








    }






    public String randomName(){


        // create a string of all characters
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // specify length of random string
        int length = 7;

        for(int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphabet.length());

            // get character specified by index
            // from the string
            char randomChar = alphabet.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }

        String randomString = sb.toString();
return randomString;
    }


    private void saveData() {
        SharedPreferences sharedPreferences = MainActivity3.this.getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartList);
        editor.putString("task list", json);
        editor.apply();





    }

    private void getdata() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        DatabaseReference ref = database.getReference(firebaseUser.getEmail().split("@", 2)[0]).child("Cart").child("0");
        // for getting the values from database.
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    cartItem value = snapshot.getValue(cartItem.class);
                    cartList.add(value);}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(MainActivity3.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });}


    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<cartItem>>() {}.getType();
        cartList = gson.fromJson(json, type);

        if (cartList == null) {
            cartList = new ArrayList<>();
            getdata();
        }

//


    }

    @Override
    public void update(List<cartItem> cartList) {
        loadData();
        RecyclerView myrv=(RecyclerView)findViewById(R.id.gridView);
        CartAdapter myRecyclerViewAdapter=new CartAdapter(MainActivity3.this,cartList);
        myrv.setLayoutManager(new GridLayoutManager(MainActivity3.this,1));
        myrv.setAdapter(myRecyclerViewAdapter);
    }
}