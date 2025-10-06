package task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.nio.file.Files;
import java.nio.file.Path;

public class TaskTracker {
    private static HashMap<Long, Task> tasks;
    private static final Path path = Path.of(System.getProperty("user.dir"), "tasks.json");
    private static final Gson gson = new Gson();

    public static void main(String[] args) throws IllegalArgumentException {
        tasks = generateTasks();
        if(args.length == 0) {
            printUsage();
            return;
        }
        switch(args[0]) {
            case "add":
                requireArgs(args, 2);
                addTask(args[1]);
                break;
            case "update":
                requireArgs(args, 3);
                updateTask(Long.valueOf(args[1]), args[2]);
                break;
            case "delete":
                requireArgs(args, 2);
                deleteTask(Long.valueOf(args[1]));
                break;
            case "list":
                if (args.length == 1) {
                    System.out.println("listing all");
                    listAllTasks();
                } else {
                    listTasksWithStatus(Status.valueOf(args[1]));
                }
                break;
            case "mark-todo":
                requireArgs(args, 2);
                markTaskAs(Long.valueOf(args[1]), Status.TODO);
                break;
            case "mark-in-progress":
                requireArgs(args, 2);
                markTaskAs(Long.valueOf(args[1]), Status.IN_PROGRESS);
                break;
            case "mark-done":
                requireArgs(args, 2);
                markTaskAs(Long.valueOf(args[1]), Status.DONE);
                break;
            default:
                printUsage();
                break;
        }
    }

    private static HashMap<Long, Task> generateTasks() {
        Type type = new TypeToken<HashMap<Long, Task>>() {}.getType();
        try {
            if (!Files.exists(path) || Files.size(path) == 0) {
                Files.writeString(path, "{}");
                return new HashMap<>();
            }
            try (FileReader reader = new FileReader(path.toFile())) {
                HashMap<Long, Task> map = gson.fromJson(reader, type);
                if (map == null) map = new HashMap<>();
                // Actualizar el contador al valor máximo existente
                long maxId = map.keySet().stream().mapToLong(Long::longValue).max().orElse(0);
                Task.setCounter(maxId);
                return map;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
    private static void addTask(String description) {
        Task task = new Task(description);
        tasks.put(task.getId(), task);
        System.out.println("Task added successfully (ID: " + task.getId() + ")");
        saveJSON();
    }

    private static void updateTask(Long id, String description) {
        tasks.get(id).setDescription(description);
        System.out.println("Task updated successfully (ID: " + id + ")");
        saveJSON();
    }

    private static void deleteTask(Long key) {
        tasks.remove(key);
        System.out.println("Task deleted successfully (ID: " + key + ")");
        saveJSON();
    }
    private static void saveJSON() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(path.toFile())) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void markTaskAs(Long key, Status status) {
        tasks.get(key).setStatus(status);
        saveJSON();
    }
    private static void listTasksWithStatus(Status status) {
        tasks.forEach((key, value) -> {
            if (value.getStatus() == status) {
                System.out.println("ID: " + key + ": " + value);
            }
        });
    }

    private static void listAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found");
            return;
        }
        tasks.forEach((id, task) -> {
            System.out.printf("ID: %-3d | %-30s | %-12s | Created: %s%n",
                    id, task.getDescription(), task.getStatus(), task.getCreatedAt());
        });
    }

    private static void printUsage() {
        System.out.println("""
        Usage: task-cli <command> [arguments]

        Commands:
          add "<description>"
              Adds a new task with the given description.
              Example: task-cli add "Buy groceries"

          update <id> "<new description>"
              Updates the description of an existing task.
              Example: task-cli update 1 "Buy groceries and cook dinner"

          delete <id>
              Deletes the task with the given ID.
              Example: task-cli delete 1

          mark-todo <id>
              Marks the task as TODO.
              Example: task-cli mark-todo 1

          mark-in-progress <id>
              Marks the task as IN PROGRESS.
              Example: task-cli mark-in-progress 1

          mark-done <id>
              Marks the task as DONE.
              Example: task-cli mark-done 1

          list [status]
              Lists all tasks, or only those with a specific status.
              Available statuses: todo, in-progress, done
              Examples:
                  task-cli list
                  task-cli list done
                  task-cli list todo
                  task-cli list in-progress

        Notes:
          - Task IDs are numeric.
          - Descriptions with spaces must be quoted.
    """);
    }

    private static void requireArgs(String[] args, int expected) {
        if (args.length != expected)
            throw new IllegalArgumentException("Expected " + (expected - 1) + " argument(s), got " + (args.length - 1));
    }

}
