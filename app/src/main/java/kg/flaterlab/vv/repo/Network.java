package kg.flaterlab.vv.repo;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.lang.ref.WeakReference;

import kg.flaterlab.vv.fragments.ProfileFragment;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.FormBody;

class  Network extends AsyncTask<String, String, String> {
    private WeakReference<ProfileFragment> activityReference;

    Network(ProfileFragment context) {
        activityReference = new WeakReference<>(context);
    }

    @Override
    protected String doInBackground(String... strings) {

        OkHttpClient client = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();

        builder.add(  "Param1", "A"  );
        builder.add(  "Param2", "b"  );

        RequestBody formBody = builder.build();
        Request request2 = new Request.Builder()
                .url("https://ptsv2.com/t/qfto0-1556245310/d/285070126/post")
                .post(formBody)
                .build();
        String res2 = "";
        try{
            Response response2 = client.newCall(request2).execute();
            res2 = response2.body().toString();
            Log.d("check", "post executer server res :" + res2);
        }catch (IOException e){
            Log.d("check", "cant read response");
        }

        return res2;
    }

    @Override
    protected void onPostExecute(String s) {

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

}
