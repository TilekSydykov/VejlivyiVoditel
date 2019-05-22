package kg.flaterlab.vv.fragments.add;

import android.os.AsyncTask;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import kg.flaterlab.vv.R;
import kg.flaterlab.vv.data.AddRepository;

import kg.flaterlab.vv.data.Result;
import kg.flaterlab.vv.data.model.Number;




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

    LiveData<AddResult> getAddResult() {
        return addResult;
    }

    public void search(String search) {
        new SearchInWeb(this).execute(search);
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
}
