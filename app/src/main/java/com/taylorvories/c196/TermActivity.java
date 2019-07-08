package com.taylorvories.c196;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taylorvories.c196.models.Term;
import com.taylorvories.c196.ui.TermAdapter;
import com.taylorvories.c196.viewmodel.TermViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermActivity extends AppCompatActivity {
    @BindView(R.id.term_recycler_view)
    RecyclerView mTermRecyclerView;

    @OnClick(R.id.fab)
    void fabClickHandler() {
        Intent intent = new Intent(this, TermEditActivity.class);
        startActivity(intent);
        /*Context context = getApplicationContext();
        CharSequence text = "Add Term Pressed!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();*/
    }

    private List<Term> termData = new ArrayList<>();
    private TermAdapter mTermAdapter;
    private TermViewModel mTermViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    private void initRecyclerView() {
        mTermRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mTermRecyclerView.setLayoutManager(layoutManager);

        /*DividerItemDecoration divider = new DividerItemDecoration(
                mTermRecyclerView.getContext(), layoutManager.getOrientation()
        );
        mTermRecyclerView.addItemDecoration(divider);*/
    }

    private void initViewModel() {
        final Observer<List<Term>> termObserver =
            termEntities -> {
                termData.clear();
                termData.addAll(termEntities);

                if(mTermAdapter == null) {
                    mTermAdapter = new TermAdapter(termData, TermActivity.this);
                    mTermRecyclerView.setAdapter(mTermAdapter);
                } else {
                    mTermAdapter.notifyDataSetChanged();
                }
            };
        mTermViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        mTermViewModel.mTerms.observe(this, termObserver);
    }
}
