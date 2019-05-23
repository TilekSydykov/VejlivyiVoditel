package kg.flaterlab.vv.fragments.add;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.arlib.floatingsearchview.FloatingSearchView;

import java.util.ArrayList;

import io.paperdb.Paper;
import kg.flaterlab.vv.R;
import kg.flaterlab.vv.adapters.SearchResultsAdapter;
import kg.flaterlab.vv.data.model.Number;
import kg.flaterlab.vv.helper.DB;
import kg.flaterlab.vv.helper.NumberConverter;

public class AddFragment extends Fragment {

    AddViewModel addViewModel;
    String Query;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    SearchResultsAdapter mAdapter;
    TextView titleTexView;
    ArrayList<Number> myDataList;

    public AddFragment() {
    }

    public static AddFragment newInstance() {
        return new AddFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!Paper.book().contains(DB.NUMS_NODE)){
            Paper.book().write(DB.NUMS_NODE, new ArrayList<Number>());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.add_layout, container, false);

        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        titleTexView = view.findViewById(R.id.last_searched);
        addViewModel = ViewModelProviders.of(this, new AddViewModelFactory())
                .get(AddViewModel.class);

        myDataList = Paper.book().read(DB.NUMS_NODE, new ArrayList<Number>());

        mAdapter = new SearchResultsAdapter(myDataList, getContext());
        recyclerView.setAdapter(mAdapter);
        final FloatingSearchView mSearchView = view.findViewById(R.id.floating_search_view);
        Query = "";

        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                if(newQuery.length() < 10 && newQuery.length() > 0 && newQuery.length() % 2 == 0){
                    addViewModel.search(NumberConverter.formatSearchString(newQuery));
                    Query = newQuery;
                }
                if(newQuery.length() == 0){
                    titleTexView.setText(view.getContext().getString(R.string.latest_search));
                    myDataList = Paper.book().read(DB.NUMS_NODE, new ArrayList<Number>());
                    mAdapter = new SearchResultsAdapter(myDataList, getContext());
                    recyclerView.setAdapter(mAdapter);
                }
            }
        });

        addViewModel.getAddResult().observe(this, new Observer<AddResult>() {
            @Override
            public void onChanged(@Nullable AddResult addResult) {
                if (addResult == null) {
                    return;
                }
                // loadingProgressBar.setVisibility(View.GONE);
                if (addResult.getError() != null) {
                    // error handle

                }
                if (addResult.getSuccess() != null) {

                    myDataList = addResult.getSuccess();

                    if(myDataList.size() == 0 && NumberConverter.isNumber(Query)){
                        myDataList.add(new Number(0, NumberConverter.formatNumber(Query), 0, 0, 0));
                    }
                    titleTexView.setText(view.getContext().getString(R.string.search_result));
                    mAdapter = new SearchResultsAdapter(myDataList, getContext());
                    recyclerView.setAdapter(mAdapter);
                }
            }
        });
        return view;
    }
}

