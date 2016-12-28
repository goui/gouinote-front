package fr.goui.gouinote.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import fr.goui.gouinote.model.Note;
import fr.goui.gouinote.model.User;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Network interface containing all the endpoints signature.
 * The factory creates the retrofit object.
 */
public interface NetworkService {

    String BASE_URL = "http://192.168.0.11:8080/";

    @POST("user/createAccount")
    Observable<User> createUser(@Body User user);

    @POST("user/signIn")
    Observable<User> signIn(@Query("nickname") String nickname, @Query("password") String password);

    @GET("note/getAllNotes")
    Observable<List<Note>> getAllNotes();

    @GET("user/getAllUsers")
    Observable<List<User>> getAllUsers();

    @GET("user/{nickname}")
    Observable<User> getUser(@Path("nickname") String nickname);

    @POST("user/addNote")
    Observable<Boolean> addNote(@Query("nickname") String nickname, @Body Note note);

    class Factory {
        public static NetworkService create() {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(NetworkService.class);
        }
    }
}
