# Movies Data Analytics

## Timeline

- Start Date: 2018/09/28
- Last Update: 2018/10/04

## Data aggregated structure

```json
{
	"_id": {
		"$oid": "5bae817fa51314219e24d74a"
	},
	"movieid": 122,
	"title": "Boomerang (1992)",
	"genre": [
		{
			"genres": [
				"Romance",
				"Comedy"
			]
		}
	],
	"tag": [
		{
			"tags": [
				{
					"userid": 40294,
					"tag": "Can't remember",
					"timestamp": {
						"$numberLong": "1149823823"
					}
				},
				{
					"userid": 30167,
					"tag": "Nudity (Topless - Brief)",
					"timestamp": {
						"$numberLong": "1159062512"
					}
				},
				{
					"userid": 9316,
					"tag": "dating",
					"timestamp": {
						"$numberLong": "1137373679"
					}
				}
			]
		}
	],
	"rating": [
		{
			"ratings": [
				{
					"userid": 70819,
					"rating": 2.0,
					"timestamp": {
						"$numberLong": "941460493"
					}
				},
				{
					"userid": 70564,
					"rating": 3.0,
					"timestamp": {
						"$numberLong": "941405758"
					}
				},
				{
					"userid": 70777,
					"rating": 3.0,
					"timestamp": {
						"$numberLong": "941463084"
					}
				}
			]
		}
	]
}
```

## Queries & Results

**Query 1: Write a query that finds average rating of each movie.**

Part of the results:

```json
{ "_id" : { "movieid" : 6200, "title" : "Insignificance (1985)" }, "averageRating" : 3.5795454545454546 }
{ "_id" : { "movieid" : 7872, "title" : "Getting It Right (1989)" }, "averageRating" : 3.5 }
{ "_id" : { "movieid" : 7878, "title" : "Straight to Hell (1987)" }, "averageRating" : 2.9347826086956523 }
{ "_id" : { "movieid" : 7880, "title" : "Friday Night (Vendredi Soir) (2002)" }, "averageRating" : 3.0 }
{ "_id" : { "movieid" : 8937, "title" : "Friday Night Lights (2004)" }, "averageRating" : 3.5375375375375375 }
{ "_id" : { "movieid" : 7881, "title" : "White Zombie (1932)" }, "averageRating" : 3.111111111111111 }
```

**Query 2: Write a query that finds users who are similar to a given user (target user), the id of the target user is an input parameter. Users are similar to the target user if they rate the same movies.**

Results:

```json
Target userid: 34789
Number of similar users for user 34789 : 4
similar user(s) : [59269, 29877, 30167, 14463]
Target userid: 3
Number of similar users for user 3 : 1
similar user(s) : [59269]
```

**Query 3: Write a query that finds to number of movies in each genre.**

Results:

```json
{ "genre" : "Film-Noir", "amount" : 148 }
{ "genre" : "Action", "amount" : 1473 }
{ "genre" : "Adventure", "amount" : 1025 }
{ "genre" : "Horror", "amount" : 1013 }
{ "genre" : "Romance", "amount" : 1685 }
{ "genre" : "War", "amount" : 511 }
{ "genre" : "Western", "amount" : 275 }
{ "genre" : "Documentary", "amount" : 482 }
{ "genre" : "Sci-Fi", "amount" : 754 }
{ "genre" : "Drama", "amount" : 5339 }
{ "genre" : "Thriller", "amount" : 1706 }
{ "genre" : "(no genres listed)", "amount" : 1 }
{ "genre" : "Crime", "amount" : 1118 }
{ "genre" : "Fantasy", "amount" : 543 }
{ "genre" : "Animation", "amount" : 286 }
{ "genre" : "IMAX", "amount" : 29 }
{ "genre" : "Comedy", "amount" : 3703 }
{ "genre" : "Mystery", "amount" : 509 }
{ "genre" : "Children", "amount" : 528 }
{ "genre" : "Musical", "amount" : 436 }
```

**Query 4: Write a query that shows the movies a target user rated in descending order of the rating scores.**

Results:

