# Namma Kathey - ನಮ್ಮ ಕಥೆ

**Android App: Regional Hero Storybook for Karnataka**

## About the App

Namma Kathey is an interactive history app for children that tells stories of local freedom fighters, poets, and social reformers from Karnataka's districts. It builds **Local Pride** and **Character** through storytelling.

## Features

### 🗺️ District Map
- Tap any Karnataka district to explore its local heroes
- 7+ districts with vibrant color-coded cards
- Supports Kannada and English

### 📖 Illustrated Storybook (ViewPager2)
- Swipe-based storybook with beautiful emoji illustrations
- 3 pages per hero in simple language
- Full Kannada and English support

### 🔊 Text-To-Speech
- Tap "Listen" to hear the story read aloud
- Supports Kannada TTS (if device has Kannada TTS installed)
- Falls back to English TTS

### 📝 Hero Quiz
- 3 multiple-choice questions per hero
- Answer 2/3 correctly to earn a Heritage Badge
- Beautiful quiz UI with correct/wrong feedback

### 🏆 Heritage Badges (Profile)
- Badges saved locally in the user's profile
- View all earned badges with date and score
- Persistent across app restarts

### 📍 Statue Finder
- Shows nearest memorial/statue location
- Opens Google Maps for directions
- Works even offline with coordinates

### 🌐 Language Toggle
- Instantly switch between Kannada (ಕನ್ನಡ) and English
- All content switches — hero names, stories, quiz questions, UI labels

## Heroes Included

| District | Hero | Category |
|----------|------|----------|
| Belagavi | Sangolli Rayanna | Freedom Fighter |
| Belagavi | Kittur Rani Chennamma | Queen & Freedom Fighter |
| Mysuru | Tipu Sultan | Freedom Fighter & Ruler |
| Dharwad | Basavanna | Social Reformer & Philosopher |
| Uttara Kannada | Abbakka Rani | Sea Queen & Freedom Fighter |
| Bengaluru | Kempegowda I | Founder & Builder |
| Raichur | Krishnadevaraya | Emperor & Poet |
| Hassan | Akka Mahadevi | Poet Saint & Social Reformer |
| Tumakuru | Dr. Siddalingaiah | Poet & Social Activist |

## Setup Instructions

### Requirements
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17+
- Android SDK 34
- Minimum Android 7.0 (API 24)

### Steps
1. Unzip `NammaKathey.zip`
2. Open **Android Studio** → **File → Open** → Select the `NammaKathey` folder
3. Wait for Gradle sync to complete (first time may take 3-5 minutes)
4. Connect an Android device or start an emulator
5. Click **Run ▶** or press `Shift+F10`

### If Gradle sync fails
- Go to **File → Settings → Build → Gradle** and set JDK to 17
- Check internet connection (Gradle downloads dependencies)
- Try **File → Invalidate Caches → Invalidate and Restart**

## Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Kotlin |
| Min SDK | API 24 (Android 7.0) |
| Target SDK | API 34 (Android 14) |
| UI | Material Design 3, ViewPager2, RecyclerView |
| Data | Local JSON (raw resources) |
| Persistence | SharedPreferences + Gson |
| TTS | Android TextToSpeech API |
| Maps | Google Maps Intent |
| Build | Gradle 8.2, AGP 8.2.0 |

## Data Storage

All stories and quiz data are stored in:
`app/src/main/res/raw/heroes_data.json`

Add more heroes by editing this JSON file — no code changes needed!

## Architecture

- **Activities**: SplashActivity → MainActivity → HeroListActivity → StoryActivity → QuizActivity / ProfileActivity / StatueFinderActivity
- **Adapters**: DistrictAdapter, HeroAdapter, StoryPageAdapter, BadgeAdapter
- **Models**: District, Hero, StoryPage, QuizQuestion, Badge
- **Utils**: DataManager (JSON loading), PrefsManager (SharedPreferences), TTSManager (Text-to-Speech)

---
*MindMatrix VTU Internship Program — Project Title 05*
*Android App Development using GenAI - Namma Kathey (National Pride)*
