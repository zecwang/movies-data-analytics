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

public class GroupRatings {
	public static void main(String[] args) {
		doGroupRatings();
	}
	
	public static void doGroupRatings() {
		MongoDatabase db = MongoDB_Config.getDB("movie_sets").withWriteConcern(WriteConcern.MAJORITY);
		MongoCollection coll = db.getCollection("ratings");
		MongoCollection ratings_agg = db.getCollection("ratings_agg");
		ratings_agg.drop();
		
		AggregateIterable result = coll.aggregate(asList(
				Aggregates.group("$movieid", Accumulators.addToSet("ratings",
						new Document("userid", "$userid")
								.append("rating", "$rating")
								.append("timestamp", "$timestamp")
				)))
		).allowDiskUse(true);
		
		MongoCursor<Document> cursor = result.iterator();
		
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			ratings_agg.insertOne(doc);
		}
	}
}
