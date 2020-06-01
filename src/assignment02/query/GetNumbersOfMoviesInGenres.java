package assignment02.query;

import assignment02.conf.MongoDB_Config;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashSet;

import static java.util.Arrays.asList;

public class GetNumbersOfMoviesInGenres {
	public static void main(String[] args) {
		getNumbers();
	}
	
	public static void getNumbers() {
		MongoDatabase db = MongoDB_Config.getDB("movie_sets");
		MongoCollection coll = db.getCollection("movie_info");
		
		AggregateIterable it = coll.aggregate(asList(
				Aggregates.project(new Document("genre", 1).append("_id", 0))
		)).allowDiskUse(true);
		MongoCursor<Document> cursor = it.iterator();
		HashSet<String> types = new HashSet<>();
		
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			ArrayList list = (ArrayList) doc.get("genre");
			Document genres_doc = (Document) list.get(0);
			ArrayList<String> genres = (ArrayList<String>) genres_doc.get("genres");
			for (String el : genres)
				types.add(el);
		}
		
		for (String genre : types) {
			it = coll.aggregate(asList(
					Aggregates.unwind("$genre")
					, Aggregates.match(new Document("genre.genres", genre))
					, Aggregates.project(new Document("genre.genres", 1))
					, Aggregates.count("amount")
			)).allowDiskUse(true);
			cursor = it.iterator();
			while (cursor.hasNext()) {
				Document doc = new Document("genre", genre)
						.append("amount", cursor.next().get("amount"));
				System.out.println(doc.toJson());
			}
		}
	}
}
