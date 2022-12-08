package com.example.pf_hpa4.services;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pf_hpa4.interfaces.IApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService extends AppCompatActivity {
    private static IApi API_SERVICE;
    private static final String BaseUrl = "https://asistencia-upn43.ondigitalocean.app/api/";

    public static IApi getApiService(){
        if (API_SERVICE == null){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            API_SERVICE = retrofit.create(IApi.class);
        }
        return API_SERVICE;
    }

}
