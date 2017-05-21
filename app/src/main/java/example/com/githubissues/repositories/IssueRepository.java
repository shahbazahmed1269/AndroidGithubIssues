package example.com.githubissues.repositories;

import java.util.List;

import example.com.githubissues.models.Issue;
import io.reactivex.Observable;
import retrofit2.Call;

/**
 * Created by James on 5/21/2017.
 */

public interface IssueRepository {
    Call<List<Issue>> getIssues(String owner, String repo);
}
