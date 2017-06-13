# AndroidGithubIssues
Android app to fetch issues for a given GitHub repository.
Please refer to the following posts describing the code in details: 
1. [Getting started with android architecture components and MVVM](https://android.jlelse.eu/getting-started-with-android-architecture-components-and-mvvm-156a96a1bd05): Check corresponding branch **feature_mvvm** and **master**
2. [Getting started with android architecture components and MVVM Part 2- Dependency Injection](https://android.jlelse.eu/getting-started-with-android-architecture-components-and-mvvm-part-2-dependency-injection-334d54e7e7ac): Check branch **mvvm_part2_di**

The app demonstrates the usage of MVVM architecture pattern using android architecture component library and LiveData and Repository pattern, inspired from Google recommended architecture pattern from I/O 17 .

![MVVM android architecture components ](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

**MVVM** pattern ensures that the UI data is persisted during configuration changes.
View (Activity or Fragment) uses LiveData to observe data changes and react accordingly.

**LiveData** is used to notify the View of the data chnages/ error which may happen in the ViewModel while fetching the data using API Service. Retrofit is used to make API calls.

**Repository** class abstracts the underlying datastores implementation (be it SQLite based or fetching from remote API or both) from the rest of the code. This way the code is decoupled from the underlying datastore implementations.

### Libraries Used
1. [Android architecture components](https://developer.android.com/topic/libraries/architecture/index.html)
2. [Retrofit 2](http://square.github.io/retrofit/)
3. [Dagger 2](https://google.github.io/dagger/)

<img src="https://raw.githubusercontent.com/shahbazahmed1269/AndroidGithubIssues/master/art/github-issues-shot-1.png" alt="github-issues-shot-1" width="480"/>