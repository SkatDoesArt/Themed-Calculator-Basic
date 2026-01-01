# 🧮 SkatCalculator PWA

---

A modern, elegant, and lightning-fast calculator developed with **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**. This project harnesses the power of **Kotlin/Wasm** to deliver a smooth experience directly in your browser as a Progressive Web App (PWA).

---

## 🌟 Project Summary
This project demonstrates the capabilities of code sharing via Kotlin. The application adapts dynamically based on the device: it features a stylized immersive background on desktop and an "Edge-to-Edge" full-screen interface on mobile for total immersion.

* **Technology**: Kotlin/Wasm (WebAssembly) for native-like performance on the Web.
* **Interface**: Fully customized Material Design 3.
* **Portability**: Installable on iOS, Android, Windows, and Mac thanks to PWA support.

---

## ✨ Features
* **Fluid Calculations**: Standard operations, percentage handling, and sign inversion (+/-).
* **Theme Persistence**: Your favorite theme is saved locally and automatically restored upon the next launch.
* **Persistent History**: A side panel (Drawer) keeps your last 30 calculations, even after closing the application.
* **Precise Control (AC/C)**:
    * **Single Click on C**: Deletes the last character entered.
    * **Long Press on AC/C**: Full reset of the calculator.
* **Adaptive Display**: Perfect centering on PC and borderless full-screen mode on mobile.

---

## 🚀 Development & Build Commands

Here are the essential commands to manage and test the project:

### 🛠️ Local Testing (Development)
To launch the app with Hot Reload and debug tools:
```bash
./gradlew wasmJsBrowserDevelopmentRun --no-build-cache
```

### 📦 Build for Production
To generate optimized files for deployment (GitHub Pages, Vercel, etc.):
```bash
./gradlew wasmJsBrowserDistribution --no-build-cache
```
Note: The --no-build-cache option is recommended to ensure every build includes your latest changes without conflicts from old cache.

---
## 💡 Useful Tips

### 📱 PWA Installation
The application is a certified PWA. To install it:
- On iOS (Safari): Tap "Share" then "Add to Home Screen".
- On Android (Chrome): Follow the installation banner or click the three dots > "Install App".
- On PC: Click the installation icon in the browser address bar.

### ⚠️ File Management on Windows

Kotlin Multiplatform development generates very deep file structures (especially in .gradle), which can cause "Path too long" errors on Windows.
- Tip: Never manually copy the build/ folder between Windows directories to avoid system lockups.
- Git: The .gitignore file is configured to ignore these heavy files and avoid failures during GitHub pushes.
