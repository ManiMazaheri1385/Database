package db;

public abstract class Entity implements Cloneable {
    public int id;

    public abstract int getEntityCode();

    @Override
    public Entity clone() throws CloneNotSupportedException {
        return (Entity) super.clone();
    }

}
