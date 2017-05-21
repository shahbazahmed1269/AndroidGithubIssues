package example.com.githubissues.views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import example.com.githubissues.adapters.DataAdapter;
import example.com.githubissues.R;
import example.com.githubissues.models.Issue;
import example.com.githubissues.viewmodels.ListIssuesViewModel;

public class MainActivity extends LifecycleActivity {

    private final String TAG = MainActivity.class.getName();

    private RecyclerView mRecyclerView;
    private ProgressDialog mDialog;
    private DataAdapter mAdapter;
    private EditText mSearchEditText;
    private Button mSearchBtn;
    private ListIssuesViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(ListIssuesViewModel.class);
        setupView();
        // Handle changes emitted by LiveData
        viewModel.getIssues().observe(this, issues -> {
            handleResponse(issues);
        });
        viewModel.getError().observe(this, err -> {
            handleError(err);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setupView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mSearchEditText = (EditText) findViewById(R.id.et_search);
        mSearchBtn = (Button) findViewById(R.id.search_btn);

        // Setup Progress Dialog to show loading state
        mDialog = new ProgressDialog(MainActivity.this);
        mDialog.setIndeterminate(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setTitle(getString(R.string.progress_title));
        mDialog.setMessage(getString(R.string.progress_body));
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);

        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false)
        );
        mRecyclerView.hasFixedSize();
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(), LinearLayoutManager.VERTICAL
        );
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mAdapter = new DataAdapter(getLayoutInflater());
        mRecyclerView.setAdapter(mAdapter);

        mSearchBtn.setOnClickListener((View v) -> {
            String repo = mSearchEditText.getText().toString();
            hideSoftKeyboard(MainActivity.this, v);
            setProgress(true);
            viewModel.loadIssues(repo);
        });
    }

    private void hideSoftKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    private void handleResponse(List<Issue> issues) {
        setProgress(false);
        mAdapter.addIssues(issues);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void handleError(Throwable error) {
        setProgress(false);
        Log.e(TAG, "error occured: " + error.toString());
        Toast.makeText(this, "Oops! Some error occured.", Toast.LENGTH_SHORT).show();
    }

    public void setProgress(boolean flag) {
        if (flag) {
            MainActivity.this.runOnUiThread(() -> mDialog.show());
        } else {
            MainActivity.this.runOnUiThread(() -> mDialog.dismiss());
        }
    }

}
