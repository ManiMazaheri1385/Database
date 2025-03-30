package todo.service;

import db.Database;
import db.Entity;
import db.exception.InvalidEntityException;
import todo.entity.Task;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class TaskService {
    private TaskService() {}

    public static void addTask() {
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Due date (yyyy-MM-dd): ");
        Date dueDate = date(scanner.nextLine().trim());

        Task task = new Task(title, description, dueDate);
        try {
            Database.add(task);
            System.out.println("Task saved successfully.");
            System.out.println("ID: " + task.id);
        } catch (InvalidEntityException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void setAsInProgress(int taskID) {
        Task task = (Task) Database.get(taskID);
        task.status = Task.Status.InProgress;
        try {
            Database.update(task);
        }
        catch (InvalidEntityException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setAsCompleted(int taskID) {
        Task task = (Task) Database.get(taskID);
        task.status = Task.Status.Completed;
        try {
            Database.update(task);
        }
        catch (InvalidEntityException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Entity> getTaskSteps(int taskID) {
        ArrayList<Entity> tasks = Database.getAll(Task.TASK_ENTITY_CODE);
        ArrayList<Entity> taskSteps = new ArrayList<>();
        for (Entity entity : tasks) {
            Task task = (Task) entity;
            if (taskID == task.id) {
                taskSteps.add(task);
            }
        }
        return taskSteps;
    }

    public static ArrayList<Task> getSortedTasks() {
        ArrayList<Entity> tasks = Database.getAll(Task.TASK_ENTITY_CODE);
        ArrayList<Task> sortedTasks = new ArrayList<>();
        for (Entity entity : tasks) {
            Task task = (Task) entity;
            sortedTasks.add(task);
        }
        sortedTasks.sort(Comparator.comparing(task -> task.dueDate));
        return sortedTasks;
    }

    public static ArrayList<Task> getIncompleteTasks(int taskID) {
        ArrayList<Task> tasks = getSortedTasks();
        ArrayList<Task> incompleteTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.status == Task.Status.InProgress || task.status == Task.Status.NotStarted) {
                incompleteTasks.add(task);
            }
        }
        return incompleteTasks;
    }

}
