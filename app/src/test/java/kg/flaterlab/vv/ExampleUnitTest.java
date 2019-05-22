package kg.flaterlab.vv;

import android.net.Network;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import io.paperdb.Paper;
import kg.flaterlab.vv.data.model.User;
import kg.flaterlab.vv.helper.DB;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void CheckUserVote(){
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormEncodingBuilder()
                .add(  "user", "2"  )
                .build();
        Request request2 = new Request.Builder()
                .url("https://flipdex.ru/api/search/0")
                .post(formBody)
                .build();
        String res2 = "";
        try{
            Response response2 = client.newCall(request2).execute();
            res2 = response2.body().string();

//            try {
//                JSONObject json = new JSONObject(res2);
//                JSONObject json_user = json.getJSONObject("user");
//                System.out.println(res2);
//                User u = new User();
//                u.setName(json_user.getString("name"));
//                u.setUid(json_user.getString("token"));
//                u.setUsername(json_user.getString("login"));
//                u.setEmail(json_user.getString("email"));
//
//                Paper.book().write(DB.USER_NODE, u);
//            }catch (JSONException e) {
//                Log.d("check", "getCon: ");
//            }
            System.out.println(res2);
            // Log.d("check", "post executer server res :" + res2);
        }catch (IOException e){
            // Log.d("check", "cant read response");
        }
    }

    @Test
    public void CheckUserGet(){
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormEncodingBuilder()
                .add(  "Param1", "A"  )
                .add(  "Param2", "b"  )
                .build();
        Request request2 = new Request.Builder()
                .url("https://ptsv2.com/t/qfto0-1556245310/d/285070126/post")
                .post(formBody)
                .build();
        String res2 = "";
        try{
            Response response2 = client.newCall(request2).execute();
            res2 = response2.body().string();

            try {
                JSONObject json = new JSONObject(res2);
                JSONObject json_user = json.getJSONObject("user");
                System.out.println(res2);
                User u = new User();
                u.setName(json_user.getString("name"));
                u.setUid(json_user.getString("token"));
                u.setUsername(json_user.getString("login"));
                u.setEmail(json_user.getString("email"));

                Paper.book().write(DB.USER_NODE, u);
            }catch (JSONException e) {
                Log.d("check", "getCon: ");
            }
            Log.d("check", "post executer server res :" + res2);
        }catch (IOException e){
            Log.d("check", "cant read response");
        }

    }
}