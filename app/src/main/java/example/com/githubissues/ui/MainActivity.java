package example.com.githubissues.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import example.com.githubissues.IssuesApplication;
import example.com.githubissues.adapters.DataAdapter;
import example.com.githubissues.R;
import example.com.githubissues.entities.Issue;
import example.com.githubissues.viewmodels.ListIssuesViewModel;

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private final String TAG = MainActivity.class.getName();
    private RecyclerView mRecyclerView;
    private ProgressDialog mDialog;
    private DataAdapter mAdapter;
    private EditText mSearchEditText;
    private ListIssuesViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Required by Dagger2 for field injection
        ((IssuesApplication) getApplication()).getAppComponent().inject(this);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ListIssuesViewModel.class);
        setupView();

        mSearchEditText.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String repo = mSearchEditText.getText().toString();
                if (repo.length() > 0) {
                    String[] query = repo.split("/");
                    if (query.length == 2) {
                        hideSoftKeyboard(MainActivity.this, v);
                        setProgress(true);
                        mViewModel.loadIssues(query[0], query[1]);
                    } else {
                        handleError(new Exception(
                                "Error wrong format of input. Required format owner/repository_name")
                        );
                    }
                } else {
                    handleError(new Exception(
                            "Repository name empty. Required format owner/repository_name")
                    );
                }
                return true;
            }
            return false;
        });

        // Handle changes emitted by LiveData
        mViewModel.getApiResponse().observe(this, apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse(apiResponse.getIssues());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setupView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mSearchEditText = (EditText) findViewById(R.id.et_search);

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
    }

    private void hideSoftKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    private void handleResponse(List<Issue> issues) {
        setProgress(false);
        if (issues != null && issues.size() > 0) {
            mAdapter.addIssues(issues);
        } else {
            mAdapter.clearIssues();
            Toast.makeText(
                    this,
                    "No issues found for the searched repository.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void handleError(Throwable error) {
        setProgress(false);
        mAdapter.clearIssues();
        Log.e(TAG, "error occured: " + error.toString());
        Toast.makeText(this, "Oops! Some error occured.", Toast.LENGTH_SHORT).show();
    }

    public void setProgress(boolean flag) {
        if (flag) {
            mDialog.show();
        } else {
            mDialog.dismiss();
        }
    }

}
