
# My Android Kotlin Project

This project is an Android application written in Kotlin, following best practices like MVVM architecture, using dependency injection with Hilt, and implementing unit tests with code coverage using Kover.

## Project Structure

The project is organized into three main layers:

1. **Presentation Layer**
   - Contains UI components and ViewModel classes.
   - Uses Jetpack Compose for UI.
   - ViewModels handle UI-related logic and interact with UseCases.

2. **Domain Layer**
   - Contains business logic.
   - Includes UseCase classes that encapsulate a single action or piece of logic.
   - Defines Repository interfaces that abstract the data layer.

3. **Data Layer**
   - Handles data management (local and remote).
   - Includes Repository implementations, Room DAOs, and Retrofit API services.
   - Data is fetched from a remote source via Retrofit or stored/retrieved locally using Room.

## Key Technologies

- **Kotlin**: The programming language used throughout the project.
- **Jetpack Compose**: For building the UI declaratively.
- **Hilt**: For dependency injection.
- **Room**: For local database storage.
- **Retrofit**: For making network requests.
- **MockK**: For unit testing and mocking dependencies.
- **Kover**: For generating code coverage reports.

## Setup

### Prerequisites

- **Android Studio**: Make sure you have the latest version of Android Studio installed.
- **JDK**: The project uses the JDK configured in Android Studio. No manual setup of `org.gradle.java.home` is necessary.

### Clone the Repository

```bash
git clone https://github.com/khiem-huynhthien/Demo-MVVM-Android.git
cd Demo-MVVM-Android
```

### Build the Project

Open the project in Android Studio and sync the Gradle files. Alternatively, you can build the project via the command line:

```bash
./gradlew assembleDebug
```

### Running the App

To run the app on an emulator or connected device, use Android Studio's run configuration or the following command:

```bash
./gradlew installDebug
```

## Testing and Code Coverage

### Running Unit Tests

The project includes unit tests for ViewModels, UseCases, and Repositories. You can run the tests using:

```bash
./gradlew testDebugUnitTest
```

### Code Coverage with Kover

Kover is used to generate code coverage reports. The coverage is focused on `ViewModel`, `UseCase`, and `Repository` classes.

To generate the coverage report:

```bash
./gradlew koverReport
```

The HTML report will be generated in `build/reports/kover/html/index.html`.

### Enforcing Code Coverage

You can enforce minimum coverage thresholds with:

```bash
./gradlew koverVerify
```

## Known Issues

- Ensure that the JDK path is correctly set in Android Studio, as the project relies on this configuration for consistent Gradle builds.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
