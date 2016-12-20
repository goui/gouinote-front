package fr.goui.gouinote.exception;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Contains a static method used to extract an exception message from a throwable.
 */
public class ExceptionHandler {

    public static String getMessage(Throwable throwable) {
        String message = "";
        ResponseBody body = ((HttpException) throwable).response().errorBody();
        Gson gson = new Gson();
        try {
            CustomException customException = gson.fromJson(body.string(), CustomException.class);
            message = customException.getMessage();
        } catch (IOException e_p) {
            e_p.printStackTrace();
        }
        return message;
    }
}
