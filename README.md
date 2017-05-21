# AndroidGithubIssues
Android app to fetch issues for a given Github repository.

Idea is to demonstrate the usage of MVVM architecture pattern using android architecture component library and LiveData.
Retrofit is used to make API calls.

MVVM ensures that the UI data is persisted during configuration changes.
View (Activity or Fragment) uses LiveData to observe data changes and react accordingly.

LiveData is also used to notify the View of the error which may happen in the ViewModel while fetching the data using API Service.  
