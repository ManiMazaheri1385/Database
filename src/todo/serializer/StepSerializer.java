package todo.serializer;

import db.Entity;
import db.Serializer;
import java.util.Date;
import java.util.Locale;
import todo.entity.Step;
import org.json.JSONObject;
import java.text.SimpleDateFormat;

public class StepSerializer implements Serializer {

    @Override
    public String serialize(Entity entity) {
        Step step = (Step) entity;

        JSONObject json = new JSONObject();

        json.put("entityCode", Step.STEP_ENTITY_CODE);
        json.put("id", step.id);
        json.put("taskRef", step.taskRef);
        json.put("title", step.title);
        json.put("status", step.status.name());
        json.put("creationDate", step.creationDate.toString());
        json.put("lastModificationDate", step.lastModificationDate.toString());

        return json.toString();
    }

    @Override
    public Step deserialize(String data) {
        JSONObject json = new JSONObject(data);

        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

        try {
            int id = json.getInt("id");
            int taskRef = json.getInt("taskRef");
            String title = json.getString("title");
            Step.Status status = Step.Status.valueOf(json.getString("status"));
            Date creationDate = format.parse(json.getString("creationDate"));
            Date lastModificationDate = format.parse(json.getString("lastModificationDate"));

            Step step = new Step(taskRef, title);
            step.id = id;
            step.status = status;
            step.creationDate = creationDate;
            step.lastModificationDate = lastModificationDate;

            return step;
        } catch (Exception e) {
            System.out.println("Error: Cannot load data.");
        }

        return null;
    }

}
