package assignment02.query;

import assignment02.conf.MongoDB_Config;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;

import static java.util.Arrays.asList;

public class GetAverageRatings {
	public static void main(String[] args) {
		MongoCursor<Document> cursor = getAvgRatings().iterator();
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			System.out.println(doc.toJson());
		}
	}
	
	public static AggregateIterable getAvgRatings() {
		MongoDatabase db = MongoDB_Config.getDB("movie_sets");
		MongoCollection coll = db.getCollection("movie_info");
		
		AggregateIterable it = coll.aggregate(asList(
				Aggregates.unwind("$rating"),
				Aggregates.unwind("$rating.ratings"),
				Document.parse("{ $group : { _id: { movieid:'$movieid',title:'$title' }, averageRating:{$avg: '$rating.ratings.rating'} } }")
		));
		
		return it;
		
	}
}
