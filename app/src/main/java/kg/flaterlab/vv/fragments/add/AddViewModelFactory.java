package kg.flaterlab.vv.fragments.add;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import kg.flaterlab.vv.data.AddDataSource;
import kg.flaterlab.vv.data.AddRepository;

public class AddViewModelFactory implements ViewModelProvider.Factory{
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddViewModel.class)) {
            return (T) new AddViewModel(AddRepository.getInstance(new AddDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
