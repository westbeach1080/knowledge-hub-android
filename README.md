# Knowledge Hub (Android)

A standalone native Android Java application with search, category filters, bookmarks and external learning links. Inspired by [trimstray/the-book-of-secret-knowledge](https://github.com/trimstray/the-book-of-secret-knowledge), licensed MIT. This project is not affiliated with the original maintainer.

## Build an APK without Android Studio
1. Create a **new** GitHub repository (e.g. `knowledge-hub-android`). Do not push these files to the original trimstray repository.
2. Upload the project contents, including `.github/workflows/android-apk.yml` (hidden `.github` directory).
3. Open **Actions** → **Build Knowledge Hub APK** → **Run workflow**.
4. When green, open the run and download **KnowledgeHub-debug-apk** from **Artifacts**.
5. Unzip artifact and install `app-debug.apk` on Android.

A debug APK is for testing, not a Play Store release. Publishing to Google Play requires a signed release AAB, store assets and compliance review.

Requirements: Android 8.0+, Android Gradle Plugin 8.7.3, Gradle 8.9, JDK 17. No external runtime libraries.
