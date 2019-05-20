package kg.flaterlab.vv;

import android.net.Network;
import android.util.Log;

import org.junit.Test;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    public void getCon(){
        OkHttpClient client = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        builder.add(  "app", "v1"  );
        builder.add(  "name", "dsfd"  );
        builder.add(  "login", "fzlsfre" );
        builder.add(  "password", "1d"  );
        builder.add(  "password2", "1d"  );
        RequestBody formBody = builder.build();
        Request request2 = new Request.Builder()
                .url("http://himikawa.io/api/signUp")
                .post(formBody)
                .build();
        String res2 = "";
        try{
            Response response2 = client.newCall(request2).execute();
            res2 = response2.body().string();
            System.out.println("post executer server res :" + res2);
        }catch (IOException e){
            Log.d("check", "cant read response");
        }
    }
}