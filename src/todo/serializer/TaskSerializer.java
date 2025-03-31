package todo.serializer;

import db.Entity;
import db.Serializer;
import java.util.Date;
import java.util.Locale;
import todo.entity.Task;
import org.json.JSONObject;
import java.text.SimpleDateFormat;

public class TaskSerializer implements Serializer {

    @Override
    public String serialize(Entity entity) {
        Task task = (Task) entity;

        JSONObject json = new JSONObject();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat date = new SimpleDateFormat();

        json.put("entityCode", Task.TASK_ENTITY_CODE);
        json.put("id", task.id);
        json.put("title", task.title);
        json.put("description", task.description);
        json.put("dueDate", dateFormat.format(task.dueDate));
        json.put("status", task.status.name());
        json.put("creationDate", task.creationDate.toString());
        json.put("lastModificationDate", task.lastModificationDate.toString());

        return json.toString();
    }

    @Override
    public Task deserialize(String data) {
        JSONObject json = new JSONObject(data);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

        try {
            int id = json.getInt("id");
            String title = json.getString("title");
            String description = json.getString("description");
            Date dueDate = dateFormat.parse(json.getString("dueDate"));
            Task.Status status = Task.Status.valueOf(json.getString("status"));
            Date creationDate = format.parse(json.getString("creationDate"));
            Date lastModificationDate = format.parse(json.getString("lastModificationDate"));

            Task task = new Task(title, description, dueDate);
            task.id = id;
            task.status = status;
            task.creationDate = creationDate;
            task.lastModificationDate = lastModificationDate;

            return task;
        } catch (Exception e) {
            System.out.println("Error: Cannot load data.");
        }

        return null;
    }

}
