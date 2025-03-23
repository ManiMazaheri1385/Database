package db;

import db.exception.*;
import java.util.ArrayList;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static int identifier = 1;

    private Database() {}

    public static void add(Entity e) {
        e.id = identifier;

        try {
            entities.add(e.clone());
        }
        catch (CloneNotSupportedException ex) {
            System.out.println("Cloning failed!");
        }

        identifier++;
    }

    public static Entity get(int id) throws EntityNotFoundException {
        for (Entity e : entities) {
            if (e.id == id) {

                try {
                    return e.clone();
                }
                catch (CloneNotSupportedException ex) {
                    System.out.println("Cloning failed!");
                }

            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void delete(int id) throws EntityNotFoundException {
        Entity e = get(id);
        entities.remove(e);
    }

    public static void update(Entity e) throws EntityNotFoundException {
        get(e.id);
        int index = entities.indexOf(e);

        try {
            entities.set(index, e.clone());
        }
        catch (CloneNotSupportedException ex) {
            System.out.println("Cloning failed!");
        }

    }

}