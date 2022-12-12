package com.example.pf_hpa4.configurations.retrofit;

import com.example.pf_hpa4.constants.ApiConstants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private static Retrofit retrofitClient = null;

    public static Retrofit getInstance() {
        if (retrofitClient == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor);
            retrofitClient = new Retrofit.Builder()
                    .baseUrl(ApiConstants.BaseUrl)
                    .client(httpClient.build())
                    .build();
        }


        return retrofitClient;
    }
}
