package com.example.gasolineprices;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Normalizer;
import java.util.List;
import java.util.Scanner;
//import static com.example.gasolineprices.json.readJsonFromUrl;
import java.lang.Object;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
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
import android.widget.GridView;
import android.widget.Spinner;
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
public class MainActivity2 extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main2);







        Bundle extras = getIntent().getExtras();
        String value="";
        if (extras != null) {
             value = extras.getString("key");
if(value.contains("ğ"))
    value=value.replace('ğ','g');
            if(value.contains("İ"))
                value=value.replace('İ','I');
            if(value.contains("ı"))
                value=value.replace('ı','i');
            if(value.contains("ö"))
                value=value.replace('ö','o');
            if(value.contains("Ç"))
                value=value.replace('Ç','C');
            if(value.contains("â"))
                value=value.replace('â','a');
            if(value.contains("ü"))
                value=value.replace('ü','u');
            if(value.contains("ş"))
                value=value.replace('ş','s');
            if(value.contains("Ş"))
                value=value.replace('Ş','S');


        }

        List<FuelClass> fuelClass = new ArrayList<FuelClass>();
        Spinner mySpinner=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(MainActivity2.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Fuel_Types));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);


        int iCurrentSelection = mySpinner.getSelectedItemPosition();
        String finalValue = value;
        fuelClass.clear();
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fuelClass.clear();
                switch(mySpinner.getSelectedItem().toString()){

                    case "Gasoline":

                        ProgressDialog progress = new ProgressDialog(MainActivity2.this);
                        progress.setTitle("Loading");
                        progress.setMessage("Wait while loading...");
                        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                        progress.show();


                        Call<GasolineList> callGasoline = APIClient.service("https://api.collectapi.com/").verGasoline(APIClient.authToken, Uri.encode(finalValue));

                        callGasoline.enqueue(new Callback<GasolineList>() {

                            @Override
                            public void onResponse(Call<GasolineList> call, retrofit2.Response<GasolineList> response) {
                                if (response.code() >= 200 && response.code() < 300) {
                                    Toast.makeText(MainActivity2.this, "success", Toast.LENGTH_LONG).show();
                                    for (int i = 0; i < response.body().result.size(); i++) {
                                        fuelClass.add(new FuelClass(finalValue,"Gasoline",response.body().result.get(i).marka,response.body().result.get(i).benzin)) ;
                                    }
                                    RecyclerView myrv=(RecyclerView)findViewById(R.id.gridView);
                                    RecyclerViewAdapter myRecyclerViewAdapter=new RecyclerViewAdapter(MainActivity2.this,fuelClass);
                                    myrv.setLayoutManager(new GridLayoutManager(MainActivity2.this,1));
                                    myrv.setAdapter(myRecyclerViewAdapter);
                                    progress.dismiss();
                                } else {
                                    Toast.makeText(MainActivity2.this, "dfs", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<GasolineList> call, Throwable t) {
                                Toast.makeText(MainActivity2.this, "sd", Toast.LENGTH_LONG).show();
                            }
                        });









                        break;
                    case "Lpg":

                     progress = new ProgressDialog(MainActivity2.this);
                        progress.setTitle("Loading");
                        progress.setMessage("Wait while loading...");
                        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                        progress.show();

                        Call<LpgResult> callLpg = APIClient.service("https://api.collectapi.com/").verLpg(APIClient.authToken, Uri.encode(finalValue));
                        String finalValue1 = finalValue;
                        callLpg.enqueue(new Callback<LpgResult>() {
                            @Override
                            public void onResponse(Call<LpgResult> call, retrofit2.Response<LpgResult> response) {
                                if (response.code() >= 200 && response.code() < 300) {
                                    Toast.makeText(MainActivity2.this, "success", Toast.LENGTH_LONG).show();
                                    fuelClass.clear();
                                    for (int i = 0; i < response.body().result.size(); i++) {
                                        fuelClass.add(new FuelClass(finalValue1,"Lpg",response.body().result.get(i).marka,Double.valueOf((response.body().result.get(i).lpg).replace(',','.')))) ;
                                    }
                                    RecyclerView myrv=(RecyclerView)findViewById(R.id.gridView);
                                    RecyclerViewAdapter myRecyclerViewAdapter=new RecyclerViewAdapter(MainActivity2.this,fuelClass);
                                    myrv.setLayoutManager(new GridLayoutManager(MainActivity2.this,1));
                                    myrv.setAdapter(myRecyclerViewAdapter);
                                    progress.dismiss();
                                } else {
                                    Toast.makeText(MainActivity2.this, "dfs", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<LpgResult> call, Throwable t) {
                                Toast.makeText(MainActivity2.this, "sd", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    case "Diesel":

                        progress = new ProgressDialog(MainActivity2.this);
                        progress.setTitle("Loading");
                        progress.setMessage("Wait while loading...");
                        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                        progress.show();
                        Call<DieselList> callDiesel = APIClient.service("https://api.collectapi.com/").verDiesel(APIClient.authToken, Uri.encode(finalValue));
                        String finalValue2 = finalValue;
                        callDiesel.enqueue(new Callback<DieselList>() {

                            @Override
                            public void onResponse(Call<DieselList> call, retrofit2.Response<DieselList> response) {
                                if (response.code() >= 200 && response.code() < 300) {
                                    Toast.makeText(MainActivity2.this, "success", Toast.LENGTH_LONG).show();
                                    fuelClass.clear();
                                    for (int i = 0; i < response.body().result.size(); i++) {
                                        fuelClass.add(new FuelClass(finalValue2,"Diesel",response.body().result.get(i).marka,response.body().result.get(i).motorin)) ;
                                    }
                                    RecyclerView myrv=(RecyclerView)findViewById(R.id.gridView);
                                    RecyclerViewAdapter myRecyclerViewAdapter=new RecyclerViewAdapter(MainActivity2.this,fuelClass);
                                    myrv.setLayoutManager(new GridLayoutManager(MainActivity2.this,1));
                                    myrv.setAdapter(myRecyclerViewAdapter);
                                    progress.dismiss();
                                } else {
                                    Toast.makeText(MainActivity2.this, "dfs", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<DieselList> call, Throwable t) {
                                Toast.makeText(MainActivity2.this, "sd", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;

                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }});


















    }

}