package todo.entity;

import db.Entity;

public class Step extends Entity {
    public static final int STEP_ENTITY_CODE = 2;

    public int taskRef;
    public String title;
    public Status status;

    public enum Status {
        NotStarted, Completed;
    }

    public Step(int taskRef, String title) {
        this.taskRef = taskRef;
        this.title = title;
        this.status = Status.NotStarted;
    }

    @Override
    public int getEntityCode() {
        return STEP_ENTITY_CODE;
    }

    @Override
    public Step clone() {
        return (Step) super.clone();
    }

}
