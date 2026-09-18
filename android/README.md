# USA VisaCoach - Android Application

Production-ready Native Android Application built with **Kotlin**, **Jetpack Compose**, **Material 3**, and **Clean Architecture**.

---

## 🛠️ Requirements & Environment Setup
- **Android Studio**: Iguana (2023.2.1), Jellyfish (2023.3.1), Koala (2024.1.1), or Ladybug (2024.2.1)+
- **JDK**: Java 17 (OpenJDK 17 or Android Studio Embedded JBR 17)
- **Android SDK**:
  - `compileSdk`: **34** (Android 14)
  - `minSdk`: **26** (Android 8.0 Oreo)
  - `targetSdk`: **34** (Android 14)
- **Gradle**: 8.4+
- **Android Gradle Plugin (AGP)**: 8.3.2

---

## 🚀 How to Build APK in Android Studio

### Method 1: Using the Android Studio GUI (Recommended)

1. **Open the Project**:
   - Launch **Android Studio**.
   - Click **Open** (or `File > Open...`).
   - Select the `android/` directory of this repository and click **OK**.
2. **Gradle Sync**:
   - Wait for Android Studio to index and run the initial Gradle Sync.
   - If prompted for JDK version, navigate to `Settings (Preferences) > Build, Execution, Deployment > Build Tools > Gradle` and ensure **Gradle JDK** is set to **Java 17** (Embedded JBR 17).
3. **Build Debug APK**:
   - In the top menu bar, click:
     ```
     Build > Build Bundle(s) / APK(s) > Build APK(s)
     ```
   - Android Studio will compile the code, process KSP annotations (Hilt & Room), and generate the APK.
   - Once finished, a notification popup will appear at the bottom right:
     *"APK(s) generated successfully for 1 module: app"*
   - Click **locate** inside the notification to open the folder containing `app-debug.apk`.
   - File path: `android/app/build/outputs/apk/debug/app-debug.apk`

4. **Build Release APK (Signed)**:
   - In the menu bar, click:
     ```
     Build > Generate Signed Bundle / APK...
     ```
   - Select **APK** and click **Next**.
   - Select an existing KeyStore or click **Create new...** to create a signing key.
   - Choose the `release` build variant and select V1/V2 signature checkboxes.
   - Click **Finish**. The signed release APK will be in:
     `android/app/build/outputs/apk/release/app-release.apk`

---

### Method 2: Using the Terminal / Command Line

Open your terminal, navigate to the `android/` directory:

```bash
cd android
```

#### Build Debug APK:
```bash
# On Linux / macOS
./gradlew assembleDebug

# On Windows PowerShell / Command Prompt
.\gradlew.bat assembleDebug
```

The APK will be generated at:
```
android/app/build/outputs/apk/debug/app-debug.apk
```

#### Run Unit Tests:
```bash
./gradlew testDebugUnitTest
```

#### Install Directly onto Connected Device or Emulator:
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```
Or directly via Gradle:
```bash
./gradlew installDebug
```

---

## 📱 App Capabilities & Permissions
- **Record Audio**: Required for the real-time AI interview voice stream (`android.permission.RECORD_AUDIO`).
- **Network / WebSocket**: Communicates with the Spring Boot WebFlux backend via bidirectional OkHttp WebSocket.
- **Local Persistence**: Room database caches questions and mock interview results; Encrypted DataStore persists JWT tokens.
