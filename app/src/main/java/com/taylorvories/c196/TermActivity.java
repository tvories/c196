package com.taylorvories.c196;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.taylorvories.c196.models.Term;
import com.taylorvories.c196.ui.RecyclerContext;
import com.taylorvories.c196.ui.TermAdapter;
import com.taylorvories.c196.viewmodel.TermViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mDrawer;
    NavigationView mNavigationView;


    @BindView(R.id.term_recycler_view)
    RecyclerView mTermRecyclerView;

    @OnClick(R.id.fab)
    void fabClickHandler() {
        Intent intent = new Intent(this, TermEditActivity.class);
        startActivity(intent);
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
        mDrawer = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.navigation_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.nav_open, R.string.nav_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        // Tells icons to use full color
        mNavigationView.setItemIconTintList(null);
        mNavigationView.setNavigationItemSelectedListener(this);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    private void initRecyclerView() {
        mTermRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mTermRecyclerView.setLayoutManager(layoutManager);
    }

    private void initViewModel() {
        final Observer<List<Term>> termObserver =
            termEntities -> {
                termData.clear();
                termData.addAll(termEntities);

                if(mTermAdapter == null) {
                    mTermAdapter = new TermAdapter(termData, TermActivity.this, RecyclerContext.PARENT);
                    mTermRecyclerView.setAdapter(mTermAdapter);
                } else {
                    mTermAdapter.notifyDataSetChanged();
                }
            };
        mTermViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        mTermViewModel.mTerms.observe(this, termObserver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        int duration = Toast.LENGTH_SHORT;
        Toast toast;

        switch (id) {
            case R.id.nav_terms:
                toast = Toast.makeText(this, "Terms pressed", duration);
                toast.show();
                break;
            case R.id.nav_courses:
                toast = Toast.makeText(this, "Courses pressed", duration);
                toast.show();
                break;
            case R.id.nav_assessments:
                toast = Toast.makeText(this, "Assessments pressed", duration);
                toast.show();
                break;
            case R.id.nav_mentors:
                toast = Toast.makeText(this, "Mentors pressed", duration);
                toast.show();
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
