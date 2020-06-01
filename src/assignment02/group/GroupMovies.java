package assignment02.group;

import assignment02.conf.MongoDB_Config;
import com.mongodb.WriteConcern;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;

import static java.util.Arrays.asList;

public class GroupMovies {
	public static void main(String[] args) {
		doGroupMovies();
	}
	
	public static void doGroupMovies() {
		MongoDatabase db = MongoDB_Config.getDB("movie_sets").withWriteConcern(WriteConcern.MAJORITY);
		MongoCollection coll = db.getCollection("movies");
		
		MongoCollection movies_agg = db.getCollection("movies_agg");
		movies_agg.drop();
		
		AggregateIterable result = coll.aggregate(asList(
				Aggregates.group("$movieid", Accumulators.addToSet("genres", "$genre"
				)))).allowDiskUse(true);
		MongoCursor<Document> cursor = result.iterator();
		
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			movies_agg.insertOne(doc);
		}
	}
}
