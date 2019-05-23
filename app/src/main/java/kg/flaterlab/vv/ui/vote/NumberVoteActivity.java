package kg.flaterlab.vv.ui.vote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import io.paperdb.Paper;
import kg.flaterlab.vv.R;
import kg.flaterlab.vv.data.model.Number;
import kg.flaterlab.vv.helper.DB;

public class NumberVoteActivity extends AppCompatActivity {
    String vote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_vote);

        final TextView mTextView = findViewById(R.id.rating_rate);
        TextView titleTextView = findViewById(R.id.title_num_activity);
        TextView votes = findViewById(R.id.votes);
        TextView plus = findViewById(R.id.plus);
        TextView minus = findViewById(R.id.minus);

        ArrayList<Number> history;

        history = Paper.book().read(DB.NUMS_NODE);
        Number current = (Number) getIntent().getExtras().getSerializable("num");

        ArrayList<Number> h = new ArrayList<>();
        h.add(current);
        for(Number num : history){
            if(num.getId() != current.getId()){
                h.add(num);
            }
        }
        Paper.book().write(DB.NUMS_NODE, h);
        titleTextView.setText(current.getValue());
        String text = getString(R.string.votes_display) + " " + current.getVotes();
        votes.setText(text);
        text = getString(R.string.plus_display) +  current.getPlus();
        plus.setText(text);
        text = getString(R.string.minus_display) + current.getMinus();
        minus.setText(text);

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

        }
}
