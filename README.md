# Cakes

An Android app that loads a list of cakes and shows each one's description in a popup.

## Running

1. Open the project in Android Studio.
2. Run the `app` configuration on an emulator or device.

Run the unit tests with:

```
./gradlew :app:testDebugUnitTest
```

## Stack

Kotlin, Jetpack Compose, MVVM, Hilt, Ktor, Coil, Coroutines.

## AI usage

- I used Claude (claude.ai) as a sounding board: for a quick UI mockup, to check library setup and Compose approaches, and to draft the cake list item and description dialog, which I then reviewed and adapted.
- I used Claude Code to split my work into logical Git commits, help write unit tests that pin down the app's behaviour once the architecture was in place, tidy up imports and dependencies, and draft this README.
