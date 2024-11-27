package utils;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.MongoClientSettings;
import model.*;
import model.enums.Role;
import model.types.Coordinates;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;

import java.util.HashMap;
import java.util.List;

public class MongoUtil {
    //TODO: remove hardcoded mess and start reading from dbconfig file in resources.
    private static String URI = "mongodb://localhost:27017";
    private static String DATABASE_NAME = "bastion";
    private static HashMap<Class, String> COLLECTIONS = new HashMap<>();
    MongoClient mongoClient;
    MongoDatabase database;

    public MongoUtil() {

        COLLECTIONS.put(Employee.class, "employee");
        COLLECTIONS.put(Device.class, "device");
        COLLECTIONS.put(SkyObject.class, "sky_object");
        COLLECTIONS.put(SkyState.class, "sky_state");
        connectToDatabase();
    }

    public <T> void save(T object) {
        if (object == null) {
            throw new IllegalArgumentException("Object to save cannot be null.");
        }

        String collectionName = COLLECTIONS.get(object.getClass());
        if (collectionName == null) {
            throw new IllegalArgumentException("No collection mapping found for class: " + object.getClass().getName());
        }

        MongoCollection<T> collection = database.getCollection(collectionName, (Class<T>) object.getClass());
        collection.insertOne(object);
    }

    public <T> void saveAll(List<T> objects) {
        if (objects == null) {
            throw new IllegalArgumentException("Objects to save cannot be null.");
        }

        Class<T> tClass = (Class<T>) objects.getFirst().getClass();
        String collectionName = COLLECTIONS.get(tClass);

        if (collectionName == null) {
            throw new IllegalArgumentException("No collection mapping found for class: " + tClass.getName());
        }

        MongoCollection<T> collection = database.getCollection(collectionName, tClass);
        collection.insertMany(objects);
    }


    private void connectToDatabase() {
        try {
            mongoClient = MongoClients.create(URI);
            database = mongoClient
                    .getDatabase(DATABASE_NAME)
                    .withCodecRegistry(getCodecRegistry());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private CodecRegistry getCodecRegistry() {
        return fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(
                        PojoCodecProvider.builder()
                                .automatic(true)
                                .build()));

    }
}