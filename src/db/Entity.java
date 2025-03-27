package db;

import java.util.Date;

public abstract class Entity implements Cloneable {
    public int id;
    public Date creationDate;
    public Date lastModificationDate;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Entity entity = (Entity) object;
        return id == entity.id;
    }

}
