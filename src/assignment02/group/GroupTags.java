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

public class GroupTags {
	public static void main(String[] args) {
		doGroupTags();
	}
	
	public static void doGroupTags() {
		MongoDatabase db = MongoDB_Config.getDB("movie_sets").withWriteConcern(WriteConcern.MAJORITY);
		MongoCollection coll = db.getCollection("tags");
		
		MongoCollection tags_agg = db.getCollection("tags_agg");
		tags_agg.drop();
		
		AggregateIterable result = coll.aggregate(asList(
				//				Aggregates.unwind("$tags", new UnwindOptions().preserveNullAndEmptyArrays(true)),
				Aggregates.group("$movieid", Accumulators.addToSet("tags",
						new Document("userid", "$userid")
								.append("tag", "$tag")
								.append("timestamp", "$timestamp")
				)))).allowDiskUse(true);
		MongoCursor<Document> cursor = result.iterator();
		
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			tags_agg.insertOne(doc);
		}
	}
	
}
