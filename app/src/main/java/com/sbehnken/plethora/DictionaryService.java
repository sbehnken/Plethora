package com.sbehnken.plethora;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.sbehnken.plethora.model.DictionaryResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sbehnken.plethora.model.LexicalEntry;

public class DictionaryService {
    static private final String TAG = DictionaryService.class.getSimpleName();

    private final DictionaryInterface dictionaryInterface;

    public DictionaryService() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://od-api.oxforddictionaries.com:443/api/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        dictionaryInterface = retrofit.create(DictionaryInterface.class);
    }

    public Call<DictionaryResponse> getResponse(String word) {
        return dictionaryInterface.getResponse("5e69ff0f", BuildConfig.OXFORD_TOKEN, word);
    }
}
