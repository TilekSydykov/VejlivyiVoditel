package kg.flaterlab.vv.fragments.profile;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.paperdb.Paper;
import kg.flaterlab.vv.R;
import kg.flaterlab.vv.databinding.UserProfileBinding;
import kg.flaterlab.vv.data.model.User;
import kg.flaterlab.vv.helper.DB;
import kg.flaterlab.vv.ui.informative.AboutActivity;

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
        final View view = inflater.inflate(R.layout.user_profile, container, false);
        User user = Paper.book().read(DB.USER_NODE);

        TextView about = view.findViewById(R.id.open_about);
        TextView name = view.findViewById(R.id.name);
        TextView settings = view.findViewById(R.id.open_settings);


        name.setText(user.getName());

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AboutActivity.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        return view;
    }

}
