package com.foodrescue.repository;

import java.util.ArrayList;
import java.util.List;

import com.foodrescue.domain.Restaurant;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
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

	public MongoRestaurant() {
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
			double[] locs = { Double.parseDouble(restaurant.getLatitude()),
					Double.parseDouble(restaurant.getLongitude()) };
			document.put("locs", locs);
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

	
	
	//find restaurants
	
	 public void nearWithMaxDistanceExample(String latitude, String longitude) {
		 
	        final BasicDBObject filter = new BasicDBObject("near", new double[] { Double.parseDouble(latitude), Double.parseDouble(longitude) });
	        filter.put("$maxDistance", (double)2500/6378137);

	        final BasicDBObject query = new BasicDBObject("locs", filter);

	        int count = 0;
	        for (final DBObject venue : this.col.find(query).toArray()) {
	            //System.out.println("---- near venue: " + venue.get("name"));
	            count++;
	        }
	        System.out.println(count);
	       
	    }

	// Find Restaurants
	public List<Restaurant> retrieveData(String latitude, String longitude) {
		List<Restaurant> restaurants = new ArrayList<>();

		BasicDBObject myCmd = new BasicDBObject();
		myCmd.append("geoNear", this.col.getName());
		double[] loc = { Double.parseDouble(latitude), Double.parseDouble(longitude) };

		myCmd.append("near", loc);
		myCmd.append("spherical", true);
		//myCmd.append("maxDistance", (double) 2500 / 6378137);
		myCmd.append("distanceMultiplier", 6378137);

		System.out.println(myCmd);
		
		this.col.createIndex(new BasicDBObject("point", "2dsphere"));
		CommandResult cursor = this.db.command(myCmd);
		System.out.println(cursor.toString());
		/*
		 * Restaurant restaurant = null; while (cursor.isEmpty()) { DBObject t =
		 * cursor.next(); restaurant = new Restaurant();
		 * restaurant.setName(t.get("name").toString());
		 * restaurant.setLocation(t.get("location").toString());
		 * restaurants.add(restaurant); }
		 */
		return restaurants;

	}

	public Restaurant getData(String phone, String password) {

		try {
			BasicDBObject document = new BasicDBObject();
			Restaurant restaurant = new Restaurant();
			document.put("phone", phone);
			document.put("password", password);
			DBCursor cursor = this.col.find(document);
			if (cursor.size() > 1 || cursor.size() == 0) {
				System.out.println("HII");
				return null;
			} else {
				DBObject t = cursor.next();
				restaurant.setName(t.get("name").toString());
				restaurant.setPhone(t.get("phone").toString());
				restaurant.setPassword(t.get("password").toString());
				restaurant.setLocation(t.get("location").toString());
				return restaurant;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	//delete data
	public boolean removeData(String phone, String password) {
		
		try {
			BasicDBObject document = new BasicDBObject();
			Restaurant restaurant = new Restaurant();
			document.put("phone", phone);
			document.put("password", password);
			DBCursor cursor = this.col.find(document);
			if (cursor.size() > 1 || cursor.size() == 0) {
				System.out.println("HII");
				return false;
			} else {
				this.col.remove(document);
				return true;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
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
