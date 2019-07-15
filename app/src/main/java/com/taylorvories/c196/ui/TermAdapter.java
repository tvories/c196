package com.taylorvories.c196.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.taylorvories.c196.R;
import com.taylorvories.c196.TermDetailsActivity;
import com.taylorvories.c196.TermEditActivity;
import com.taylorvories.c196.models.Term;
import com.taylorvories.c196.utilities.TextFormatting;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.taylorvories.c196.utilities.Constants.TERM_ID_KEY;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {

    private final List<Term> mTerms;
    private final Context mContext;
    private final RecyclerContext rContext;

    public TermAdapter(List<Term> mTerms, Context mContext, RecyclerContext rContext) {
        this.mTerms = mTerms;
        this.mContext = mContext;
        this.rContext = rContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.term_list_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.ViewHolder holder, int position) {
        final Term term = mTerms.get(position);
        holder.tvTitle.setText(term.getTitle());
        String startAndEnd = TextFormatting.cardDateFormat.format(term.getStartDate()) + " to " + TextFormatting.cardDateFormat.format(term.getEndDate());
        holder.tvDates.setText(startAndEnd);

        switch(rContext) {
            case MAIN:
                holder.termFab.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_edit));
                holder.termImageBtn.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, TermDetailsActivity.class);
                    intent.putExtra(TERM_ID_KEY, term.getId());
                    mContext.startActivity(intent);
                });

                holder.termFab.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, TermEditActivity.class);
                    intent.putExtra(TERM_ID_KEY, term.getId());
                    mContext.startActivity(intent);
                });
                break;
            case CHILD:
                holder.termFab.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_delete));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_term_title)
        TextView tvTitle;
        @BindView(R.id.card_term_fab)
        FloatingActionButton termFab;
        @BindView(R.id.card_term_dates)
        TextView tvDates;
        @BindView(R.id.btn_term_details)
        ImageButton termImageBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
