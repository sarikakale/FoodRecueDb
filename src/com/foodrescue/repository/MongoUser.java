package com.foodrescue.repository;

import java.util.List;
import com.foodrescue.domain.Restaurant;
import com.foodrescue.domain.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoUser {
	
	private MongoConnection connection = null;
	private MongoClient client = null;
	private DB db = null;
	private DBCollection col = null;
	
	public  MongoUser() {
		 
		getConnection();
	}
	public List<User> retrieveData(String User) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean removeData(String restId) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean updateData(User user){
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

	public DB getDB() {
		this.db = client.getDB(Constants.dbName);
		return this.db;
	}
	
	public boolean insertData(User user) {
		try {

			BasicDBObject document = new BasicDBObject();
			document.put("name", user.getName());
			document.put("deviceId", user.getDeviceId());
			document.put("location", user.getLocation());
			document.put("phone", user.getPhone());
			document.put("password", user.getPassword());
			this.col.insert(document);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
	}

}
