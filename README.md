# Smart Reply AI

Android-only WhatsApp Smart Reply Assistant (Kotlin + Jetpack Compose).

## Setup

1. Open the project in Android Studio Ladybug or newer.
2. Sync Gradle.
3. Add your Gemini API key in **Settings → AI Settings**.
4. Enable **Notification Access** and optional **Display over other apps** in **Permissions**.

## Build

```bash
./gradlew assembleDebug
```

## Package

`com.myai.smartreplyai`

## Play Store Safety

- No auto-send — user copies replies manually.
- Notification Listener reads alerts locally only.
- AI calls go directly to Google Gemini API from the device.
