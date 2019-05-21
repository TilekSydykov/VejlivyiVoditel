package kg.flaterlab.vv.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.util.Patterns;

import java.lang.ref.WeakReference;

import kg.flaterlab.vv.data.LoginRepository;
import kg.flaterlab.vv.data.Result;
import kg.flaterlab.vv.R;
import kg.flaterlab.vv.data.model.User;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        new LoginUser(this).execute(username, password);
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
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

    static class LoginUser extends AsyncTask<String, String, String> {
        WeakReference<LoginViewModel> c;

        public LoginUser(LoginViewModel c) {
            this.c = new WeakReference<>(c);
        }

        @Override
        protected String doInBackground(String... strings) {
            Result<User> result = c.get().loginRepository.login(strings[0], strings[1]);

            if (result instanceof Result.Success) {
                User data = ((Result.Success<User>) result).getData();
                c.get().loginResult.postValue(new LoginResult(new LoggedInUserView(data.getName())));
            } else {
                c.get().loginResult.postValue(new LoginResult(R.string.login_failed));
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
