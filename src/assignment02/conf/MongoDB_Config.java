package assignment02.conf;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDB_Config {
	public static MongoDatabase getDB(String dbName) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase db = mongoClient.getDatabase(dbName);
		return db;
	}
}
