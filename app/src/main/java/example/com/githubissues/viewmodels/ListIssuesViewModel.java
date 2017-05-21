package example.com.githubissues.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import example.com.githubissues.models.Issue;
import example.com.githubissues.repositories.IssueRepository;
import example.com.githubissues.repositories.IssueRepositoryImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 5/21/2017.
 */

public class ListIssuesViewModel extends AndroidViewModel {
    private final String TAG = ListIssuesViewModel.class.getName();
    MutableLiveData<List<Issue>> issues;
    MutableLiveData<Throwable> error;
    IssueRepository repository;

    public ListIssuesViewModel(@NonNull Application app) {
        super(app);
        issues = new MutableLiveData<>();
        error = new MutableLiveData<>();
        repository = new IssueRepositoryImpl();
    }


    @Nullable
    public LiveData<List<Issue>> getIssues() {
        return issues;
    }

    @Nullable
    public LiveData<Throwable> getError() {
        return error;
    }

    public void loadIssues(@NonNull String repo) {
        String[] parts = repo.split("/");
        if (parts.length == 2) {
            Call<List<Issue>> issuesCall = repository.getIssues(parts[0], parts[1]);
            issuesCall.enqueue(new Callback<List<Issue>>() {

                @Override
                public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {
                    List<Issue> issues = response.body();
                    Log.d(TAG, "issues size: " + issues.size());
                    setIssues(issues);
                }

                @Override
                public void onFailure(Call<List<Issue>> call, Throwable t) {
                    // TODO: Handle error in retrieving issues`
                    Log.e(TAG, "Error occured: " + t.toString());
                    setError(t);
                }
            });
        } else {
            setError(new Exception(
                    "Error wrong format of input. Required format owner/repository_name")
            );
        }
    }

    public void setIssues(@NonNull final List<Issue> issues) {
        this.issues.setValue(issues);
    }

    private void setError(@NonNull final Throwable err) {
        this.error.setValue(err);
    }
}