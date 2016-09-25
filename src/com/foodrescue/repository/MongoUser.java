package com.foodrescue.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.foodrescue.domain.Restaurant;
import com.foodrescue.domain.User;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoUser {

	private MongoConnection connection = null;
	private MongoClient client = null;
	private DB db = null;
	private DBCollection col = null;

	public MongoUser() {

		getConnection();
	}

	public boolean removeData(String restId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateData(User user) {
		try {

			BasicDBObject newDocument = new BasicDBObject();
			
			BasicDBObject searchQuery = new BasicDBObject().append("hosting", "hostB");

			
			double[] locs = { Double.parseDouble(user.getLongitude()), Double.parseDouble(user.getLatitude()) };
			BasicDBObject document = new BasicDBObject();
			document.append("$set",
					new BasicDBObject().append("deviceId", user.getDeviceId()).append("phone", user.getPhone())
							.append("location", user.getLocation()).append("locs", locs)
							.append("latitude", user.getLatitude()).append("longitude", user.getLongitude()));
			BasicDBObject query = new BasicDBObject().append("phone", user.getPhone());

			

			this.col.update(query, document);
			return true;
		} catch (Exception e) {
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
		this.col = this.db.getCollection("users");

	}

	public DB getDB() {
		this.db = client.getDB(Constants.dbName);
		return this.db;
	}

	public User login(String phone, String password) {
		try {
			BasicDBObject document = new BasicDBObject();
			User user = new User();
			document.put("phone", phone);
			document.put("password", password);
			DBCursor cursor = this.col.find(document);
			if (cursor.size() > 1 || cursor.size() == 0) {
				System.out.println("HII");
				return null;
			} else {
				/*
				 * DBObject t = cursor.next();
				 * restaurant.setName(t.get("name").toString());
				 * restaurant.setPhone(t.get("phone").toString());
				 * restaurant.setPassword(t.get("password").toString());
				 * restaurant.setAddress(t.get("location").toString());
				 */
				return user;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public boolean insertData(User user) {
		try {

			BasicDBObject document = new BasicDBObject();
			document.put("name", user.getName());
			document.put("deviceId", user.getDeviceId());
			document.put("location", user.getLocation());
			double[] locs = { Double.parseDouble(user.getLongitude()), Double.parseDouble(user.getLatitude()) };
			document.put("locs", locs);
			document.put("latitude", user.getLatitude());
			document.put("longitude", user.getLongitude());
			document.put("phone", user.getPhone());
			document.put("password", user.getPassword());
			this.col.insert(document);
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

	// Retrieve all data of users
	public List<String> retrieveData(String latitude, String longitude) {
		List<String> users = new ArrayList<>();

		try {

			BasicDBObject myCmd = new BasicDBObject();
			BasicDBObject index = new BasicDBObject("locs", "2d");

			System.out.println(this.col.getName());

			myCmd.append("geoNear", "users");
			double[] loc = { Double.parseDouble(longitude), Double.parseDouble(latitude) };
			System.out.println(loc[0]);
			myCmd.append("near", loc);
			myCmd.append("spherical", true);
			myCmd.append("maxDistance", Constants.maxDistance);
			myCmd.append("nums", Constants.limit);
			if (!isValidLngLat(loc[0], loc[1])) {
				System.out.println("Location coordinates are not valid");
				return null;
			}
			System.out.println(myCmd);

			this.col.createIndex(index);
			CommandResult cmdResult = this.db.command(myCmd);
			User user = null;
			System.out.println(cmdResult);
			BasicDBList results = (BasicDBList) cmdResult.get("results");

			for (Iterator<Object> it = results.iterator(); it.hasNext();) {
				BasicDBObject result = (BasicDBObject) it.next();
				user = new User();
				BasicDBObject dbo = (BasicDBObject) result.get("obj");
				users.add(dbo.getString("deviceId"));
			}
			return users;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return users;
	}

}
