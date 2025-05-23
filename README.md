# RepoScope-Droid

**RepoScope-Droid** is a read-only Android application that lets users explore Git repositories directly on their mobile devices. Built with modern Android development practices, this app emphasizes an offline-first approach using Jetpack Compose and a modular, scalable architecture.

---

## 🔧 Project Structure

The project is divided into several modules:

* **`app/`**
  The main application module. It initializes the app and integrates feature and core modules. Built entirely using Jetpack Compose.

* **`core/`**
  Contains shared components and utilities such as base classes, constants, themes, and helpers used across all feature modules. This ensures a clean architecture and avoids redundancy.

* **`feature/repositories/`**
  Encapsulates everything related to displaying and browsing repositories. It includes repository models and Compose UI elements for listing and viewing details.

* **`buildSrc/`**
  Contains centralized dependency and plugin management to ensure consistent versions and streamlined builds across modules.

---

## 🧩 Core Module Highlights

The `core` module serves as the foundation for consistency and reusability across the application. It includes:

* **UI Components**
  Reusable Jetpack Compose components and theming.

* **Utilities**
  Helpers for things like date formatting and error handling.

* **Architecture Components**
  Base ViewModels using Kotlin Coroutines and StateFlow (no LiveData), along with common result/error wrappers.

* **Constants & Configuration**
  Centralized location for app-wide constants and build config keys.

This abstraction ensures that feature modules are lean and focused solely on their domain logic and UI.

---

## ⚙️ Features

* 📁 **Read-Only Repository Browser**
  Browse repositories with details, files, and metadata — strictly read-only; no push, pull, or write operations.

* ⚡ **Offline-First**
  Designed to work seamlessly without internet. Data is cached locally using Room for a fast, reliable experience.

* 🧱 **Modular Codebase**
  Easily maintainable and scalable architecture with clearly defined module boundaries.

* 🎨 **Jetpack Compose UI**
  Built entirely with Jetpack Compose for a modern, declarative UI experience — no Fragments or XML layouts.

---

## 🚀 Getting Started

### Prerequisites

* **Android Studio** (Hedgehog or newer recommended)
* **JDK 11+**

### Installation

```bash
git clone -b development https://github.com/antoniokranjcina/RepoScope-Droid.git
```

Open the project in Android Studio and let Gradle sync. Then build and run the app on an emulator or device.

---

## 📦 Key Technologies

* **Kotlin + Coroutines + StateFlow** – Asynchronous programming without LiveData
* **Jetpack Compose** – Modern UI toolkit
* **Room** – Local database with offline-first persistence
* **Koin** – Lightweight dependency injection
* **Retrofit** – REST API client for fetching remote repository data
* **Modular Architecture** – Encourages separation of concerns and easier testing

---

## 🤝 Contributing

Want to contribute? Awesome!

1. Fork the repository.
2. Create a feature branch:

   ```bash
   git checkout -b feature/my-new-feature
   ```
3. Commit your changes:

   ```bash
   git commit -m "Add my new feature"
   ```
4. Push to your branch:

   ```bash
   git push origin feat/my-new-feature
   ```
5. Open a Pull Request targeting the `development` branch.

---

## 📄 License

This project is licensed under the MIT License.
