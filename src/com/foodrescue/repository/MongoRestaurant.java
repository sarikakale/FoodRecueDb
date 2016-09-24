package com.foodrescue.repository;

import java.util.ArrayList;
import java.util.List;

import com.foodrescue.domain.Restaurant;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoRestaurant {

	private MongoConnection connection = null;
	private MongoClient client = null;
	private DB db = null;
	private DBCollection col = null;;

	
	public MongoRestaurant(){
		getConnection();
	}
	
	
	public DB getDB() {
		this.db = client.getDB(Constants.dbName);
		return this.db;
	}

	public boolean insertData(Restaurant restaurant) {
		try {

			BasicDBObject document = new BasicDBObject();
			document.put("name", restaurant.getName());
			document.put("location", restaurant.getLocation());
			document.put("latitude", restaurant.getLatitude());
			document.put("longitude", restaurant.getLongitude());
			document.put("phone", restaurant.getPhone());
			document.put("password", restaurant.getPassword());
			this.col.insert(document);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateData(Restaurant restaurant) {
		return false;
	}

	// Find Restaurants
	public List<Restaurant> retrieveData(String latitude, String longitude) {
		List<Restaurant> restaurants = new ArrayList<>();

		BasicDBObject myCmd = new BasicDBObject();
		myCmd.append("geoNear", "data");
		double[] loc = { Double.parseDouble(latitude), Double.parseDouble(longitude) };
		
		myCmd.append("near", loc);
		myCmd.append("spherical", true);
		myCmd.append("maxDistance", (double) 2500 / 6378137);
		myCmd.append("distanceMultiplier", 6378137);
		
		System.out.println(myCmd);

		DBCursor cursor = this.col.find(myCmd);
		
		Restaurant restaurant = null;
		while (cursor.hasNext()) {
			DBObject t = cursor.next();
			restaurant = new Restaurant();
			restaurant.setName(t.get("name").toString());
			restaurant.setLocation(t.get("location").toString());
			restaurants.add(restaurant);
		}

		return restaurants;

	}

	public boolean removeData(String restId) {
		
		return false;
	}

	public void closeConnection() {
		this.connection.closeConnection();
	}

	public void getConnection() {
		connection = new MongoConnection();
		client = connection.getConnection();
		this.db = getDB();
		this.col = this.db.getCollection("restaurants");
		
	}

}
