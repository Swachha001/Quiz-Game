# Tangail District Knowledge Quiz

A Servlet, JSP, and JDBC CRUD application.

Players answer **10 random unique questions** from a bank of 20 questions about Tangail District, Bangladesh.

The homepage title is:

**Test Your Knowledge About Tangail**

Topics:

- Crops & Agriculture
- Academic Institutions
- Geography

---

## What this project does

1. Player enters a name and starts a quiz.
2. The server picks 10 different active questions from MySQL (`ORDER BY RAND() LIMIT 10`).
3. Those 10 questions are stored in the HTTP session, so a page refresh does not pick a new set.
4. The player submits answers.
5. The **server** marks the quiz (10 points per correct answer).
6. The attempt and each answer are saved in MySQL inside one transaction.
7. The result page shows the score and a question-by-question review.
8. An admin area can create, read, update, delete, and activate/deactivate questions.

---

## How to read this code (start here)

This project uses a simple MVC split. If you are new to Java web apps, read the files in this order:

1. `database/tangail_quiz_db.sql` — tables and the 20 questions
2. `src/main/java/com/tangailquiz/model/` — plain Java objects (one object = one table row)
3. `src/main/java/com/tangailquiz/dao/DBConnection.java` — opens a MySQL connection
4. `src/main/java/com/tangailquiz/dao/QuestionDAO.java` — SQL for questions
5. `src/main/java/com/tangailquiz/controller/QuizStartServlet.java` — starts a quiz
6. `src/main/java/com/tangailquiz/controller/QuizSubmitServlet.java` — marks and saves
7. `src/main/webapp/WEB-INF/jsp/quiz.jsp` — the quiz page

Rule of thumb:

- **Servlet** = traffic cop (reads the request, calls a DAO, sends the user to a JSP)
- **DAO** = talks to MySQL
- **Model** = holds data
- **JSP** = HTML page

There is no Spring, no Hibernate, and no React.

---

## Features

- Random 10-question quiz from 20 seeded questions
- Player, attempt, and answer history in MySQL
- Question CRUD with search and category/difficulty filters
- Activate/deactivate questions (inactive questions are skipped)
- Player list, edit, delete (delete also removes that player's attempts)
- Admin dashboard with counts, recent attempts, and highest scores
- About Tangail page with official source links

---

## Technologies

- Java 17+
- Maven
- Jakarta Servlet + JSP + JSTL
- JDBC
- MySQL 8+
- Apache Tomcat 10+
- HTML5 / CSS3 / a little JavaScript

---

## Requirements

- JDK 17 or 21
- Maven 3.8+ (`brew install maven` on a Mac if `mvn` is missing)
- MySQL 8+
- Apache Tomcat 10.1+ (Jakarta, not Tomcat 9)

---

## Database setup

1. Open MySQL.
2. Import the SQL file:

```bash
mysql -u root -p < database/tangail_quiz_db.sql
```

Or in MySQL Workbench: File → Run SQL Script → choose `database/tangail_quiz_db.sql`.

That creates:

- database `tangail_quiz_db`
- tables `players`, `questions`, `quiz_attempts`, `quiz_answers`
- 20 starter questions

### Schema

```
players 1 ---- N quiz_attempts 1 ---- N quiz_answers
questions 1 ---------------------- N quiz_answers
```

Deleting a player also deletes that player's attempts and answers (`ON DELETE CASCADE`).

---

## MySQL configuration

Edit:

`src/main/resources/db.properties`

```
db.url=jdbc:mysql://localhost:3306/tangail_quiz_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Dhaka
db.user=root
db.password=CHANGE_ME
admin.username=admin
admin.password=admin123
```

Put your real MySQL password in place of `CHANGE_ME`.

---

## How to run on Windows

See the full Windows guide:

**[docs/HOW_TO_RUN_WINDOWS.md](docs/HOW_TO_RUN_WINDOWS.md)**

That file covers JDK, Maven, MySQL, Tomcat 10, and common Windows errors.

---

## How to run

### 1. Build the WAR

```bash
mvn clean package
```

This creates `target/tangail-quiz.war`.

### 2. Deploy on Tomcat

1. Copy `target/tangail-quiz.war` into Tomcat's `webapps` folder.
2. Start Tomcat.
3. Open:

```
http://localhost:8080/tangail-quiz/
```

If you renamed the WAR to `ROOT.war`, the app will be at `http://localhost:8080/`.

---

## Default URLs

| Page | URL |
|---|---|
| Home | `/tangail-quiz/` or `/tangail-quiz/home` |
| About Tangail | `/tangail-quiz/about` |
| Start quiz | `/tangail-quiz/quiz/start` |
| Quiz | `/tangail-quiz/quiz` |
| Result | `/tangail-quiz/quiz/result` |
| Admin login | `/tangail-quiz/admin/login` |
| Dashboard | `/tangail-quiz/admin/dashboard` |
| Questions | `/tangail-quiz/admin/questions` |
| Players | `/tangail-quiz/admin/players` |
| Attempts | `/tangail-quiz/admin/attempts` |

Default admin login (from `db.properties`):

- username: `admin`
- password: `admin123`

---

## Project structure

```
QuizGame_project/
├── pom.xml
├── README.md
├── database/tangail_quiz_db.sql
└── src/main/
    ├── java/com/tangailquiz/
    │   ├── model/          Player, Question, QuizAttempt, QuizAnswer
    │   ├── dao/            DBConnection + DAO classes
    │   ├── controller/     Servlets and filters
    │   └── util/           Small helpers
    ├── resources/db.properties
    └── webapp/
        ├── index.jsp
        ├── css/style.css
        ├── js/app.js
        └── WEB-INF/
            ├── web.xml
            ├── jsp/        HTML pages
            └── jspf/       Header and footer pieces
```

---

## Quiz scoring

- 10 questions
- 10 points for each correct answer
- 10 correct = 100, 7 correct = 70, 0 correct = 0

Messages:

- 90–100: Excellent! You know Tangail very well!
- 70–89: Very Good!
- 50–69: Good effort!
- Below 50: Keep learning about Tangail!

---

## Demo checklist for viva

1. Add a question.
2. See it in the question list.
3. Edit it.
4. Deactivate it.
5. Start a quiz — the inactive question should not appear.
6. Play and submit.
7. See the score on the result page.
8. Open Admin → Attempts and view the saved answers.
9. Edit or delete a player.

---

## Screenshots

Add screenshots here after you run the app:

- Home
- Quiz
- Result
- Admin questions

---

## Future improvements

- History & Heritage questions
- Tourist Attractions questions
- Culture & Tradition questions
- Liberation War questions
- Famous Personalities questions
- Stronger admin authentication
- Optional shuffled answer order

Official Tangail sources have material for those extra categories, so new questions can be added through the admin CRUD without changing the architecture.

---

## Sources used for quiz facts

- https://www.tangail.gov.bd/
- https://zp.tangail.gov.bd/
- https://bwdb.tangail.gov.bd/
- https://mbstu.ac.bd/
