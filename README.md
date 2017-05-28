# AndroidGithubIssues
Android app to fetch issues for a given Github repository.
Please refer to the following post describing the code in details: [Getting started with android architecture components and MVVM](http://ahmedshahbaz.me/2017/05/26/getting-started-with-android-architecture-components/)

The app demonstrates the usage of MVVM architecture pattern using android architecture component library and LiveData and Repository pattern, inspired from Google recommended architecture pattern from I/O 17 .

![MVVM android architecture components ](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

**MVVM** pattern ensures that the UI data is persisted during configuration changes.
View (Activity or Fragment) uses LiveData to observe data changes and react accordingly.

**LiveData** is used to notify the View of the data chnages/ error which may happen in the ViewModel while fetching the data using API Service. Retrofit is used to make API calls.

**Repository** class abstracts the underlying datastores implementation (be it SQLite based or fetching from remote API or both) from the rest of the code. This way the code is decoupled from the underlying datastore implementations.

<img src="https://raw.githubusercontent.com/shahbazahmed1269/AndroidGithubIssues/master/art/github-issues-shot-1.png" alt="github-issues-shot-1" style="width: 480px;"/>



