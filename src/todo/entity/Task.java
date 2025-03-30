package todo.entity;

import db.Entity;
import db.Trackable;
import java.util.Date;

public class Task extends Entity implements Trackable {
    public static final int TASK_ENTITY_CODE = 1;

    public String title;
    public String description;
    public Date dueDate;
    public Status status;

    public Task(String title, String description, Date dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        status = Status.NotStarted;
    }

    public enum Status {
        NotStarted, InProgress, Completed;
    }

    @Override
    public int getEntityCode() {
        return TASK_ENTITY_CODE;
    }

    @Override
    public boolean hasValidator() {
        return true;
    }

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    @Override
    public Task clone() {
        Task cloned = (Task) super.clone();
        cloned.dueDate = (Date) this.dueDate.clone();
        return cloned;
    }

}