package example.com.githubissues.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;

/**
 * Created by James on 6/7/2017.
 */

public class ViewModelFactory implements ViewModelProvider.Factory {
    private ListIssuesViewModel mViewModel;

    @Inject
    public ViewModelFactory(ListIssuesViewModel viewModel) {
        this.mViewModel = viewModel;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListIssuesViewModel.class)) {
            return (T) mViewModel;
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
