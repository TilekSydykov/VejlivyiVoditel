package kg.flaterlab.vv.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.paperdb.Paper;
import kg.flaterlab.vv.R;
import kg.flaterlab.vv.adapters.MainRecyclerAdapter;
import kg.flaterlab.vv.adapters.SearchResultsAdapter;
import kg.flaterlab.vv.data.model.Number;
import kg.flaterlab.vv.helper.DB;

public class DashBoardFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Number> myDataList;
    MainRecyclerAdapter mAdapter;

    public DashBoardFragment() {
    }

    public static DashBoardFragment newInstance() {
        return new DashBoardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dashboard_layout, container, false);


        recyclerView = view.findViewById(R.id.main_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        myDataList = Paper.book().read(DB.NUMS_NODE, new ArrayList<Number>());

        mAdapter = new MainRecyclerAdapter(myDataList, getContext());

        recyclerView.setAdapter(mAdapter);

        return view;
    }
}
