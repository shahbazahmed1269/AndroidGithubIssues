package example.com.githubissues.views;

import android.app.ProgressDialog;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import adapters.DataAdapter;
import example.com.githubissues.R;
import example.com.githubissues.models.Issue;
import example.com.githubissues.retrofit.GithubApiService;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://api.github.com/";

    private RecyclerView mRecyclerView;
    private ProgressDialog mDialog;
    private DataAdapter mAdapter;
    private EditText mSearchEditText;
    private Button mSearchBtn;
    private GithubApiService mGithubApiService;
    private CompositeDisposable mDisposables;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();

        mDisposables = new CompositeDisposable();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        mGithubApiService = retrofit.create(GithubApiService.class);
    }

    @Override
    protected void onDestroy() {
        // Dispose the subscriptions
        mDisposables.dispose();
        mGithubApiService = null;
        super.onDestroy();
    }

    private void setupView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mSearchEditText = (EditText) findViewById(R.id.et_search);
        mSearchBtn = (Button) findViewById(R.id.search_btn);
        // setup Progress Dialog to show loading state
        mDialog=new ProgressDialog(MainActivity.this);
        mDialog.setIndeterminate(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setTitle(getString(R.string.progress_title));
        mDialog.setMessage(getString(R.string.progress_body));
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);

        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.hasFixedSize();
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mAdapter=new DataAdapter(getLayoutInflater());

        mSearchBtn.setOnClickListener((View v) -> {
            String repo = mSearchEditText.getText().toString();
            fetchIssues(repo);
        });
    }

    private void fetchIssues(String repo) {
        String[] parts = repo.split("/");
        if(parts.length > 1) {
            Observable<List<Issue>> issuesObservable = getIssuesObservable(parts[0], parts[1]);
            mDisposables.add(issuesObservable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(MainActivity.this::handleResponse,
                            MainActivity.this::handleError));
        } else {
            Toast.makeText(this, "Please enter repo name with user name", Toast.LENGTH_SHORT).show();
        }
    }

    private Observable<List<Issue>> getIssuesObservable(String user, String repository) {
        Observable<List<Issue>> issues = mGithubApiService.getIssues(user, repository);
        setProgress(true);
        return issues;
    }

    private void handleResponse(List<Issue> issues) {
        setProgress(false);
        mAdapter.addIssues(issues);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void handleError(Throwable error) {
        setProgress(false);
        Log.e("MainActivity1", "Error occured: " + error.toString());
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
