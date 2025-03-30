package todo.service;

import db.Database;
import db.Entity;
import db.exception.InvalidEntityException;
import todo.entity.Task;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class TaskService {
    public static Scanner scanner = new Scanner(System.in);

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

    // update fields:
    // -------------------------------------------------------------------------------------------
    public static void updateTitle(int taskID) {
        Task task = (Task) Database.get(taskID);

        String oldValue = task.title;
        System.out.print("New Value: ");
        String newValue = scanner.nextLine().trim();
        task.title = newValue;

        try {
            Database.update(task);
            updateSuccessfully(task, "title", oldValue, newValue);
        } catch (InvalidEntityException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void updateDescription(int taskID) {
        Task task = (Task) Database.get(taskID);

        String oldValue = task.description;
        System.out.print("New Value: ");
        String newValue = scanner.nextLine().trim();
        task.description = newValue;

        try {
            Database.update(task);
            updateSuccessfully(task, "description", oldValue, newValue);
        } catch (InvalidEntityException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void updateStatus(int taskID) {
        Task task = (Task) Database.get(taskID);

        String oldValue = String.valueOf(task.status);
        System.out.print("New Value: ");
        String newValue = scanner.nextLine().trim();
        task.status = Task.Status.valueOf(newValue);

        try {
            Database.update(task);
            setAsCompleted(task);
            updateSuccessfully(task, "status", oldValue, newValue);
        } catch (InvalidEntityException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void updateDueDate(int taskID) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Task task = (Task) Database.get(taskID);

        String oldValue = dateFormat.format(task.dueDate);
        System.out.print("New Value: ");
        String newValue = scanner.nextLine().trim();
        task.dueDate = date(newValue);

        try {
            Database.update(task);
            updateSuccessfully(task, "dueDate", oldValue, newValue);
        } catch (InvalidEntityException e) {
            System.out.println(e.getMessage());
        }

    }
    // -------------------------------------------------------------------------------------------

    // status automatic updates
    // *******************************************************************************************
    public static void setAsInProgress(int taskID) {
        Task task = (Task) Database.get(taskID);

        if (task.status == Task.Status.InProgress) {
            return;
        }
        if (task.status == Task.Status.NotStarted) {
            ArrayList<Step> taskSteps = TaskService.getTaskSteps(taskID);
            for (Step taskStep : taskSteps) {
                if (taskStep.status == Step.Status.Completed) {
                    break;
                }
                if (taskSteps.size() - taskSteps.indexOf(taskStep) == 1) {
                    return;
                }
            }
        }

        task.status = Task.Status.InProgress;
        try {
            Database.update(task);
        } catch (InvalidEntityException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void setAsCompleted(Task task) {
        if (task.status != Task.Status.Completed) {
            return;
        }

        ArrayList<Step> taskSteps = getTaskSteps(task.id);
        if (taskSteps.isEmpty()) {
            return;
        }

        for (Step taskStep : taskSteps) {
            taskStep.status = Step.Status.Completed;
            try {
                Database.update(taskStep);
            } catch (InvalidEntityException e) {
                System.out.println(e.getMessage());
            }
        }

    }
    // *******************************************************************************************

    public static ArrayList<Step> getTaskSteps(int taskID) {
        ArrayList<Entity> steps = Database.getAll(Step.STEP_ENTITY_CODE);
        ArrayList<Step> taskSteps = new ArrayList<>();
        for (Entity entity : steps) {
            Step step = (Step) entity;
            if (taskID == step.taskRef) {
                taskSteps.add(step);
            }
        }
        return taskSteps;
    }

    public static ArrayList<Task> getIncompleteTasks() {
        ArrayList<Task> tasks = getSortedTasks();
        ArrayList<Task> incompleteTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.status == Task.Status.InProgress || task.status == Task.Status.NotStarted) {
                incompleteTasks.add(task);
            }
        }
        return incompleteTasks;
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
