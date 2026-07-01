# TaskMaster

A complete Android personal task management application built with Kotlin 2.0+, Jetpack Compose, Material Design 3, Hilt, MVVM, and Firebase (Authentication & Firestore).

## Features

- **Firebase Authentication**: Email/Password login, registration, and password recovery.
- **Firestore Database**: Real-time CRUD operations for tasks (using `addSnapshotListener` and `callbackFlow`).
- **Data Privacy**: Firestore rules ensure that each user can only read/write their own tasks.
- **Modern UI**: Jetpack Compose and Material Design 3 with dynamic colors and dark/light mode support.
- **Architecture**: MVVM with StateFlow, Dependency Injection via Hilt, Compose Navigation.

## Prerequisites

- Android Studio Koala (or the most recent version that supports Gradle 8.7+ and Kotlin 2.0).
- A Firebase project.

## Setup Instructions

### 1. Firebase Configuration

Since this app uses Firebase, you must connect it to a Firebase project:

1. Go to the [Firebase Console](https://console.firebase.google.com/).
2. Create a new project or select an existing one.
3. Add an Android app to the project:
   - **Android package name**: `com.example.taskmaster`
4. Download the `google-services.json` file.
5. Place the `google-services.json` file in the `app/` directory of this project (`d:\3IASPM\Gemini\TaskMaster\app\google-services.json`).

### 2. Enable Firebase Services

In the Firebase Console, you need to enable Authentication and Firestore:

**Authentication**:
1. Go to **Authentication** > **Sign-in method**.
2. Enable **Email/Password**.

**Firestore Database**:
1. Go to **Firestore Database** > **Create database**.
2. Start in **Production mode** (or test mode, but we will apply custom rules).
3. Go to the **Rules** tab and paste the contents of `firestore.rules`:
   ```javascript
   rules_version = '2';
   service cloud.firestore {
     match /databases/{database}/documents {
       match /tasks/{taskId} {
         allow read, write: if request.auth != null && request.auth.uid == resource.data.userId;
         allow create: if request.auth != null && request.auth.uid == request.resource.data.userId;
       }
     }
   }
   ```
4. Publish the rules.

### 3. Build and Run

1. Open Android Studio.
2. Select **File > Open** and navigate to the `TaskMaster` folder (`d:\3IASPM\Gemini\TaskMaster`).
3. Allow Gradle to sync. (It will download Kotlin 2.0, Compose, and Firebase dependencies).
4. Run the app on an Emulator or a physical Android device (minSdk 26+).
