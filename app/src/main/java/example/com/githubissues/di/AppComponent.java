package example.com.githubissues.di;

import javax.inject.Singleton;

import dagger.Component;
import example.com.githubissues.ui.MainActivity;

/**
 * Created by James on 6/7/2017.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

}