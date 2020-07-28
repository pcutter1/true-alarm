//package edu.cnm.deepdive.truealarm.service;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import io.reactivex.Single;
//import okhttp3.OkHttpClient;
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.http.GET;
//import retrofit2.http.Header;
//import retrofit2.http.Query;
//
//public interface GoogleDirectionsService {
//
//  @GET("gallery/search")
//  Single<Gallery.Search> getSearchResult(
//      @Header("Authorization") String authHeader, @Query("q") String subject);
//
//  static ImgurService getInstance() {
//    return InstanceHolder.INSTANCE;
//  }
//
//  class InstanceHolder {
//
//    private static final ImgurService INSTANCE;
//
//    static {
//      Gson gson = new GsonBuilder()
//          .excludeFieldsWithoutExposeAnnotation()
//          .create();
//      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//      interceptor.setLevel(Level.BODY);
//      OkHttpClient client = new OkHttpClient.Builder()
//          .addInterceptor(interceptor)
//          .build();
//      Retrofit retrofit = new Retrofit.Builder()
//          .addConverterFactory(GsonConverterFactory.create(gson))
//          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//          .client(client)
//          .baseUrl(BuildConfig.BASE_URL)
//          .build();
//      INSTANCE = retrofit.create(ImgurService.class);
//    }
//
//  }
//
//}
