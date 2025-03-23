package db;

public abstract class Entity implements Cloneable {
    public int id;

    @Override
    public Entity clone() throws CloneNotSupportedException {
        return (Entity) super.clone();
    }

}
