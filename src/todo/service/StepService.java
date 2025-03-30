package todo.service;

import db.Database;
import db.exception.InvalidEntityException;
import todo.entity.Step;

public class StepService {
    private StepService() {}

    public static void addStep() {
        System.out.print("TaskID: ");
        int taskID = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Title: ");
        String title = scanner.nextLine().trim();

        Step step = new Step(taskID, title);

        try {
            Database.add(step);
        }
        catch (InvalidEntityException e) {
            TaskService.setAsInProgress(taskID);
            System.out.println("Step saved successfully.");
            System.out.println("ID: " + step.id);
            System.out.println("Creation Date: " + step.creationDate);
        } catch (InvalidEntityException e) {
            System.out.println(e.getMessage());
        }

    }

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
