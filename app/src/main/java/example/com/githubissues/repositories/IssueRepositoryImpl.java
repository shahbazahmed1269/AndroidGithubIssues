package example.com.githubissues.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import example.com.githubissues.entities.ApiResponse;
import example.com.githubissues.entities.Issue;
import example.com.githubissues.api.GithubApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 5/21/2017.
 */

public class IssueRepositoryImpl implements IssueRepository {
    @Inject
    GithubApiService apiService;

    @Inject
    public IssueRepositoryImpl() {
    }

    public LiveData<ApiResponse> getIssues(String owner, String repo) {
        final MutableLiveData<ApiResponse> liveData = new MutableLiveData<>();
        Call<List<Issue>> call = apiService.getIssues(owner, repo);
        call.enqueue(new Callback<List<Issue>>() {
            @Override
            public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {
                liveData.setValue(new ApiResponse(response.body()));
            }

            @Override
            public void onFailure(Call<List<Issue>> call, Throwable t) {
                liveData.setValue(new ApiResponse(t));
            }
        });
        return liveData;
    }

}
