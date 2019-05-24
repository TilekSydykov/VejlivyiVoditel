package kg.flaterlab.vv.ui.vote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;
import kg.flaterlab.vv.R;
import kg.flaterlab.vv.data.AddDataSource;
import kg.flaterlab.vv.data.AddRepository;
import kg.flaterlab.vv.data.model.Number;
import kg.flaterlab.vv.data.model.User;
import kg.flaterlab.vv.fragments.add.AddViewModel;
import kg.flaterlab.vv.fragments.add.AddViewModelFactory;
import kg.flaterlab.vv.helper.DB;
import kg.flaterlab.vv.helper.NumberConverter;

public class NumberVoteActivity extends AppCompatActivity {
    String vote;
    AddViewModel addViewModel;
    User currentUser;

    HashMap<String, String> voted;

    RelativeLayout relativeLayoutVotePlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        if(!Paper.book().contains(DB.LAST_VOTED)){
            voted = new HashMap<>();
            Paper.book().write(DB.LAST_VOTED, voted);
        }else {
            voted = Paper.book().read(DB.LAST_VOTED, new HashMap<String, String>());
        }

        setContentView(R.layout.activity_number_vote);

        final TextView mTextView = findViewById(R.id.rating_rate);
        TextView titleTextView = findViewById(R.id.title_num_activity);
        final TextView votes = findViewById(R.id.votes);
        TextView plus = findViewById(R.id.plus);
        TextView minus = findViewById(R.id.minus);
        relativeLayoutVotePlace = findViewById(R.id.vote_place);
        EditText review = findViewById(R.id.review_on_vote);
        FloatingActionButton back = findViewById(R.id.back_button);
        Button submit = findViewById(R.id.submit_vote);
        TextView info = findViewById(R.id.information_for_user);

        ArrayList<Number> history;



        currentUser = Paper.book().read(DB.USER_NODE, new User());

        history = Paper.book().read(DB.NUMS_NODE);
        final Number current = (Number) getIntent().getExtras().getSerializable("num");

        ArrayList<Number> h = new ArrayList<>();
        h.add(current);
        int i = 1;
        for(Number num : history){
            if(num.getId() != current.getId() && i < 20 && current.getId() != 0){
                h.add(num);
                i++;
            }
        }
        Paper.book().write(DB.NUMS_NODE, h);

        if(voted.containsKey(current.getValue())){
            long old = Integer.valueOf(voted.get(current.getValue()));
            long now = System.currentTimeMillis() / 1000;
            long passed = (now - old) ;
            if(passed < 600){
                relativeLayoutVotePlace.setVisibility(View.GONE);
                info.setVisibility(View.VISIBLE);
                info.setText(getString(R.string.time_limit_vote_message));
            }
        }

        titleTextView.setText(
                NumberConverter.formatNumber(current.getValue())
        );
        String text = getString(R.string.votes_display) + " " + current.getVotes();
        votes.setText(text);
        text = getString(R.string.plus_display) +  current.getPlus();
        plus.setText(text);
        text = getString(R.string.minus_display) + current.getMinus();
        minus.setText(text);

        addViewModel = ViewModelProviders.of(this, new AddViewModelFactory())
                .get(AddViewModel.class);


        if(current.getId() == 0){
            addViewModel.addNum(current.getValue(), currentUser.getUid());
        }

        final RadioGroup radioGroup = findViewById( R.id.radio_buuton_group);
        vote = "";

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = radioGroup.getCheckedRadioButtonId();
                switch (id){
                    case R.id.r_b_minus3:
                        vote = "-3";
                        break;
                    case R.id.r_b_minus2:
                        vote = "-2";
                        break;
                    case R.id.r_b_minus1:
                        vote = "-1";
                        break;
                    case R.id.r_b_plus3:
                        vote = "+3";
                        break;
                    case R.id.r_b_plus2:
                        vote = "+2";
                        break;
                    case R.id.r_b_plus1:
                        vote = "+1";
                        break;

                }
                mTextView.setText(vote);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!vote.isEmpty()){
                    addViewModel.voteOnUser(
                            current.getValue(),
                            vote,
                            currentUser.getUid()
                    );
                    voted.put(current.getValue(), String.valueOf(System.currentTimeMillis()/1000));
                    Paper.book().write(DB.LAST_VOTED, voted);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "not rated", Toast.LENGTH_LONG).show();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
