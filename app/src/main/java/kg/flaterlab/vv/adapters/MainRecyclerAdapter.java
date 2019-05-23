package kg.flaterlab.vv.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kg.flaterlab.vv.R;
import kg.flaterlab.vv.data.model.Number;
import kg.flaterlab.vv.helper.NumberConverter;
import kg.flaterlab.vv.ui.vote.NumberVoteActivity;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MyViewHolder> {
    private ArrayList<Number> mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView linearLayout;
        public TextView textView;
        public TextView votes;
        public TextView plus;
        public TextView minus;
        public MyViewHolder(CardView v) {
            super(v);
            linearLayout = v;
            textView = v.findViewById(R.id.num);
            plus = v.findViewById(R.id.plus);
            minus = v.findViewById(R.id.minus);
            votes = v.findViewById(R.id.votes);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MainRecyclerAdapter(ArrayList<Number> myDataset, Context context) {
        mDataset = myDataset;
        this.context  = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MainRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_recycler_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Number n = mDataset.get(position);
        holder.textView.setText(NumberConverter.formatNumber(n.getValue()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NumberVoteActivity.class);
                intent.putExtra("num", n);
                context.startActivity(intent);
            }
        });
        String text = context.getString(R.string.votes_display) + " " + n.getVotes();
        holder.votes.setText(text);
        text = context.getString(R.string.plus_display) +  n.getPlus();
        holder.plus.setText(text);
        text = context.getString(R.string.minus_display) + n.getMinus();
        holder.minus.setText(text);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

