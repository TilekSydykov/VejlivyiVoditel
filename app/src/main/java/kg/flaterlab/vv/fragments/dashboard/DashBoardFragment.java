package kg.flaterlab.vv.fragments.dashboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import io.paperdb.Paper;
import kg.flaterlab.vv.R;
import kg.flaterlab.vv.adapters.MainRecyclerAdapter;
import kg.flaterlab.vv.data.model.Number;
import kg.flaterlab.vv.helper.DB;

public class DashBoardFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Number> myDataList;
    MainRecyclerAdapter mAdapter;
    RelativeLayout filtersLayout;
    private int shortAnimationDuration;
    private int longAnimationDuration;
    ImageView toggler;
    boolean isFiltersUp = false;


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

        recyclerView = view.findViewById(R.id.main_recycler);
        filtersLayout = view.findViewById(R.id.filter_menu);
        toggler = view.findViewById(R.id.filter_toggle);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        myDataList = Paper.book().read(DB.NUMS_NODE, new ArrayList<Number>());

        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        longAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        toggler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFiltersUp){
                    hideFilters();
                    isFiltersUp = false;
                }else {
                    showFilters();
                    isFiltersUp = true;
                }
            }
        });

        mAdapter = new MainRecyclerAdapter(myDataList, getContext());

        recyclerView.setAdapter(mAdapter);

        return view;
    }

    private void hideFilters(){
        filtersLayout.setAlpha(1f);
        ViewGroup.LayoutParams p =  filtersLayout.getLayoutParams();
        p.height = 300;
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
                        p.height = 300;
                        filtersLayout.setLayoutParams(p);
                    }
                });
    }

}
