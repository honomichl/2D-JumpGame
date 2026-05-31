# 2D_JumpGame
A lightweight 2D rhythm-based platformer built in Java using Swing and AWT.
The game features grid-based level generation from JSON configurations, customizable user settings, a layered UI system, and custom particle mechanics upon player death.

---

## 🚀 Features

* **Custom Physics & Movement:** Smooth jumping, custom gravity acceleration, and precise box/polygon collision handling.
* **Dynamic Level Loading:** Levels are parsed on the fly from a JSON data source, instantly translating character grids into visual map structures.
* **Layered Menu System:** Fully responsive screens powered by a custom factory (`UIFactory`) and managed seamlessly by a singular window frame controller.
* **Theme Customization:** Global application state allowing real-time switching between Light and Dark visual themes.
* **Visual Effects:** Explosion particle physics generated dynamically at the player's coordinates upon running into obstacles.

---

## 🛠️ Tech Stack & Architecture

* **Language:** Java 24
* **Graphics & GUI:** Java Swing & AWT (`JPanel`, `JLayeredPane`, `Graphics2D`)
* **Data Parsing:** Google Gson (for JSON map reading)
* **Design Patterns:** Factory Pattern (`UIFactory`), Manager Pattern (`ScreenManager`), Utility Class (`Collisions`)

---

## 📂 Project Structure

src/Game/
│
├── GameObjects/         # In-game entities (Player, Spike, Block, Floor, End)
├── Screens/             # UI Panels (WelcomeScreen, SettingsScreen, GameScreen, VictoryScreen)
│
├── AppSettings.java     # Global configuration, themes, and preferences
├── Collisions.java      # Static collision physics engine
├── DeathParticle.java   # Explosion particle physics
├── LevelReader.java     # JSON parsing and grid generation
├── Main.java            # Main application entry point
├── ScreenManager.java   # View layout manager and screen swapping controller
└── UIFactory.java       # Centralized component styler factory

---

🎮 How to Play
Launch the Game: Run the Main.java file to open the application.

Navigate: Use the Main Menu to start the game, modify your profile in Settings, or exit.

Controls:

SPACEBAR — Make the cube jump.

ESCAPE — Toggle the Pause Menu (Resume, Reset Level, Main Menu).

Goal: Avoid the spikes and block walls, check your real-time progress HUD at the top, and reach the gray finish zone to win!

---

📦 Installation & Setup
Prerequisites
Java Development Kit (JDK) 17 or higher

Google Gson library added to your dependencies

Running via Terminal
Clone the repository:

git clone [https://github.com/YOUR_USERNAME/YOUR_REPOSITORY_NAME.git](https://github.com/YOUR_USERNAME/YOUR_REPOSITORY_NAME.git)

Run the main class:

java Game.Main

---

📝 Author
Filip Honomichl 

© 2026 Filip Honomichl. All rights reserved.
