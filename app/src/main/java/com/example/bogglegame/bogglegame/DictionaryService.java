package com.example.bogglegame.bogglegame;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Call;
import okhttp3.Response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class DictionaryService {
    static private final String TAG = DictionaryService.class.getSimpleName();

    public static void validateWord(String word, Callback callback) {

        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.OXFORD_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("key", "42b58f5f-d5f1-482d-995b-8f9b3bea3ba0\n" +
                "\n");
        urlBuilder.addQueryParameter(Constants.OXFORD_WORD_QUERY_PARAMTER, word);
        String url = urlBuilder.build().toString();


//        String url = "https://www.dictionaryapi.com/api/v3/references/collegiate/json/voluminous?key=42b58f5f-d5f1-482d-995b-8f9b3bea3ba0" + word;

        Request request = new Request.Builder().url(url).header("Authorization", Constants.OXFORD_TOKEN).build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }


    public boolean processResults(Response response) {
        boolean wordExists = true;

        try {
            String jsonData = response.body().string();
            JSONObject OxfordJSON = new JSONObject(jsonData);
            JSONArray wordcheckJSON = OxfordJSON.getJSONArray("wordExists");
            Log.v(TAG, wordcheckJSON.toString());
            wordExists= response.isSuccessful();

        }

        catch (IOException e) {
            e.printStackTrace();
        }

        catch (JSONException e) {
            e.printStackTrace();
        }
        return wordExists;

    }

//        private class CallbackTask extends AsyncTask<String, Integer, String> {
//
//            @Override
//            protected String doInBackground(String... params) {
//
//                final String app_id = "5e69ff0f";
//                final String app_key = "ac255a2dfb4318041d9e4dbd5f7372c4";
//                try {
//                    URL url = new URL(params[0]);
//                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
//                    urlConnection.setRequestProperty("Accept","application/json");
//                    urlConnection.setRequestProperty("app_id",app_id);
//                    urlConnection.setRequestProperty("app_key",app_key);
//
//                    // read the output from the server
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                    StringBuilder stringBuilder = new StringBuilder();
//
//                    String line = null;
//                    while ((line = reader.readLine()) != null) {
//                        stringBuilder.append(line + "\n");
//                    }
//
//                    return stringBuilder.toString();
//
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                    return e.toString();
//                }
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                super.onPostExecute(result);
//
//                System.out.println(result);
//            }
//        }
    }

