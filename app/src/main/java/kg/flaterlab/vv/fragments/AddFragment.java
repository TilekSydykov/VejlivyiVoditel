package kg.flaterlab.vv.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;

import java.util.ArrayList;
import java.util.List;

import kg.flaterlab.vv.R;
import kg.flaterlab.vv.helper.NumberSugestion;

public class AddFragment extends Fragment {

    public AddFragment() {
    }

    public static AddFragment newInstance() {
        return new AddFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_layout, container, false);

        final FloatingSearchView mSearchView = view.findViewById(R.id.floating_search_view);

        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                //get suggestions based on newQuery

                List<NumberSugestion> newSuggestions = new ArrayList<>();

                newSuggestions.add(new NumberSugestion("dfdf"));

                //pass them on to the search view
                mSearchView.swapSuggestions(newSuggestions);
            }
        });

        return view;
    }
}

