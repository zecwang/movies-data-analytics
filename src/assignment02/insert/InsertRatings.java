package assignment02.insert;

import assignment02.conf.MongoDB_Config;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

import java.io.*;

public class InsertRatings {
	public static void main(String[] args) {
		try {
			insertAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void insertAll() throws IOException {
		String path = "src/assignment02/ml-10m/ml-10M100K/ratings.dat";
		File filename = new File(path);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
		BufferedReader br = new BufferedReader(reader);
		String line;
		MongoDatabase db = MongoDB_Config.getDB("movie_sets").withWriteConcern(WriteConcern.MAJORITY);
		MongoCollection coll = db.getCollection("ratings");
		coll.drop();
		IndexOptions indexOptions = new IndexOptions().unique(true);
		coll.createIndex(Indexes.ascending("userid", "movieid", "timestamp"), indexOptions);
		while ((line = br.readLine()) != null) {
			String arr[] = line.split("::");
			Document doc = new Document("userid", Integer.parseInt(arr[0]))
					.append("movieid", Integer.parseInt(arr[1]))
					.append("rating", Double.parseDouble(arr[2]))
					.append("timestamp", Long.parseLong(arr[3]));
			coll.insertOne(doc);
		}
		br.close();
		
	}
}
