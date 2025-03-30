package todo.validator;

import db.Entity;
import db.Validator;
import todo.entity.Task;
import db.exception.InvalidEntityException;

public class TaskValidator implements Validator {

    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Task task)) {
            throw new IllegalArgumentException("Error: Invalid entity type");
        }
        if (task.title == null || task.title.isEmpty()) {
            throw new InvalidEntityException("Cannot save task.\nError: Task title cannot be empty.");
        }
    }

}
