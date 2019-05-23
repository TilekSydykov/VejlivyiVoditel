package kg.flaterlab.vv.ui.vote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.TextView;

import java.util.ArrayList;

import io.paperdb.Paper;
import kg.flaterlab.vv.R;
import kg.flaterlab.vv.data.model.Number;
import kg.flaterlab.vv.helper.DB;

public class NumberVoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_vote);

        TextView mTextView = findViewById(R.id.rating_rate);
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
    }
}
