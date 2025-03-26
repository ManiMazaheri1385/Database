package db;

public abstract class Entity implements Cloneable {
    public int id;

    public abstract int getEntityCode();

    @Override
    public Entity clone() {
        try {
            return (Entity) super.clone();
        }
        catch (CloneNotSupportedException e) {
            System.out.println("Cloning failed!");
            return null;
        }
    }

}
