package todo.validator;

import db.*;
import db.exception.*;
import todo.entity.Step;

public class StepValidator implements Validator {

    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Step step)) {
            throw new IllegalArgumentException("Error: Invalid entity type");
        }
        if (step.title == null || step.title.isEmpty()) {
            throw new InvalidEntityException("Cannot save step.\nError: Step title cannot be empty.");
        }
        try {
            Database.get(step.taskRef);
        }
        catch (EntityNotFoundException e) {
            throw new InvalidEntityException("Cannot save step.\nError: Cannot find task with ID=" + step.taskRef + ".");
        }
    }

}
