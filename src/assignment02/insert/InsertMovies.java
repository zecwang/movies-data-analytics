package assignment02.insert;

import assignment02.conf.MongoDB_Config;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

import java.io.*;

public class InsertMovies {
	public static void main(String[] args) {
		try {
			insertALL();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void insertALL() throws IOException {
		String path = "src/assignment02/ml-10m/ml-10M100K/movies.dat";
		File filename = new File(path);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
		BufferedReader br = new BufferedReader(reader);
		String line;
		MongoDatabase db = MongoDB_Config.getDB("movie_sets").withWriteConcern(WriteConcern.MAJORITY);
		MongoCollection coll = db.getCollection("movies");
		MongoCollection index = db.getCollection("index");
		coll.drop();
		index.drop();
		IndexOptions indexOptions = new IndexOptions().unique(true);
		index.createIndex(Indexes.ascending("movieid"), indexOptions);
		coll.createIndex(Indexes.ascending("movieid", "genre"), indexOptions);
		while ((line = br.readLine()) != null) {
			String arr[] = line.split("::");
			Document indexDoc = new Document("movieid", Integer.parseInt(arr[0]))
					.append("title", arr[1]);
			index.insertOne(indexDoc);
			String genres[] = arr[2].split("\\|");
			for (String el : genres) {
				Document doc = new Document("movieid", Integer.parseInt(arr[0]))
						.append("title", arr[1])
						.append("genre", el);
				coll.insertOne(doc);
			}
		}
		br.close();
	}
}
