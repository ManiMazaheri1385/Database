package db;

import db.exception.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private static int identifier = 1;

    private static ArrayList<Entity> entities = new ArrayList<>();
    private static HashMap<Integer, Validator> validators = new HashMap<>();

    private Database() {}

    public static void add(Entity entity) throws InvalidEntityException {
        if (entity instanceof Validator) {
            Validator validator = validators.get(entity.getEntityCode());
            validator.validate(entity);
        }

        if (entity instanceof Trackable) {
            Date now = new Date();
            ((Trackable) entity).setCreationDate(now);
            ((Trackable) entity).setLastModificationDate(now);
        }

        entity.id = identifier;
        entities.add(entity.clone());
        identifier++;
    }

    public static Entity get(int id) throws EntityNotFoundException {
        for (Entity entity : entities) {
            if (entity.id == id) {
                return entity.clone();
            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void delete(int id) throws EntityNotFoundException {
        Entity entity = get(id);

        if (entity instanceof Task task) {
            ArrayList<Entity> steps = TaskService.getTaskSteps(task.id);
            for (Entity step : steps) {
                entities.remove(step);
            }
        }

        entities.remove(entity);
    }

    public static void update(Entity entity) throws EntityNotFoundException, InvalidEntityException {
        if (entity instanceof Validator) {
            Validator validator = validators.get(entity.getEntityCode());
            validator.validate(entity);
        }

        get(entity.id);

        if (entity instanceof Trackable) {
            Date now = new Date();
            ((Trackable) entity).setLastModificationDate(now);
        }

        int index = entities.indexOf(entity);
        entities.set(index, entity.clone());
    }

    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.containsKey(entityCode)) {
            throw new IllegalArgumentException("Entity with code " + entityCode + " already exists");
        }
        validators.put(entityCode, validator);
    }

    public static ArrayList<Entity> getAll(int entityCode) {
        ArrayList<Entity> sameEntityCode = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getEntityCode() == entityCode) {
                sameEntityCode.add(entity.clone());
            }
        }
        return sameEntityCode;
    }

}