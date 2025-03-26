package example;

import db.*;
import db.exception.InvalidEntityException;

public class HumanValidator implements Validator {
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Human)) {
            throw new IllegalArgumentException("Invalid entity type");
        }
        if (((Human) entity).name == null || ((Human) entity).name.isEmpty()) {
            throw new InvalidEntityException("Human name cannot be empty");
        }
        if (((Human) entity).age <= 0) {
            throw new InvalidEntityException("Age must be a positive integer");
        }
    }
}
