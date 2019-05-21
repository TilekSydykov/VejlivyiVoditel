package kg.flaterlab.vv.ui.login;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import kg.flaterlab.vv.data.LoginDataSource;
import kg.flaterlab.vv.data.LoginRepository;
import kg.flaterlab.vv.data.SignUpDataSource;
import kg.flaterlab.vv.data.SignUpRepository;


public class SignUpViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SignUpViewModel.class)) {
            return (T) new SignUpViewModel(SignUpRepository.getInstance(new SignUpDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
