package kg.flaterlab.vv.ui.vote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import hyogeun.github.com.colorratingbarlib.ColorRatingBar;
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

        ArrayList<Number> history;

        history = Paper.book().read(DB.NUMS_NODE);
        Number current = (Number) getIntent().getExtras().getSerializable("num");

        ArrayList<Number> h = new ArrayList<>();
        h.add(current);
        h.addAll(history);
        Log.d("mylog", "onCreate: "+ h.size());
        Paper.book().write(DB.NUMS_NODE, h);
        titleTextView.setText(current.getValue());
    }
}
