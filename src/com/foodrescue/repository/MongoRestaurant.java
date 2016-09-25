package com.foodrescue.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.foodrescue.domain.Restaurant;
import com.mongodb.BasicDBList;
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
	private DBCollection col = null;

	public MongoRestaurant() {
		getConnection();
	}

	public DB getDB() {
		this.db = client.getDB(Constants.dbName);
		return this.db;
	}
	
	//Insert data
	public boolean insertData(Restaurant restaurant) {
		try {
			
			if(getData(restaurant.getPhone())){
				return false;
			}
			
			BasicDBObject document = new BasicDBObject();
			document.put("name", restaurant.getName());
			document.put("location", restaurant.getLocation());
			double[] locs = { Double.parseDouble(restaurant.getLongitude()),
					Double.parseDouble(restaurant.getLatitude()) };
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
	
	//Check Duplicates
	private boolean getData(String phone) {
		try {
			BasicDBObject document = new BasicDBObject();
			
			Restaurant restaurant = new Restaurant();
			
			document.put("phone", phone);
			
			DBCursor cursor = this.col.find(document);
			
			if (cursor.size() != 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	//Update Data
	public boolean updateData(Restaurant restaurant) {
		try {

			BasicDBObject document = new BasicDBObject();
			
			BasicDBObject query = new BasicDBObject();
			query.put("phone", restaurant.getPhone());
			
			document.put("name", restaurant.getName());
			document.put("location", restaurant.getLocation());
			double[] locs = { Double.parseDouble(restaurant.getLongitude()),
					Double.parseDouble(restaurant.getLatitude()) };
			document.put("locs", locs);
			document.put("latitude", restaurant.getLatitude());
			document.put("longitude", restaurant.getLongitude());
			document.put("phone", restaurant.getPhone());
			document.put("password", restaurant.getPassword());
			this.col.update(query,document);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// find restaurants
	boolean isValidLngLat(double lng, double lat) {
		return lat >= -90 && lat <= 90 && lng >= -180 && lng <= 180;
	}

	// Find Restaurants
	public List<Restaurant> retrieveData(String latitude, String longitude) {

		List<Restaurant> restaurants = new ArrayList<>();
		;
		try {

			BasicDBObject myCmd = new BasicDBObject();

			System.out.println(this.col.getName());

			myCmd.append("geoNear", "restaurants");
			double[] loc = { Double.parseDouble(longitude), Double.parseDouble(latitude) };
			myCmd.append("near", loc);
			myCmd.append("spherical", true);
			myCmd.append("maxDistance", Constants.maxDistance);

			if (!isValidLngLat(loc[0], loc[1])) {
				System.out.println("Location coordinates are not valid");
				return null;
			}
			System.out.println(myCmd);

			CommandResult cmdResult = this.db.command(myCmd);
			Restaurant restaurant = null;

			BasicDBList results = (BasicDBList) cmdResult.get("results");

			for (Iterator<Object> it = results.iterator(); it.hasNext();) {
				BasicDBObject result = (BasicDBObject) it.next();
				restaurant = new Restaurant();
				BasicDBObject dbo = (BasicDBObject) result.get("obj");
				restaurant.setName(dbo.getString("name"));
				restaurant.setLocation(dbo.getString("location"));
				restaurants.add(restaurant);
			}
			return restaurants;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return restaurants;

	}

	// login check to check if user login is valid
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

	// delete data
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

	// Close Connection
	public void closeConnection() {
		this.connection.closeConnection();
	}

	// Get Connection
	public void getConnection() {
		connection = new MongoConnection();
		client = connection.getConnection();
		this.db = getDB();
		this.col = this.db.getCollection("restaurants");

	}

}
