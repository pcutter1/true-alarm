package edu.cnm.deepdive.truealarm.service;

import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.truealarm.model.entity.Location;
import io.reactivex.Single;
import java.io.IOException;
import java.net.URLEncoder;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;


//public interface GoogleMapsService {
//
//  @GET ("/geocode")
//  Single<Location.GeoCode> getLatLng(
//      @Header("key") String authHeader, @Query("address") String address);
//
//  static GoogleMapsService getInstance() {return InstanceHolder.INSTANCE;}
//
//  class InstanceHolder {
//
//    private static final GoogleMapsService INSTANCE;
//
//    static {
//      Gson gson = new GsonBuilder()
//          .excludeFieldsWithoutExposeAnnotation()
//          .create();
//        OkHttpClient client = new OkHttpClient.Builder()
//            .build();
//        Retrofit retrofit = new Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .client(client)
//            .baseUrl("https://google-maps-geocoding.p.rapidapi.com/geocode/json?language=en")
//            .build();
//
//        String encodedAddress = URLEncoder.encode(address, "UTF-8");
//        Request request = new Request.Builder()
//            .url("https://google-maps-geocoding.p.rapidapi.com/geocode/json?language=en"
//                + encodedAddress)
//            .get()
//
//            .build();
//        ResponseBody responseBody = client.newCall(request).execute().body();
//        return responseBody.string();
//      }
//    }
//  }
//}
