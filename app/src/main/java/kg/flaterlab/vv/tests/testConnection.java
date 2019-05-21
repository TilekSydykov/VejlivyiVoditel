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

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormEncodingBuilder()
                .add(  "app", "v1"  )
                .add(  "login", "tilekfromkg@gmail.com"  )
                .add(  "password", "himikthemob"  )
                .build();

        Request request2 = new Request.Builder()
                .url("https://flipdex.ru/api/signIn")
                .post(formBody)
                .build();
        String res2 = "";
        try{
            Response response2 = client.newCall(request2).execute();
            res2 = response2.body().string();

            try {
                JSONObject json = new JSONObject(res2);
                JSONObject json_user = json.getJSONObject("user");
                User u = new User();
                u.setName(json_user.getString("name"));
                u.setUid(json_user.getString("token"));
                u.setUsername(json_user.getString("login"));
                u.setEmail(json_user.getString("email"));

                Paper.book().write(DB.USER_NODE, u);
            }catch (JSONException e) {
                Log.d("check", "getCon: ");
            }
            System.out.println(res2);
            Log.d("check", "post executer server res :" + res2);
        }catch (
                IOException e){
            Log.d("check", "cant read response");
        }
    }
}
