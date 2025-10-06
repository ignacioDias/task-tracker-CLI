# If the jar is not found, it means the maven-assembly-plugin is missing
JAR_NAME="task-tracker-CLI-1.0-SNAPSHOT-jar-with-dependencies.jar"
TARGET_DIR="target"
MAIN_CLASS="task.TaskTracker"
if [ ! -f "$TARGET_DIR/$JAR_NAME" ]; then
    echo "❌ Fat JAR not found at $TARGET_DIR/$JAR_NAME"
    exit 1
fi

echo "🚀 Running JAR with args: $@"
java -jar "$TARGET_DIR/$JAR_NAME" "$@"
