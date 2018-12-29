package com.sbehnken.plethora;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.sbehnken.plethora.model.DictionaryResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DictionaryService {
    static private final String TAG = DictionaryService.class.getSimpleName();

    private final DictionaryInterface dictionaryInterface;

    public DictionaryService() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://od-api.oxforddictionaries.com:443/api/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        dictionaryInterface = retrofit.create(DictionaryInterface.class);

    }

    public Call<DictionaryResponse> getResponse(String word) {
        //todo replace with gradel properties variable
        return dictionaryInterface.getResponse("5e69ff0f", "ac255a2dfb4318041d9e4dbd5f7372c4", word);
    }

    //    public static void validateWord(String word, Callback callback) {



//        OkHttpClient client = new OkHttpClient.Builder().build();
//
//        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.OXFORD_BASE_URL).newBuilder();
//        urlBuilder.addQueryParameter("key", "42b58f5f-d5f1-482d-995b-8f9b3bea3ba0\n" +
//                "\n");
//        urlBuilder.addQueryParameter(Constants.OXFORD_WORD_QUERY_PARAMTER, word);
//        String url = urlBuilder.build().toString();
//
//
////        String url = "https://www.dictionaryapi.com/api/v3/references/collegiate/json/voluminous?key=42b58f5f-d5f1-482d-995b-8f9b3bea3ba0" + word;
//
//        Request request = new Request.Builder().url(url).header("Authorization", Constants.OXFORD_TOKEN).build();
//
//        Call call = client.newCall(request);
//        call.enqueue(callback);
    }


//    public boolean processResults(Response response) {
//        boolean wordExists = true;
//
//        try {
//            String jsonData = response.body().string();
//            JSONObject OxfordJSON = new JSONObject(jsonData);
//            JSONArray wordcheckJSON = OxfordJSON.getJSONArray("wordExists");
//            Log.v(TAG, wordcheckJSON.toString());
//            wordExists= response.isSuccessful();
//
//        }
//
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return wordExists;
//
//    }

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
//    }

