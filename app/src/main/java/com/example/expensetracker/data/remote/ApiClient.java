package com.example.expensetracker.data.remote;

import com.example.expensetracker.BuildConfig;
import com.example.expensetracker.data.model.ISO8601DateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;
import android.util.Log;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;

    public static Retrofit get() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws java.io.IOException {
                            Request original = chain.request();

                            // Log the BuildConfig value to verify it's correct
                            Log.d("API_DEBUG", "X-DB-NAME from BuildConfig: '" + BuildConfig.X_DB_NAME + "'");

                            Request.Builder builder = original.newBuilder()
                                    .header("X-DB-NAME", BuildConfig.X_DB_NAME);

                            Request request = builder.build();

                            // Log the actual header being sent
                            Log.d("API_DEBUG", "X-DB-NAME in request: '" + request.header("X-DB-NAME") + "'");

                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(logging)  // Logging should come AFTER to see the modified request
                    .build();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new ISO8601DateAdapter())
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}