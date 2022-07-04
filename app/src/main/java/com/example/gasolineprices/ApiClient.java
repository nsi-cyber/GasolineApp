
package com.example.gasolineprices;

//import com.journaldev.retrofitintro.pojo.MultipleResource;

import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class APIClient {

    //public static Retrofit retrofit = getRetrofit();
    public static String authToken="apikey 1awIvrQnmn8A3CulaWSIlk:5i6Ob15bARdcs1SPusAcCZ";
    public static ApiInterface service(String baseUrl) {
        return getRetrofit(baseUrl).create(ApiInterface.class);
    }

    public static Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
    }

}