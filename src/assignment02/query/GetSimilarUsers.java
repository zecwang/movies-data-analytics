package assignment02.query;

import assignment02.conf.MongoDB_Config;
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

public class GetSimilarUsers {
	public static void main(String[] args) {
		System.out.println("similar user(s) : " + getSimilarUsers(34789));
		System.out.println("similar user(s) : " + getSimilarUsers(3));
	}
	
	public static HashSet<Integer> getSimilarUsers(int userid) {
		MongoDatabase db = MongoDB_Config.getDB("movie_sets");
		MongoCollection coll = db.getCollection("movie_info");
		
		AggregateIterable it = coll.aggregate(asList(
				Aggregates.unwind("$rating")
				, Aggregates.unwind("$rating.ratings")
				, Aggregates.group("$movieid", Accumulators.addToSet("users", "$rating.ratings.userid"))
				, Aggregates.match(new Document("users", userid))
		)).allowDiskUse(true);
		MongoCursor<Document> cursor = it.iterator();
		HashSet<Integer> filter = new HashSet<>();
		HashSet<Integer> similar_users = new HashSet<>();
		
		if (cursor.hasNext()) {
			Document res = cursor.next();
			ArrayList<Integer> users = (ArrayList<Integer>) res.get("users");
			filter.addAll(users);
		}
		filter.remove(userid);
		
		while (cursor.hasNext()) {
			Document res = cursor.next();
			ArrayList<Integer> users = (ArrayList<Integer>) res.get("users");
			similar_users.clear();
			for (int el : users) {
				if (!filter.add(el))
					similar_users.add(el);
				else filter.remove(el);
			}
			filter.clear();
			filter.addAll(similar_users);
		}
		
		System.out.println("Number of similar users for user " + userid + " : " + similar_users.size());
		
		return similar_users;
	}
}
