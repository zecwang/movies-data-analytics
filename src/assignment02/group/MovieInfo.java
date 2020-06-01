package assignment02.group;

import assignment02.conf.MongoDB_Config;
import com.mongodb.WriteConcern;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Variable;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Filters.expr;
import static java.util.Arrays.asList;

public class MovieInfo {
	public static void main(String[] args) {
		createMovieInfoCollection();
	}
	
	public static void createMovieInfoCollection() {
		MongoDatabase db = MongoDB_Config.getDB("movie_sets").withWriteConcern(WriteConcern.MAJORITY);
		MongoCollection coll = db.getCollection("index");
		List<Variable<?>> let = asList(new Variable("movie_id", "$movieid"));
		List<Bson> pipeline = asList(match(expr(new Document("$eq", asList("$_id", "$$movie_id")))),
				project(new Document("_id", 0))
		);
		AggregateIterable result = coll.aggregate(asList(
				Aggregates.lookup("movies_agg", let, pipeline, "genre"),
				Aggregates.lookup("tags_agg", let, pipeline, "tag"),
				Aggregates.lookup("ratings_agg", let, pipeline, "rating")
		));
		MongoCursor<Document> cursor = result.iterator();
		MongoCollection targetColl = db.getCollection("movie_info");
		targetColl.drop();
		
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			targetColl.insertOne(doc);
		}
		
	}
}
