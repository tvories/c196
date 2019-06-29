package com.taylorvories.c196;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.taylorvories.c196.models.Term;
import com.taylorvories.c196.ui.TermAdapter;
import com.taylorvories.c196.viewmodel.MainViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mDrawer;
    NavigationView mNavigationView;

    private List<Term> termData = new ArrayList<>();
    private TermAdapter mAdapter;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        initViewModel();
        setStatusNumbers();
    }

    private void setStatusNumbers() {
        // Set text for current status
        TextView termStatus = findViewById(R.id.status_terms_count);
        TextView courseStatus = findViewById(R.id.status_courses_count);
        TextView assessmentStatus = findViewById(R.id.status_assessments_count);

        termStatus.setText("3");
        courseStatus.setText("7");
        assessmentStatus.setText("10");
    }

    private void initViewModel() {
        final Observer<List<Term>> termObserver =
            termEntities -> {
                termData.clear();
                termData.addAll(termEntities);
                if (mAdapter == null) {
                    mAdapter = new TermAdapter(termData, MainActivity.this);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            };
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.mTerms.observe(this, termObserver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_sample_data) {
            addSampleData();
            return true;
        } else if (id == R.id.action_delete_all) {
            deleteAllTerms();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllTerms() {
        mViewModel.deleteAllTerms();
    }

    private void addSampleData() {
        mViewModel.addSampleData();
    }

    public void showTerms(View view) {
        //TODO: Write method
    }

    public void showCourses(View view) {
        //TODO: Write method
    }

    public void showAssessments(View view) {
        //TODO: Write Method
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
