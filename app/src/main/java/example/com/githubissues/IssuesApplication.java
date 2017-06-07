package example.com.githubissues;

import android.app.Application;

import example.com.githubissues.di.AppComponent;
import example.com.githubissues.di.DaggerAppComponent;

/**
 * Created by James on 6/7/2017.
 */

public class IssuesApplication extends Application {
    AppComponent mAppComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder().build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
