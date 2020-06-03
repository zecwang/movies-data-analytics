from surprise import Dataset
from surprise import SVD, KNNWithMeans
from surprise import accuracy
from surprise.model_selection import train_test_split

data = Dataset.load_builtin('ml-100k')
train, test = train_test_split(data, test_size=0.25, random_state=10)

algo = SVD()
algo.n_epochs = 20
algo.random_state = 15
algo.fit(train)

predictions = algo.test(test)
accuracy.rmse(predictions)

uid = str(196)  # raw user id
iid = str(302)  # raw item id
r_ui = 4  # already know the true rating is 4, so we can make a comparison

pred = algo.predict(uid, iid, r_ui=r_ui, verbose=True)
print(pred.est)

knn = KNNWithMeans(sim_options={
	"name": "msd",  # cosine / msd / pearson / pearson_baseline
	"min_support": 2,
	"user_based": False
})
knn.fit(train)

predictions = knn.test(test)
accuracy.rmse(predictions)
pred = knn.predict(uid, iid, r_ui=r_ui, verbose=True)
print(pred.est)
