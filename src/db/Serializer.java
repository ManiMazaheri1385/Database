package db;

public interface Serializer {
    String serialize(Entity entity);
    Entity deserialize(String data);
}
