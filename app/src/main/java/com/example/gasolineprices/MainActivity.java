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

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import org.json.*;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.gasolineprices.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
public class MainActivity extends AppCompatActivity  {
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    ActivityMainBinding binding;
    GridView gridView;
    Button goCart,goPast,signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        // Initialize firebase auth
        firebaseAuth= FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();



        // Initialize sign in client
        googleSignInClient= GoogleSignIn.getClient(MainActivity.this
                , GoogleSignInOptions.DEFAULT_SIGN_IN);


        signOut=(Button) findViewById(R.id.signout);









        List<String> CityNames = new ArrayList<String>();
        JSONArray json;
        GridView gridView;
        try {
            json = readJsonFromUrl("https://gist.githubusercontent.com/ozdemirburak/4821a26db048cc0972c1beee48a408de/raw/4754e5f9d09dade2e6c461d7e960e13ef38eaa88/cities_of_turkey.json");
            for (int i = 0; i < 81; i++) {
                JSONObject object = json.getJSONObject(i);
                CityNames.add(object.getString("name"));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        gridView = (GridView) MainActivity.this.findViewById(R.id.gridView);

        grid_adapter gridAdapter = new grid_adapter(MainActivity.this, CityNames);
        gridView.setAdapter(gridAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this, "You Clicked on " + CityNames.get(position), Toast.LENGTH_SHORT).show();
                String value=CityNames.get(position);
                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                i.putExtra("key",value);
                startActivity(i);
            }
        });

        signOut.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity.this, ActivityLogin.class);
                startActivity(i);
                finish();
            }
        });




goCart=(Button)MainActivity.this.findViewById(R.id.goCart);
goPast=(Button)MainActivity.this.findViewById(R.id.button);
        goCart.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(i);
            }
        });

        goPast.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PastPayments.class);
                i.putExtra("mail",firebaseUser.getEmail());
                startActivity(i);
            }
        });
    }

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