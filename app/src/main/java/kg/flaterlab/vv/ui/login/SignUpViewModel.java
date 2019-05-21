package kg.flaterlab.vv.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.util.Patterns;

import java.lang.ref.WeakReference;

import kg.flaterlab.vv.R;
import kg.flaterlab.vv.data.Result;
import kg.flaterlab.vv.data.SignUpRepository;
import kg.flaterlab.vv.data.model.User;

public class SignUpViewModel extends ViewModel {


    private MutableLiveData<SignUpFormState> signUpFormState = new MutableLiveData<>();
    private MutableLiveData<SignUpResult> signUpResult = new MutableLiveData<>();
    private SignUpRepository signUpRepository;

    SignUpViewModel(SignUpRepository signUpRepository) {
        this.signUpRepository = signUpRepository;
    }

    LiveData<SignUpFormState> getSignUpFormState() {
        return signUpFormState;
    }

    LiveData<SignUpResult> getSignUpResult() {
        return signUpResult;
    }

    public void signUp(String username, String password, String password2,String name) {
        // can be launched in a separate asynchronous job
        new SignUpViewModel.SignUpUser(this).execute(username, password, password2, name);
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            signUpFormState.setValue(new SignUpFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            signUpFormState.setValue(new SignUpFormState(null, R.string.invalid_password));
        } else {
            signUpFormState.setValue(new SignUpFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    static class SignUpUser extends AsyncTask<String, String, String> {
        WeakReference<SignUpViewModel> c;

        public SignUpUser(SignUpViewModel c) {
            this.c = new WeakReference<>(c);
        }

        @Override
        protected String doInBackground(String... strings) {
            Result<User> result = c.get().signUpRepository.signUp(strings[0], strings[1] , strings[2], strings[3]);

            if (result instanceof Result.Success) {
                User data = ((Result.Success<User>) result).getData();
                c.get().signUpResult.postValue(new SignUpResult(new LoggedInUserView(data.getName())));
            } else {
                c.get().signUpResult.postValue(new SignUpResult(R.string.login_failed));
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) { }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}
