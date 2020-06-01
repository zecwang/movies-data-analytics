package assignment02.query;

import assignment02.conf.MongoDB_Config;
import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashSet;

import static java.util.Arrays.asList;

public class GetUserRatedMovies {
	public static void main(String[] args) {
		System.out.println(compareTwoUsers(1, 21732));
	}
	
	public static Document getUserRatedMovies(int userid) {
		MongoDatabase db = MongoDB_Config.getDB("movie_sets");
		MongoCollection coll = db.getCollection("movie_info");
		
		AggregateIterable it = coll.aggregate(asList(
				Aggregates.unwind("$rating")
				, Aggregates.unwind("$rating.ratings")
				, Aggregates.match(new Document("rating.ratings.userid", userid))
				, Aggregates.group("$rating.ratings.userid", Accumulators.addToSet("movieid", "$movieid"))
		)).allowDiskUse(true);
		MongoCursor<Document> cursor = it.iterator();
		Document doc = cursor.next();
		return doc;
	}
	
	public static Boolean compareTwoUsers(int userid1, int userid2) {
		ArrayList<Integer> movies_id1 = (ArrayList<Integer>) getUserRatedMovies(userid1).get("movieid");
		ArrayList<Integer> movies_id2 = (ArrayList<Integer>) getUserRatedMovies(userid2).get("movieid");
		HashSet<Integer> set = new HashSet<>();
		for (int el : movies_id1)
			set.add(el);
		
		Boolean res = false;
		HashSet<Integer> same = new HashSet<>();
		
		for (int el : movies_id2) {
			if (!set.add(el)) {
				same.add(el);
				res = true;
			}
		}
		
		System.out.print("Movies both user " + userid1 + " and user " + userid2 + " rated : ");
		System.out.println(same);
		
		return res;
	}
}
