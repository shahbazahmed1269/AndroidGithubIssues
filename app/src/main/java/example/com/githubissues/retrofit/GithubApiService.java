package example.com.githubissues.retrofit;

import java.util.List;

import example.com.githubissues.models.Issue;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by shahbaz on 14/05/17.
 */

public interface GithubApiService {
    @GET("/repos/{owner}/{repo}/issues")
    Observable<List<Issue>> getIssues(@Path("owner") String owner, @Path("repo") String repo);
}
