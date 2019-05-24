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
import java.util.ArrayList;

import io.paperdb.Paper;
import kg.flaterlab.vv.data.model.Number;
import kg.flaterlab.vv.data.model.User;
import kg.flaterlab.vv.helper.DB;

public class AddDataSource {
    public Result<ArrayList<Number>> search(String search) {
        final String TAG = "search";
        Log.d(TAG, "search: start");

        try {
            // User u = Paper.book().read(DB.USER_NODE, new User());
            ArrayList<Number> arr = new ArrayList<>();
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormEncodingBuilder()
                    .add(  "user",  "1")
                    .build();
            Request request2 = new Request.Builder()
                    .url("https://flipdex.ru/api/search/" + search)
                    .post(formBody)
                    .build();
            String res2 = "";
            try{
                Response response2 = client.newCall(request2).execute();
                res2 = response2.body().string();

            try {
                JSONObject json = new JSONObject(res2);
                JSONArray json_arr = json.getJSONArray("data");
                for(int i = 0; i < json_arr.length(); i++){
                    JSONObject o = json_arr.getJSONObject(i);
                    Number n = new Number(
                            o.getInt("id"),
                            o.getString("val"),
                            o.getInt("votes"),
                            o.getInt("minus"),
                            o.getInt("plus")
                    );
                    arr.add(n);
                }

            }catch (JSONException e) {
                Log.d("check", "getCon: ");
            }
                Log.d("check", "post executer server res :" + res2);
            }catch (IOException e){
                Log.d("check", "cant read response");
            }
            return new Result.Success<>(arr);
        } catch (Exception e) {
            Log.d(TAG, "login: " + e.getMessage());
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result<ArrayList<Number>> list(String filter,String user) {
        final String TAG = "search";
        Log.d(TAG, "search: start");

        try {
            // User u = Paper.book().read(DB.USER_NODE, new User());
            ArrayList<Number> arr = new ArrayList<>();
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormEncodingBuilder()
                    .add(  "user",  user)
                    .add("filter", filter)
                    .build();
            Request request2 = new Request.Builder()
                    .url("https://flipdex.ru/api/list")
                    .post(formBody)
                    .build();
            String res2 = "";
            try{
                Response response2 = client.newCall(request2).execute();
                res2 = response2.body().string();

                try {
                    JSONObject json = new JSONObject(res2);
                    JSONArray json_arr = json.getJSONArray("data");
                    for(int i = 0; i < json_arr.length(); i++){
                        JSONObject o = json_arr.getJSONObject(i);
                        Number n = new Number(
                                o.getInt("id"),
                                o.getString("val"),
                                o.getInt("votes"),
                                o.getInt("minus"),
                                o.getInt("plus")
                        );
                        arr.add(n);
                    }

                }catch (JSONException e) {
                    Log.d("check", "getCon: ");
                }
                Log.d("check", "post executer server res :" + res2);
            }catch (IOException e){
                Log.d("check", "cant read response");
            }
            return new Result.Success<>(arr);
        } catch (Exception e) {
            Log.d(TAG, "login: " + e.getMessage());
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

}