```json
Target userid: 2
{ "_id" : { "movieid" : 110, "title" : "Braveheart (1995)" }, "averageRating" : 5.0 }
{ "_id" : { "movieid" : 260, "title" : "Star Wars: Episode IV - A New Hope (a.k.a. Star Wars) (1977)" }, "averageRating" : 5.0 }
{ "_id" : { "movieid" : 590, "title" : "Dances with Wolves (1990)" }, "averageRating" : 5.0 }
{ "_id" : { "movieid" : 1210, "title" : "Star Wars: Episode VI - Return of the Jedi (1983)" }, "averageRating" : 4.0 }
{ "_id" : { "movieid" : 1544, "title" : "Lost World: Jurassic Park, The (Jurassic Park 2) (1997)" }, "averageRating" : 3.0 }
{ "_id" : { "movieid" : 1391, "title" : "Mars Attacks! (1996)" }, "averageRating" : 3.0 }
{ "_id" : { "movieid" : 1356, "title" : "Star Trek: First Contact (1996)" }, "averageRating" : 3.0 }
{ "_id" : { "movieid" : 1049, "title" : "Ghost and the Darkness, The (1996)" }, "averageRating" : 3.0 }
{ "_id" : { "movieid" : 1073, "title" : "Willy Wonka & the Chocolate Factory (1971)" }, "averageRating" : 3.0 }
{ "_id" : { "movieid" : 376, "title" : "River Wild, The (1994)" }, "averageRating" : 3.0 }
{ "_id" : { "movieid" : 786, "title" : "Eraser (1996)" }, "averageRating" : 3.0 }
{ "_id" : { "movieid" : 151, "title" : "Rob Roy (1995)" }, "averageRating" : 3.0 }
{ "_id" : { "movieid" : 539, "title" : "Sleepless in Seattle (1993)" }, "averageRating" : 3.0 }
{ "_id" : { "movieid" : 780, "title" : "Independence Day (a.k.a. ID4) (1996)" }, "averageRating" : 3.0 }
{ "_id" : { "movieid" : 733, "title" : "Rock, The (1996)" }, "averageRating" : 3.0 }
{ "_id" : { "movieid" : 719, "title" : "Multiplicity (1996)" }, "averageRating" : 3.0 }
{ "_id" : { "movieid" : 736, "title" : "Twister (1996)" }, "averageRating" : 3.0 }
{ "_id" : { "movieid" : 858, "title" : "Godfather, The (1972)" }, "averageRating" : 2.0 }
{ "_id" : { "movieid" : 648, "title" : "Mission: Impossible (1996)" }, "averageRating" : 2.0 }
{ "_id" : { "movieid" : 802, "title" : "Phenomenon (1996)" }, "averageRating" : 2.0 }
```

**Query 5: Write a query that finds a target user’s tags.**

Part of the results:

```json
Target userid: 109
{ "movieid" : 36, "title" : "Dead Man Walking (1995)", "genre" : [{ "genres" : ["Drama", "Crime"] }], "tag" : { "tags" : { "tag" : "death penalty", "timestamp" : { "$numberLong" : "1211433235" } } } }
{ "movieid" : 904, "title" : "Rear Window (1954)", "genre" : [{ "genres" : ["Thriller", "Mystery"] }], "tag" : { "tags" : { "tag" : "Hitchcock", "timestamp" : { "$numberLong" : "1165555281" } } } }
{ "movieid" : 1219, "title" : "Psycho (1960)", "genre" : [{ "genres" : ["Thriller", "Horror"] }], "tag" : { "tags" : { "tag" : "Alfred Hitchcock", "timestamp" : { "$numberLong" : "1165555288" } } } }
{ "movieid" : 1258, "title" : "Shining, The (1980)", "genre" : [{ "genres" : ["Thriller", "Horror"] }], "tag" : { "tags" : { "tag" : "based on a book", "timestamp" : { "$numberLong" : "1231122288" } } } }
{ "movieid" : 1258, "title" : "Shining, The (1980)", "genre" : [{ "genres" : ["Thriller", "Horror"] }], "tag" : { "tags" : { "tag" : "Stephen King", "timestamp" : { "$numberLong" : "1165555223" } } } }
{ "movieid" : 2011, "title" : "Back to the Future Part II (1989)", "genre" : [{ "genres" : ["Sci-Fi", "Comedy", "Adventure", "Action"] }], "tag" : { "tags" : { "tag" : "time travel", "timestamp" : { "$numberLong" : "1184166988" } } } }
```

**Query 6: Write a query that finds movies both two target users rated.**
Results:

```
Two target userids: 1, 21732
Movies both user 1 and user 21732 rated : [480, 355, 356, 292, 420, 231, 616, 520, 329, 586, 362, 588, 364, 589, 594, 466, 370, 185, 377, 122, 539, 316]
```

