package com.jcpaidprojects.jcauth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class JcAuth {
    private OnCompleteListener onCompleteListener;
    private Context context;
    private String api;

    public static JcAuth getInstance(Context context, String url) {
        return new JcAuth(context, url);
    }

    private JcAuth(Context context, String api) {
        this.context = context;
        this.api = api;
    }

    public void loginUserWithEmailAndPassword(String email, String password) {
        AuthRequest authRequest = getClient(api).create(AuthRequest.class);
        Call<AuthResult> authResultCall = authRequest.prepare(getAuth(), email, password, getUUID(context));
        authResultCall.enqueue(new Callback<AuthResult>() {
            @Override
            public void onResponse(Call<AuthResult> call, Response<AuthResult> response) {
                AuthResult result = response.body();
                if (result != null && onCompleteListener != null) {
                    if (result.getLoginAuthorized()) {
                        onCompleteListener.onComplete(result.getTimestamp() * 1000);
                    } else {
                        onCompleteListener.onError(result.getMessage());
                    }
                } else if (onCompleteListener != null) {
                    onCompleteListener.onError("Authentication failure.");
                }
            }

            @Override
            public void onFailure(Call<AuthResult> call, Throwable t) {
                if (onCompleteListener != null) {
                    onCompleteListener.onError(t.getMessage());
                }
            }
        });
    }

    public void addOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    @SuppressLint("HardwareIds")
    private String getUUID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private String getAuth() {
        return new String(Base64.decode("SkNBUEk=", Base64.DEFAULT));
    }

    private Retrofit getClient(String url) {
        return new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
    }

    private interface AuthRequest {
        @FormUrlEncoded
        @POST("api.php")
        Call<AuthResult> prepare(@Field("api_key") String api_key, @Field("user_email") String email, @Field("user_password") String password, @Field("uuid") String uuid);
    }

}
