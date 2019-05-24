package kg.flaterlab.vv.fragments.dashboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import io.paperdb.Paper;
import kg.flaterlab.vv.R;
import kg.flaterlab.vv.adapters.MainRecyclerAdapter;
import kg.flaterlab.vv.adapters.SearchResultsAdapter;
import kg.flaterlab.vv.data.model.Number;
import kg.flaterlab.vv.data.model.User;
import kg.flaterlab.vv.fragments.add.AddResult;
import kg.flaterlab.vv.fragments.add.AddViewModel;
import kg.flaterlab.vv.fragments.add.AddViewModelFactory;
import kg.flaterlab.vv.helper.DB;
import kg.flaterlab.vv.helper.NumberConverter;

public class DashBoardFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Number> myDataList;
    MainRecyclerAdapter mAdapter;
    CardView filtersLayout;
    private int shortAnimationDuration;
    private int longAnimationDuration;
    ImageView toggler;
    boolean isFiltersUp = false;
    String filter;
    User user;

    AddViewModel addViewModel;

    TextView[] filterButtons = new TextView[3];


    public DashBoardFragment() { }

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

        user = Paper.book().read(DB.USER_NODE, new User());

        recyclerView = view.findViewById(R.id.main_recycler);
        filtersLayout = view.findViewById(R.id.filter_menu);
        toggler = view.findViewById(R.id.filter_toggle);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        addViewModel = ViewModelProviders.of(this, new AddViewModelFactory())
                .get(AddViewModel.class);

        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        longAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        toggler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFiltersUp){
                    hideFilters();
                }else {
                    showFilters();
                }
            }
        });

        filter = DB.FILTER_PLUS;

        updateList(filter);

        filterButtons[0] = view.findViewById(R.id.byValues);
        filterButtons[1] = view.findViewById(R.id.byPlus);
        filterButtons[2] = view.findViewById(R.id.byMinus);

        filterButtons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter = DB.FILTER_VOTE;
                updateList(filter);
                hideFilters();
            }
        });
        filterButtons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter = DB.FILTER_PLUS;
                updateList(filter);
                hideFilters();
            }
        });
        filterButtons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter = DB.FILTER_MINUS;
                updateList(filter);
                hideFilters();
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
                    mAdapter = new MainRecyclerAdapter(myDataList, getContext());
                    recyclerView.setAdapter(mAdapter);
                    // mProgreesBar.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    private void hideFilters(){

        filtersLayout.setAlpha(1f);
        ViewGroup.LayoutParams p =  filtersLayout.getLayoutParams();
        p.height = 200;
        filtersLayout.setLayoutParams(p);
        filtersLayout.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ViewGroup.LayoutParams p =  filtersLayout.getLayoutParams();
                        p.height = 0;
                        filtersLayout.setLayoutParams(p);
                    }
                });
        isFiltersUp = false;
    }

    private void showFilters(){

        filtersLayout.setAlpha(0f);
        ViewGroup.LayoutParams p =  filtersLayout.getLayoutParams();
        p.height = 0;
        filtersLayout.setLayoutParams(p);
        filtersLayout.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ViewGroup.LayoutParams p =  filtersLayout.getLayoutParams();
                        p.height = 200;
                        filtersLayout.setLayoutParams(p);
                    }
                });
        isFiltersUp = true;
    }

    private void updateList(String filter){
        addViewModel.list(filter, user.getUid());
    }
}
