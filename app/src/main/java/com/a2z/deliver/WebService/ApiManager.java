package com.a2z.deliver.webService;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;


import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiManager<T> {
    private Context mContext;
    private ProgressDialog dialog;
    private ApiResponseInterface mApiResponseInterface;
    private static final String TAG = ApiManager.class.getSimpleName();

    public ApiManager(Context context, ApiResponseInterface apiResponseInterface) {
        this.mContext = context;
        this.mApiResponseInterface = apiResponseInterface;
        dialog = new ProgressDialog(mContext);
    }

    public void makeApiRequest(Call<T> call, final int apiCode) {

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                Log.e(TAG, "URL : " + response.raw().request().url());
                Log.e(TAG, "Parameter : " + bodyToString(response.raw().request()));
                mApiResponseInterface.isSuccess(response.body(), apiCode);

            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Log.e(TAG, "URL : " + call.request().url());
                Log.e(TAG, "Parameter : " + bodyToString(call.request()));
                Log.e(TAG, "response : " + call.toString());
                mApiResponseInterface.isError(t.getMessage().toString(), apiCode);
            }
        });

    }

    private static String bodyToString(final Request request){

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    public static MultipartBody.Part getMultipartImageBody(String imagePath, String param) {
        MultipartBody.Part body = null;
        // register
        if (imagePath != null) {
            File file = new File(imagePath);
            RequestBody reqFile = RequestBody.create( MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData(param, file.getName(), reqFile);

        }
        return body;
    }

    /*public void makeApiRequest(WebServices apiService) {
        showDialog("Login user...");

        Call<LoginMaster> call = apiService.loginApi(new JsonObject());
        call.enqueue(new Callback<LoginMaster>() {
            @Override
            public void onResponse(Call<LoginMaster> call, Response<LoginMaster> response) {
                closeDialog();
                if (response.isSuccessful()) {
                    mApiResponseInterface.isSuccess(response.body());
                } else {
                    mApiResponseInterface.isError(response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<LoginMaster> call, Throwable t) {
                closeDialog();

                Toast.makeText(mContext, "Network Error", Toast.LENGTH_LONG).show();
            }
        });
    }*/

    /**
     * The purpose of this method is to show the dialog
     *
     * @param message
     */
    private void showDialog(String message) {
        dialog.setMessage(message);
        dialog.show();
    }
    /**
     * The purpose of this method is to close the dialog
     */
    private void closeDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}