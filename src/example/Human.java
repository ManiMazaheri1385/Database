package example;

import db.*;

public class Human extends Entity implements Cloneable{
    public String name;

    public Human(String name) {
        this.name = name;
    }

    @Override
    public Human clone() throws CloneNotSupportedException {
        return (Human) super.clone();
    }

}