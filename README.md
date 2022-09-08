# Solution Design and Libraries
This project is based on MVVM (Model-View-ViewModel) architecture. It uses following libraries :
1. Koin -  for Dependency injection
2. Jetpack Compose -  to display UI
3. Coroutines -  for performing network calls asynchronously
4. Retrofit -  as HTTP client
5. Kotlin libraries -  for writing code
6. Junit5 -  for unit testing
7. Arrow Core -  for error handling

Test Driven Development approach has been followed for the creation of the project. JUnit tests have been added for view-model and repositories. User Interface tests have been added for Compose layouts.

# How to run the project
The following steps are needed to run the project:
1. Update Android Studio to the latest version (Android Studio ChipMunk)
2. Point JDK to Java 11  - Go to File -> Project Structure -> SDK location -> Gradle settings -> Point Gradle JDK to 11
3. Extract the zip file and open the project in Android Studio
4. Run the kotlin_app module