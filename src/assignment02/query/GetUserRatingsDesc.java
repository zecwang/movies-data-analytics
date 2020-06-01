package assignment02.query;

import assignment02.conf.MongoDB_Config;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;

import static java.util.Arrays.asList;

public class GetUserRatingsDesc {
	public static void main(String[] args) {
		getUserRatingsDesc(2);
	}
	
	public static void getUserRatingsDesc(int userid) {
		MongoDatabase db = MongoDB_Config.getDB("movie_sets");
		MongoCollection coll = db.getCollection("movie_info");
		Block<Document> printBlock = document -> System.out.println(document.toJson());
		
		coll.aggregate(asList(
				Aggregates.unwind("$rating")
				, Aggregates.unwind("$rating.ratings")
				, Aggregates.match(new Document("rating.ratings.userid", userid))
				, Document.parse("{ $group : { _id: { movieid:'$movieid',title:'$title' }, averageRating:{$avg: '$rating.ratings.rating'} } }")
				, Aggregates.sort(new Document("averageRating", -1))
		)).allowDiskUse(true).forEach(printBlock);
	}
}
