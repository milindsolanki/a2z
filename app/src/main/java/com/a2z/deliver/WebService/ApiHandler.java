package com.a2z.deliver.webService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHandler {
    private static WebServices apiService;

    public static WebServices getApiService() {

        if (apiService == null) {

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(160, TimeUnit.SECONDS)
                    .readTimeout(160, TimeUnit.SECONDS)
                    .writeTimeout(160, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(APIUrl.BASE_URL)
                    .client(okHttpClient)
                    .build();

            apiService = retrofit.create(WebServices.class);
            return apiService;
        } else {
            return apiService;
        }
    }
}
