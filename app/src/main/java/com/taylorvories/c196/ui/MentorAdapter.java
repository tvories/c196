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
import com.taylorvories.c196.MentorDetailsActivity;
import com.taylorvories.c196.MentorEditActivity;
import com.taylorvories.c196.R;
import com.taylorvories.c196.models.Mentor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.taylorvories.c196.utilities.Constants.MENTOR_ID_KEY;

public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.ViewHolder> {

    private final List<Mentor> mMentors;
    private final Context mContext;
    private final RecyclerContext rContext;

    public MentorAdapter(List<Mentor> mMentors, Context mContext, RecyclerContext rContext) {
        this.mMentors = mMentors;
        this.mContext = mContext;
        this.rContext = rContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.mentor_list_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MentorAdapter.ViewHolder holder, int position) {
        final Mentor mentor = mMentors.get(position);
        holder.tvName.setText(mentor.getName());
        holder.tvEmail.setText(mentor.getEmail());

        switch(rContext) {
            case MAIN:
                holder.mentorFab.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_edit));
                holder.mentorImageBtn.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, MentorDetailsActivity.class);
                    intent.putExtra(MENTOR_ID_KEY, mentor.getId());
                    mContext.startActivity(intent);
                });

                holder.mentorFab.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, MentorEditActivity.class);
                    intent.putExtra(MENTOR_ID_KEY, mentor.getId());
                    mContext.startActivity(intent);
                });
                break;
            case CHILD:
                holder.mentorFab.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_delete));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mMentors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_mentor_name)
        TextView tvName;
        @BindView(R.id.card_mentor_fab)
        FloatingActionButton mentorFab;
        @BindView(R.id.card_mentor_email)
        TextView tvEmail;
        @BindView(R.id.btn_mentor_details)
        ImageButton mentorImageBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
