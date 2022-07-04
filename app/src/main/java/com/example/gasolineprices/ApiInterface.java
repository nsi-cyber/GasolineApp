package com.example.gasolineprices;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("gasPrice/turkeyDiesel")
    Call<DieselList> verDiesel(@Header("authorization") String auth,@Query("city") String city);
    @GET("gasPrice/turkeyLpg")
    Call<LpgResult> verLpg(@Header("authorization") String auth,@Query("city") String city);

    @GET("gasPrice/turkeyGasoline")
    Call<GasolineList> verGasoline(@Header("authorization") String auth,@Query("city") String city);




}
