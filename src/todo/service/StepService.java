package todo.service;

import db.Database;
import todo.entity.*;
import java.util.Scanner;
import java.util.ArrayList;
import db.exception.InvalidEntityException;

public class StepService {
    public static Scanner scanner = new Scanner(System.in);

    private StepService() {}

    public static void addStep() {
        System.out.print("TaskID: ");
        int taskID = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Title: ");
        String title = scanner.nextLine().trim();

        Step step = new Step(taskID, title);

        try {
            Database.add(step);
            automaticUpdateStatus(step.taskRef);
            System.out.println("Step saved successfully.");
            System.out.println("ID: " + step.id);
            System.out.println("Creation Date: " + step.creationDate);
        } catch (InvalidEntityException e) {
            System.out.println(e.getMessage());
        }

    }

    // update fields:
    // -------------------------------------------------------------------------------------------
    public static void updateTaskRef(int stepID) {
        Step step = (Step) Database.get(stepID);

        String oldValue = String.valueOf(step.taskRef);
        System.out.print("New Value: ");
        String newValue = scanner.nextLine().trim();
        step.taskRef = Integer.parseInt(newValue);

        try {
            Database.update(step);
            automaticUpdateStatus(step.taskRef);
            automaticUpdateStatus(Integer.parseInt(oldValue));
            updateSuccessfully(step, "taskRef", oldValue, newValue);
        } catch (InvalidEntityException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void updateTitle(int stepID) {
        Step step = (Step) Database.get(stepID);

        String oldValue = step.title;
        System.out.print("New Value: ");
        String newValue = scanner.nextLine().trim();
        step.title = newValue;

        try {
            Database.update(step);
            updateSuccessfully(step, "title", oldValue, newValue);
        } catch (InvalidEntityException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void updateStatus(int stepID) {
        Step step = (Step) Database.get(stepID);

        String oldValue = String.valueOf(step.status);
        System.out.print("New Value: ");
        String newValue = scanner.nextLine().trim();
        step.status = Step.Status.valueOf(newValue);

        try {
            Database.update(step);
            automaticUpdateStatus(step.taskRef);
            updateSuccessfully(step, "status", oldValue, newValue);
        } catch (InvalidEntityException e) {
            System.out.println(e.getMessage());
        }

    }
    // -------------------------------------------------------------------------------------------

    public static void automaticUpdateStatus(int taskID) {
        int counter = 0;
        Task task = (Task) Database.get(taskID);

        ArrayList<Step> taskSteps = TaskService.getTaskSteps(task.id);
        if (taskSteps.isEmpty()) {
            return;
        }

        for (Step taskStep : taskSteps) {
            if (taskStep.status == Step.Status.Completed) {
                counter++;
            }
        }

        Task.Status status = Task.Status.InProgress;
        if (counter == 0) {
            status = Task.Status.NotStarted;
        }
        if (counter == taskSteps.size()) {
            status = Task.Status.Completed;
        }

        task.status = status;
        try {
            Database.update(task);
        } catch (InvalidEntityException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void updateSuccessfully(Step step, String field, String oldValue, String newValue) {
        System.out.println("Successfully updated the step.");
        System.out.println("Field: " + field);
        System.out.println("Old Value: " + oldValue);
        System.out.println("New Value: " + newValue);
        System.out.println("Modification Date: " + step.lastModificationDate);
    }

    public static void printSteps(int taskID) {
        ArrayList<Step> taskSteps = TaskService.getTaskSteps(taskID);
        if (taskSteps.isEmpty()) {
            return;
        }

        System.out.println("Steps: ");
        for (Step step : taskSteps) {
            System.out.println("    + " + step.title);
            System.out.println("        ID: " + step.id);
            System.out.println("        Status: " + step.status);
        }
    }

}
