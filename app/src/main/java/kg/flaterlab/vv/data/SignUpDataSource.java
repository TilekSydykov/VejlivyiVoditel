package kg.flaterlab.vv.data;

import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import kg.flaterlab.vv.data.model.User;

public class SignUpDataSource {
    public SignUpDataSource() {
    }

    public Result<User> signUp(String username, String password, String password2, String name) {
        final String TAG = "signUp";
        Log.d(TAG, "login: start");

        try {
            // TODO: handle loggedInUser authentication
            User u = new User();
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormEncodingBuilder()
                    .add(  "app", "v1"  )
                    .add(  "login", username )
                    .add(  "password", password  )
                    .add(  "password2", password2  )
                    .add(  "name", name  )
                    .build();

            Request request2 = new Request.Builder()
                    .url("https://flipdex.ru/api/signUp")
                    .post(formBody)
                    .build();
            String res2 = "";
            try{
                Response response2 = client.newCall(request2).execute();
                res2 = response2.body().string();

                Log.d(TAG, "login: ");

                try {
                    JSONObject json = new JSONObject(res2);
                    if(json.getString("status").equals("success")){
                        JSONArray json_array = json.getJSONArray("data");
                        JSONObject json_user = json_array.getJSONObject(0);
                        u.setName(json_user.getString("name"));
                        u.setUid(json_user.getString("token"));
                        u.setUsername(json_user.getString("login"));
                        u.setEmail(json_user.getString("email"));
                    }else {
                        Log.d(TAG, "login: fail");
                        throw new Exception();
                    }
                }catch (JSONException e) {
                    Log.d(TAG, "getCon: ");
                }
                Log.d(TAG, "post executer server res :" + res2);
            }catch (
                    IOException e){
                Log.d(TAG, "cant read response");
            }
            return new Result.Success<>(u);
        } catch (Exception e) {
            Log.d(TAG, "login: " + e.getMessage());
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

}
