package com.taylorvories.c196.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.taylorvories.c196.R;
import com.taylorvories.c196.models.Term;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {

    private final List<Term> mTerms;
    private final Context mContext;

    public TermAdapter(List<Term> mTerms, Context mContext) {
        this.mTerms = mTerms;
        this.mContext = mContext;
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
        holder.tvDates.setText("Dates here.");

        holder.termFab.setOnClickListener(v -> {
            // Intent intent = new Intent(mContext, EditorActivity.class);
            // intent.putExtra(TERM_ID_KEY, term.getId());
            // mContext.startActivity(intent);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(mContext, "Edit me!", duration);
            toast.show();
        });

        holder.termImageBtn.setOnClickListener(v -> {
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(mContext, "Details pressed!", duration);
            toast.show();
        });
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
