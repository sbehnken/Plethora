package com.sbehnken.plethora;

import com.sbehnken.plethora.model.DictionaryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface DictionaryInterface {

    @GET("entries/en/{word}")

//    Call<DictionaryResponse> getResponse(@Path("id") String id, @Query("key") String key);

    Call<DictionaryResponse> getResponse(@Header("app_id") String app_id, @Header("app_key") String app_key, @Path("word") String word);
}
