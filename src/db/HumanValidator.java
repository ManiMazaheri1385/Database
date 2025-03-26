package db;

import example.*;
import db.exception.*;

public class HumanValidator implements Validator {
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Human)) {
            throw new IllegalArgumentException();
        }
        if (((Human) entity).name == null || ((Human) entity).name.isEmpty()) {
            throw new InvalidEntityException("Human name cannot be empty");
        }
        if (((Human) entity).age < 0) {
            throw new InvalidEntityException("Age cannot be negative");
        }
    }
}
