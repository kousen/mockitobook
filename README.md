# mockitobook
Code examples for the book _Mockito Made Clear_,
published by Pragmatic Programmers.

See [the book page](https://pragprog.com/titles/mockito/mockito-made-clear/) for more information.

## Running the code
To run the code, use the included Gradle wrapper (the `gradlew` scripts for Un*x and Windows) and execute either the `build` or `test` tasks. You can also run individual tests via Gradle, or just load them into your preferred IDE.

This project uses [Gradle version catalogs](https://docs.gradle.org/current/userguide/platforms.html#sub:central-declaration-of-dependencies), which require Gradle 7.4 or higher. The included wrapper is higher than that. The dependency versions are inside the `libs.versions.toml` file in the `gradle` directory, which are used inside `build.gradle`.
