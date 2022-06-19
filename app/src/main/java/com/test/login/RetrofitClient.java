package com.test.login;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class RetrofitClient {

   private static String Base_URL = "url";

   private static RetrofitClient retrofitClient;
   private static Retrofit retrofit;
   private Context context1;

    private RetrofitClient(){

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                SharedPreferences getShared = context1.getSharedPreferences("as pay", MODE_PRIVATE);
                String token = getShared.getString("token","token");
                System.out.println("token = " + token);
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization","Bearer "+token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

       retrofit = new Retrofit.Builder()
               .baseUrl(Base_URL)
               .addConverterFactory(GsonConverterFactory.create())
               .client(client)
               .build();
   }

   public static synchronized RetrofitClient getInstance(Context context){
        if (retrofitClient==null){
            retrofitClient=new RetrofitClient();
            retrofitClient.context1 = context;
        }
        return retrofitClient;
   }

   public Api getApi(){
       return retrofit.create(Api.class);
   }
}




