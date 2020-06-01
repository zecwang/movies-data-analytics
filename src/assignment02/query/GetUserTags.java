package assignment02.query;

import assignment02.conf.MongoDB_Config;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;

import static java.util.Arrays.asList;

public class GetUserTags {
	public static void main(String[] args) {
		getTags(109);
	}
	
	public static void getTags(int userid) {
		MongoDatabase db = MongoDB_Config.getDB("movie_sets");
		MongoCollection coll = db.getCollection("movie_info");
		Block<Document> printBlock = document -> System.out.println(document.toJson());
		
		coll.aggregate(asList(
				Aggregates.unwind("$tag")
				, Aggregates.unwind("$tag.tags")
				, Aggregates.match(new Document("tag.tags.userid", userid))
				, Aggregates.project(new Document("_id", 0)
						.append("rating", 0)
						.append("tag.tags.userid", 0)
				)
		)).allowDiskUse(true).forEach(printBlock);
	}
}
