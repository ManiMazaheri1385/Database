package command;

import db.Database;
import todo.entity.*;
import todo.service.*;
import todo.validator.*;
import java.util.Scanner;
import java.util.ArrayList;

public class CommandLine {
    private static Scanner scanner = new Scanner(System.in);

    public void start() {
        TaskValidator taskValidator = new TaskValidator();
        StepValidator stepValidator = new StepValidator();
        Database.registerValidator(Task.TASK_ENTITY_CODE, taskValidator);
        Database.registerValidator(Step.STEP_ENTITY_CODE, stepValidator);

        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim();

            if (command.equals("exit")) {
                System.out.println("Exiting...");
                break;
            }

            processCommand(command);

            System.out.println();
        }
    }

    public void processCommand(String command) {
        try {
            switch (command) {
                case "add task" -> addTask();
                case "add step" -> addStep();
                case "update task" -> updateTask();
                case "update step" -> updateStep();
                case "delete" -> delete();
                case "get task-by-id" -> getTaskById();
                case "get incomplete-tasks" -> getIncompleteTasks();
                case "get all-tasks" -> getAllTasks();
                default -> System.out.println("Unknown command: " + command);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addTask() {
        TaskService.addTask();
    }

    public void addStep() {
        StepService.addStep();
    }

    public void updateTask() {
        System.out.print("ID: ");
        int taskID = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Field: ");
        String field = scanner.nextLine().trim();
        try {
            switch (field) {
                case "title" -> TaskService.updateTitle(taskID);
                case "description" -> TaskService.updateDescription(taskID);
                case "dueDate" -> TaskService.updateDueDate(taskID);
                case "status" -> TaskService.updateStatus(taskID);
                default -> System.out.println("Unknown field: " + field);
            }
        } catch (ClassCastException e) {
            System.out.println("Error: Cannot find task with ID=" + taskID + ".");
        }

    }

    public void updateStep() {
        System.out.print("ID: ");
        int stepID = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Field: ");
        String field = scanner.nextLine().trim();

        try {
            switch (field) {
                case "taskRef" -> StepService.updateTaskRef(stepID);
                case "title" -> StepService.updateTitle(stepID);
                case "status" -> StepService.updateStatus(stepID);
                default -> System.out.println("Unknown field: " + field);
            }
        } catch (ClassCastException e) {
            System.out.println("Error: Cannot find step with ID=" + stepID + ".");
        }

    }

    public void delete() {
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        Database.delete(id);
        System.out.println("Entity with ID=" + id + " successfully deleted.");
    }

    public void getTaskById() {
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        try {
            Task task = (Task) Database.get(id);
            TaskService.printTask(task);
        } catch (ClassCastException e) {
            System.out.println("Error: Cannot find task with ID=" + id + ".");
        }
    }

    public void getIncompleteTasks() {
        ArrayList<Task> incompleteTasks = TaskService.getIncompleteTasks();
        if (incompleteTasks.isEmpty()) {
            System.out.println("No incomplete tasks found.");
        }
        for (Task task : incompleteTasks) {
            TaskService.printTask(task);
            if (incompleteTasks.size() - incompleteTasks.indexOf(task) != 1) {
                System.out.println();
            }
        }
    }

    public void getAllTasks() {
        ArrayList<Task> tasks = TaskService.getSortedTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        }
        for (Task task : tasks) {
            TaskService.printTask(task);
            if (tasks.size() - tasks.indexOf(task) != 1) {
                System.out.println();
            }
        }
    }

}