package kg.flaterlab.vv.fragments;

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
import kg.flaterlab.vv.adapters.SearchResultsAdapter;
import kg.flaterlab.vv.data.model.Number;
import kg.flaterlab.vv.helper.DB;

public class DashBoardFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Number> myDataList;
    MainRecyclerAdapter mAdapter;
    RelativeLayout filtersLayout;
    private int shortAnimationDuration;
    ImageView toggler;
    boolean isFiltersUp = false;


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
        filtersLayout = view.findViewById(R.id.filter_menu);
        toggler = view.findViewById(R.id.filter_toggle);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        myDataList = Paper.book().read(DB.NUMS_NODE, new ArrayList<Number>());


        shortAnimationDuration = getResources().getInteger(
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
        filtersLayout.setVisibility(View.VISIBLE);
        filtersLayout.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        filtersLayout.setVisibility(View.GONE);
                    }
                });
    }
    private void showFilters(){
        filtersLayout.setAlpha(0f);
        filtersLayout.setVisibility(View.GONE);
        filtersLayout.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        filtersLayout.setVisibility(View.VISIBLE);
                    }
                });
    }
    private void crossfade() {

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.


        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        filtersLayout.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)

    }

}
