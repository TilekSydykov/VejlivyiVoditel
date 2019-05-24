package kg.flaterlab.vv.fragments.add;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import io.paperdb.Paper;
import kg.flaterlab.vv.R;
import kg.flaterlab.vv.data.AddRepository;

import kg.flaterlab.vv.data.Result;
import kg.flaterlab.vv.data.model.Number;
import kg.flaterlab.vv.helper.DB;
import kg.flaterlab.vv.helper.NumberConverter;


public class AddViewModel extends ViewModel {
    private MutableLiveData<AddFormState> addFormState = new MutableLiveData<>();
    private MutableLiveData<AddResult> addResult = new MutableLiveData<>();
    private AddRepository addRepository;

    AddViewModel(AddRepository addRepository) {
        this.addRepository = addRepository;
    }

    LiveData<AddFormState> getAddFormState() {
        return addFormState;
    }

    public LiveData<AddResult> getAddResult() {
        return addResult;
    }
    
    public void list(String filter, String user){
        new ListTop(this).execute(filter, user);
    }

    public void search(String search) {
        if(NumberConverter.isNumber(search) || NumberConverter.isSearch(search)){
            new SearchInWeb(this).execute(search);
        }
    }

    public void voteOnUser(String id, String voteValue, String userId){
        new VoteOnUser(this).execute(id, voteValue, userId);
    }

    public void addNum(String num, String user){
        new AddNum(this).execute(NumberConverter.formatSearchString(num), user);
    }

    static class SearchInWeb extends AsyncTask<String, String, String> {
        WeakReference<AddViewModel> c;

        public SearchInWeb(AddViewModel c) {
            this.c = new WeakReference<>(c);
        }

        @Override
        protected String doInBackground(String... strings) {
            Result<ArrayList<Number>> result = c.get().addRepository.search(strings[0]);

            if (result instanceof Result.Success) {
                ArrayList<Number> data = ((Result.Success<ArrayList<Number>>) result).getData();
                c.get().addResult.postValue(new AddResult(data));
            } else {
                c.get().addResult.postValue(new AddResult(R.string.login_failed));
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    static class ListTop extends AsyncTask<String, String, String> {
        WeakReference<AddViewModel> c;

        public ListTop(AddViewModel c) {
            this.c = new WeakReference<>(c);
        }

        @Override
        protected String doInBackground(String... strings) {
            Result<ArrayList<Number>> result = c.get().addRepository.list(strings[0], strings[1]);

            if (result instanceof Result.Success) {

                ArrayList<Number> data = ((Result.Success<ArrayList<Number>>) result).getData();
                switch (strings[0]){
                    case DB.FILTER_PLUS:
                        Paper.book().write(DB.RATING_PLUS_NOTE, data);
                        Paper.book().write(DB.LAST_PLUS_SAVED_TIME,
                                String.valueOf(System.currentTimeMillis() / 1000));
                        break;
                    case DB.FILTER_VOTE:
                        Paper.book().write(DB.RATING_VOTE_NOTE, data);
                        Paper.book().write(DB.LAST_VOTE_SAVED_TIME,
                                String.valueOf(System.currentTimeMillis() / 1000));
                        break;
                    case DB.FILTER_MINUS:
                        Paper.book().write(DB.RATING_MINUS_NOTE, data);
                        Paper.book().write(DB.LAST_MINUS_SAVED_TIME,
                                String.valueOf(System.currentTimeMillis() / 1000));
                        break;
                }
                c.get().addResult.postValue(new AddResult(data));
            } else {
                c.get().addResult.postValue(new AddResult(R.string.download_failed));
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
    static class VoteOnUser extends AsyncTask<String, String, String> {
        WeakReference<AddViewModel> c;

        public VoteOnUser(AddViewModel c) {
            this.c = new WeakReference<>(c);
        }

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormEncodingBuilder()
                    .add(  "user", strings[2] )
                    .add("vote", strings[1])
                    .build();
            Request request2 = new Request.Builder()
                    .url("https://flipdex.ru/api/vote/"+strings[0] )
                    .post(formBody)
                    .build();
            String res2 = "";
            try{
                Response response2 = client.newCall(request2).execute();
                res2 = response2.body().string();

                try{
                    JSONObject json = new JSONObject(res2);
                    String status = json.getString("status");
                }catch (JSONException e){

                }

                Log.d("check", "post executer server res :" + res2);
            }catch (IOException e){
                Log.d("check", "cant read response");
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
    static class AddNum extends AsyncTask<String, String, String> {
        WeakReference<AddViewModel> c;

        public AddNum(AddViewModel c) {
            this.c = new WeakReference<>(c);
        }

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormEncodingBuilder()
                    .add(  "user", strings[1] )
                    .build();
            Request request2 = new Request.Builder()
                    .url("https://flipdex.ru/api/add/"+strings[0] )
                    .post(formBody)
                    .build();
            String res2 = "";
            try{
                Response response2 = client.newCall(request2).execute();
                res2 = response2.body().string();

                try{
                    JSONObject json = new JSONObject(res2);
                    String status = json.getString("status");
                }catch (JSONException e){

                }
                Log.d("check", "post executer server res :" + res2);
            }catch (IOException e){
                Log.d("check", "cant read response");
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}
