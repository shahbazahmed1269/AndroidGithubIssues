package example.com.githubissues.entities;

import java.util.List;

/**
 * Created by James on 5/24/2017.
 */

public class ApiResponse {
    private List<Issue> issues;
    private Throwable error;

    public ApiResponse(List<Issue> issues) {
        this.issues = issues;
        this.error = null;
    }

    public ApiResponse(Throwable error) {
        this.error = error;
        this.issues = null;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public Throwable getError() {
        return error;
    }
}
