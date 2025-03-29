package todo.service;

import db.Database;
import db.exception.InvalidEntityException;
import todo.entity.Step;

public class StepService {
    private StepService() {}

    public static void saveStep(int taskRef, String title) {
        Step step = new Step(taskRef, title);
        try {
            Database.add(step);
        }
        catch (InvalidEntityException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Step saved successfully.\n" + "ID: " + step.id + "\nCreation Date: " + step.creationDate);
    }

    public static void setAsCompleted(int stepID) {
        Step step = (Step) Database.get(stepID);
        step.status = Step.Status.Completed;
        try {
            Database.update(step);
        }
        catch (InvalidEntityException e) {
            System.out.println(e.getMessage());
        }
    }

}
