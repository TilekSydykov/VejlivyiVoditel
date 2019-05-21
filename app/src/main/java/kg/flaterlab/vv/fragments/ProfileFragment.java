package kg.flaterlab.vv.fragments;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.paperdb.Paper;
import kg.flaterlab.vv.R;
import kg.flaterlab.vv.databinding.UserProfileBinding;
import kg.flaterlab.vv.data.model.User;
import kg.flaterlab.vv.helper.DB;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        UserProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.user_profile, container, false);
        User user = Paper.book().read(DB.USER_NODE);
        binding.setUser(user);
        return binding.getRoot();
    }

}
