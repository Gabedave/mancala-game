# Mancala Game

Mancala Game is a Java-based application that allows users to play the popular Mancala board game. The game follows the traditional rules of Mancala and provides an interactive interface for players to make moves and compete against each other.

## Features

- Interactive game interface
- Multiple players support
- Rule-based move validation
- Capture and scoring functionality
- End game detection and winner determination

The app is hosted live at the following links
- Live Frontend: https://gabedave.github.io/mancala-game/
- Live Backend: https://mancala-game-app.onrender.com/

## Technologies Used

### Backend

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- RESTful API
- JUnit (for testing)
- Maven (for dependency management)

### Frontend

- JavaScript
- React

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or above
- Maven
- NPM

### Installation

1. Clone the repository:
   `git clone https://github.com/your-username/mancala-game.git`
2. Navigate to the project directory:
   `cd mancala-game`
3. Build the project using Maven:
   `mvn clean install`
4. Run the application:
   `mvn spring-boot:run`
5. Access the application api in your web browser at [http://localhost:8080](http://localhost:8080) or use the live backend at [https://mancala-game-app.onrender.com/](https://mancala-game-app.onrender.com/). Reference the swagger-ui documentation [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
6. To start the frontend, navigate to the client folder in the project directory:
   `cd client`
7. Build the project using NPM:
   `npm run build`
8. Run the web app using a static server:
   `npm install -g serve`
   `serve -s build `

## Usage

1. Create a new game by clicking on the "Start New Game" button or close from an existing game from the active games list.
2. Start the game and take turns making moves by selecting a pit on each side of the board.
3. Follow the traditional rules of Mancala to distribute the stones and capture opponent's stones.
4. Continue playing until the game ends, and a winner is determined.
5. Play again or start a new game as desired.

## Contributing

Contributions are welcome! If you find any bugs or have suggestions for improvements, please open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgements

- [Mancala](https://en.wikipedia.org/wiki/Mancala) - Wikipedia
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Hibernate Documentation](https://hibernate.org/orm/documentation/)
- [JUnit Documentation](https://junit.org/junit5/docs/current/user-guide/)

## Contact

For any questions or inquiries, please contact [gabedave@gmail.com](mailto:gabedave@gmail.com).

---
