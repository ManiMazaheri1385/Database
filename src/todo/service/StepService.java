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
            TaskService.setAsInProgress(taskID);
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
            TaskService.setAsInProgress(step.taskRef);
            setAsCompleted(step);
            updateSuccessfully(step, "status", oldValue, newValue);
        } catch (InvalidEntityException e) {
            System.out.println(e.getMessage());
        }

    }
    // -------------------------------------------------------------------------------------------

    // status automatic updates
    // *******************************************************************************************
    public static void setAsCompleted(Step step) {
        Task task = (Task) Database.get(step.taskRef);
        if (task.status == Task.Status.Completed) {
            return;
        }

        ArrayList<Step> taskSteps = TaskService.getTaskSteps(step.taskRef);
        for (Step taskStep : taskSteps) {
            if (taskStep.status == Step.Status.Completed) {
                continue;
            }
            return;
        }

        task.status = Task.Status.Completed;
        try {
            Database.update(task);
        } catch (InvalidEntityException e) {
            System.out.println(e.getMessage());
        }

    }
    // *******************************************************************************************

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
