package kg.flaterlab.vv.tests;

import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.paperdb.Paper;
import kg.flaterlab.vv.data.model.User;
import kg.flaterlab.vv.helper.DB;

public class testConnection {
    public static void main(String[] args) {

        System.out.println(System.currentTimeMillis() / 1000 );
    }
}
