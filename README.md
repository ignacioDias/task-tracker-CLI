# 🧩 Task Tracker CLI

A simple command-line task manager written in Java.  
Tasks are stored in a local `tasks.json` file (automatically created in the working directory).
Project https://roadmap.sh/projects/task-tracker
---

## ⚙️ Requirements

- **Java 21+**
- **Maven 3.8+**
- **Linux / macOS** (tested on bash)
- **Gson** (already included through Maven)

---

## 🏗️ Build

Compile and package the project into a fat JAR (includes dependencies):

```bash
sh build.sh
```
This script:

Cleans previous builds.

Packages the project using Maven.

Generates the JAR file:

target/task-tracker-CLI-1.0-SNAPSHOT-jar-with-dependencies.jar

🚀 Run

Run the CLI tool with one of the available commands:

sh run.sh <command> [args]


Example:

sh run.sh add "Buy groceries"
sh run.sh list
sh run.sh mark-done 1


The run.sh script executes:

java -jar target/task-tracker-CLI-1.0-SNAPSHOT-jar-with-dependencies.jar "$@"
