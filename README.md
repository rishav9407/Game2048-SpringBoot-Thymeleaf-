# 🎮 Game2048 -Java Spring Boot + Thymeleaf

A web-based version of the classic 2048 game, built using Spring Boot and Thymeleaf. Play directly in your browser and enjoy smooth gameplay with a clean UI.

---

## 🚀 Live Demo

👉 [Click here to play Game2048](https://game2048-springboot-thymeleaf.onrender.com)

---

## 🛠️ Tech Stack

- **Backend**: Spring Boot (Java)
- **Frontend**: Thymeleaf templates
- **Build Tool**: Maven
- **Containerization**: Docker
- **Hosting**: Render

---

## 📦 Deployment Notes

- The app is deployed on [Render](https://render.com) using a custom Dockerfile.
- Dockerfile uses a multi-stage build:
  - First stage uses Maven to build the JAR inside the container.
  - Second stage runs the JAR using Eclipse Temurin JDK 17.
- No need to push `target/` or `.jar` files to GitHub — everything builds inside Docker.

---

## 📁 Project Structure

2048game/
│
├── src/
│   ├── main/
│   │   ├── java/com/exponent/energy/
│   │   │   ├── controller/
│   │   │   │   └── GameController.java
│   │   │   ├── model/
│   │   │   │   ├── Board.java
│   │   │   │   └── Tile.java
│   │   │   ├── service/
│   │   │   │   └── GameService.java
│   │   │   └── Game2048Application.java
│   │   └── resources/
│   │       ├── templates/
│   │       │   └── index.html
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   └── style.css
│   │       │   └── js/
│   │       │       └── game.js
│   │       └── application.properties
│   └── test/
│       └── (optional unit tests)
│
├── pom.xml
├── README.md
├── .gitignore
└── screenshots/
    └── game-preview.png

## 📦 Deployment Notes

- The app is deployed on [Render](https://render.com) using a custom Dockerfile.
- Dockerfile uses a multi-stage build:
  - First stage uses Maven to build the JAR inside the container.
  - Second stage runs the JAR using Eclipse Temurin JDK 17.
- No need to push `target/` or `.jar` files to GitHub — everything builds inside Docker.

---

## ✨ Features

- Playable 2048 game with arrow keys or on-screen buttons
- Responsive layout
- Clean separation of frontend and backend logic
- Dockerized for easy deployment

---

## 🧪 How to Run Locally

```bash
# Build the project
mvn clean package

# Run the JAR
java -jar target/2048game-0.0.1-SNAPSHOT.jar
