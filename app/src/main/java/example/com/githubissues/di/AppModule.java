package example.com.githubissues.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.com.githubissues.api.GithubApiService;
import example.com.githubissues.repositories.IssueRepository;
import example.com.githubissues.repositories.IssueRepositoryImpl;
import example.com.githubissues.viewmodels.ListIssuesViewModel;
import example.com.githubissues.viewmodels.ViewModelFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by James on 6/7/2017.
 */

@Module
public class AppModule {
    public static final String BASE_URL = "https://api.github.com/";

    @Provides
    @Singleton
    GithubApiService provideGithubApiService() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(GithubApiService.class);
    }

    @Provides
    @Singleton
    IssueRepository provideIssueRepository(IssueRepositoryImpl repository) {
        return repository;
    }

    @Provides
    ViewModel provideListIssuesViewModel(ListIssuesViewModel viewModel) {
        return viewModel;
    }

    @Provides
    ViewModelProvider.Factory provideListIssuesViewModelFactory(
            ViewModelFactory factory
    ) {
        return factory;
    }

}
