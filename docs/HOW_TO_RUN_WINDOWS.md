# How to Run This Project on Windows

This guide is for running **Tangail District Quiz** on a Windows PC.

You need:

1. Java JDK 17 or 21
2. Apache Maven
3. MySQL 8 (or MariaDB)
4. Apache Tomcat 10 or 10.1 (not Tomcat 9)

---

## Step 1 — Install Java JDK

1. Download **Eclipse Temurin JDK 17** or **JDK 21** from:
   - https://adoptium.net/
2. Run the installer.
3. Tick **Set JAVA_HOME** if the installer shows that option.
4. Open **Command Prompt** and type:

```bat
java -version
```

You should see a version like `17` or `21`.

If Windows cannot find Java:

1. Search **Environment Variables** in the Start menu.
2. Click **Environment Variables**.
3. Under **System variables**, add or edit:

| Name | Example value |
|---|---|
| `JAVA_HOME` | `C:\Program Files\Eclipse Adoptium\jdk-17.0.x-hotspot` |
| `Path` | add `%JAVA_HOME%\bin` |

Open a **new** Command Prompt after changing this.

---

## Step 2 — Install Maven

1. Download Maven from:
   - https://maven.apache.org/download.cgi
2. Download the **zip** file (Binary zip archive).
3. Extract it, for example to:

```
C:\apache-maven-3.9.9
```

4. Add these environment variables:

| Name | Example value |
|---|---|
| `MAVEN_HOME` | `C:\apache-maven-3.9.9` |
| `Path` | add `%MAVEN_HOME%\bin` |

5. Open a new Command Prompt and type:

```bat
mvn -v
```

It should print the Maven version and the Java version.

---

## Step 3 — Install MySQL

### Option A: MySQL Installer (recommended)

1. Download **MySQL Installer for Windows**:
   - https://dev.mysql.com/downloads/installer/
2. Install **MySQL Server 8**.
3. Remember the **root password** you set.
4. Make sure MySQL is running:
   - Open **Services**
   - Find **MySQL80**
   - Status should be **Running**

### Option B: XAMPP

1. Install XAMPP.
2. Start **MySQL** from the XAMPP Control Panel.
3. Default user is usually `root` with an empty password, unless you changed it.

---

## Step 4 — Create the database

Open **Command Prompt**.

If `mysql` is not found, use the full path. Example:

```bat
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p
```

Then inside MySQL you can run:

```sql
SOURCE C:/Users/YOUR_NAME/Desktop/projects/swaccha_project/database/tangail_quiz_db.sql;
```

Or from Command Prompt (easier):

```bat
cd C:\Users\YOUR_NAME\Desktop\projects\swaccha_project

"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p < database\tangail_quiz_db.sql
```

Type your MySQL root password when asked.

This creates:

- database `tangail_quiz_db`
- 4 tables
- 20 quiz questions

### Check it worked

```bat
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p -e "USE tangail_quiz_db; SELECT COUNT(*) FROM questions;"
```

The count should be **20**.

You can also use **MySQL Workbench**:

1. Open Workbench
2. Connect to Local instance
3. File → Run SQL Script
4. Choose `database\tangail_quiz_db.sql`
5. Click the lightning icon to run it

---

## Step 5 — Set the database password in the project

Open this file:

```
src\main\resources\db.properties
```

Change it to match your Windows MySQL:

```
db.url=jdbc:mysql://localhost:3306/tangail_quiz_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Dhaka
db.user=root
db.password=YOUR_MYSQL_PASSWORD
admin.username=admin
admin.password=admin123
```

If your MySQL password is empty (common with XAMPP), use:

```
db.password=
```

Save the file.

---

## Step 6 — Install Apache Tomcat 10

**Do not use Tomcat 9.** This project uses `jakarta.servlet`. Tomcat 9 will not run it.

1. Download **Tomcat 10.1** Windows zip from:
   - https://tomcat.apache.org/download-10.cgi
2. Extract it, for example to:

```
C:\apache-tomcat-10.1.xx
```

3. Optional: add environment variable

| Name | Example value |
|---|---|
| `CATALINA_HOME` | `C:\apache-tomcat-10.1.xx` |
| `Path` | add `%CATALINA_HOME%\bin` |

---

## Step 7 — Build the project

Open **Command Prompt**:

```bat
cd C:\Users\YOUR_NAME\Desktop\projects\swaccha_project
mvn clean package
```

Wait until you see `BUILD SUCCESS`.

The WAR file is created here:

```
target\tangail-quiz.war
```

---

## Step 8 — Deploy to Tomcat

Copy the WAR file into Tomcat `webapps`.

Example:

```bat
copy target\tangail-quiz.war C:\apache-tomcat-10.1.xx\webapps\
```

Start Tomcat:

```bat
C:\apache-tomcat-10.1.xx\bin\startup.bat
```

A Tomcat window will open. Wait a few seconds.

---

## Step 9 — Open the website

In your browser open:

```
http://localhost:8080/tangail-quiz/
```

Admin page:

```
http://localhost:8080/tangail-quiz/admin/login
```

Admin login:

- username: `admin`
- password: `admin123`

---

## Stop Tomcat

```bat
C:\apache-tomcat-10.1.xx\bin\shutdown.bat
```

---

## Run from IntelliJ IDEA (optional)

1. Open the project folder in IntelliJ.
2. Wait for Maven to import `pom.xml`.
3. Add Tomcat 10 as an Application Server:
   - File → Settings → Build, Execution, Deployment → Application Servers
   - Add Tomcat 10 folder
4. Run → Edit Configurations → Tomcat Server → Local
5. Deploy artifact: `tangail-quiz:war exploded`
6. Application context: `/tangail-quiz`
7. Click Run

Eclipse / Apache NetBeans can also deploy a WAR to Tomcat 10 in the same way.

---

## Common Windows problems

### `java` is not recognized

JAVA_HOME and Path are not set. Close Command Prompt and open a new one after fixing them.

### `mvn` is not recognized

Maven `bin` folder is not in Path.

### `mysql` is not recognized

Use the full path to `mysql.exe`, or add MySQL `bin` to Path:

```
C:\Program Files\MySQL\MySQL Server 8.0\bin
```

### Tomcat page shows 404

- Check the URL includes `/tangail-quiz/`
- Check `tangail-quiz.war` is inside `webapps`
- Wait until Tomcat finishes unpacking the WAR

### Error about `jakarta.servlet`

You started **Tomcat 9**. Install Tomcat 10 or 10.1.

### Database connection error

- MySQL service is not running
- Wrong password in `db.properties`
- Database `tangail_quiz_db` was not created
- Rebuild the WAR after changing `db.properties`:

```bat
mvn clean package
```

Then copy the new WAR to `webapps` again. Delete the old `webapps\tangail-quiz` folder first if Tomcat already unpacked it.

### Port 8080 already in use

Another program (IIS, old Tomcat, Skype) is using 8080.

Edit:

```
C:\apache-tomcat-10.1.xx\conf\server.xml
```

Change:

```xml
<Connector port="8080" ...
```

to:

```xml
<Connector port="8081" ...
```

Then open:

```
http://localhost:8081/tangail-quiz/
```

---

## Quick checklist

1. Java works (`java -version`)
2. Maven works (`mvn -v`)
3. MySQL is running
4. SQL file imported (20 questions)
5. `db.properties` has your real password
6. `mvn clean package` succeeded
7. WAR copied to Tomcat 10 `webapps`
8. Tomcat started
9. Browser: `http://localhost:8080/tangail-quiz/`
