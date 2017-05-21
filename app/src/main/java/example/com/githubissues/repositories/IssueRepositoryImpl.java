package example.com.githubissues.repositories;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import example.com.githubissues.models.Issue;
import example.com.githubissues.retrofit.GithubApiService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by James on 5/21/2017.
 */

public class IssueRepositoryImpl implements IssueRepository {

    public static final String BASE_URL = "https://api.github.com/";
    private GithubApiService mGithubApiService;

    public IssueRepositoryImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        mGithubApiService = retrofit.create(GithubApiService.class);
    }

    @Override
    public Call<List<Issue>> getIssues(String owner, String repo) {
        return mGithubApiService.getIssues(owner, repo);
    }
}
