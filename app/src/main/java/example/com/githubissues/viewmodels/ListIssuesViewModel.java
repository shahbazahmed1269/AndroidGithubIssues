package example.com.githubissues.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import example.com.githubissues.entities.ApiResponse;
import example.com.githubissues.repositories.IssueRepository;
import example.com.githubissues.repositories.IssueRepositoryImpl;

/**
 * Created by James on 5/21/2017.
 */

public class ListIssuesViewModel extends ViewModel {

    private MediatorLiveData<ApiResponse> res;
    private IssueRepository repository;

    public ListIssuesViewModel() {
        res = new MediatorLiveData<>();
        repository = new IssueRepositoryImpl();
    }

    @NonNull
    public LiveData<ApiResponse> getRes() {
        return res;
    }


    public LiveData<ApiResponse> loadIssues(@NonNull String user, String repo) {
        res.addSource(repository.getIssues(user, repo), apiResponse -> res.setValue(apiResponse));
        return res;
    }

}