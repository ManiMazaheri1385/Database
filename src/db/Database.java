package db;

import db.exception.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private static int identifier = 1;

    private static ArrayList<Entity> entities = new ArrayList<>();
    private static HashMap<Integer, Validator> validators = new HashMap<>();

    private Database() {}

    public static void add(Entity entity) throws InvalidEntityException {
        Validator validator = validators.get(entity.getEntityCode());
        validator.validate(entity);

        entity.id = identifier;

        try {
            entities.add(entity.clone());
        }
        catch (CloneNotSupportedException ex) {
            System.out.println("Cloning failed!");
        }

        identifier++;
    }

    public static Entity get(int id) throws EntityNotFoundException {
        for (Entity entity : entities) {
            if (entity.id == id) {

                try {
                    return entity.clone();
                }
                catch (CloneNotSupportedException e) {
                    System.out.println("Cloning failed!");
                }

            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void delete(int id) throws EntityNotFoundException {
        Entity entity = get(id);
        entities.remove(entity);
    }

    public static void update(Entity entity) throws EntityNotFoundException, InvalidEntityException {
        Validator validator = validators.get(entity.getEntityCode());
        validator.validate(entity);

        get(entity.id);
        int index = entities.indexOf(entity);

        try {
            entities.set(index, entity.clone());
        }
        catch (CloneNotSupportedException ex) {
            System.out.println("Cloning failed!");
        }
    }

    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.containsKey(entityCode)) {
            throw new IllegalArgumentException("Entity with code " + entityCode + " already exists");
        }
        validators.put(entityCode, validator);
    }

}