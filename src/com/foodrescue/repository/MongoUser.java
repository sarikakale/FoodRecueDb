package com.foodrescue.repository;

import java.util.List;
import com.foodrescue.domain.Restaurant;
import com.foodrescue.domain.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoUser {
	
	MongoConnection connection=null;
	MongoClient client = null;
	
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

	public MongoClient getConnection() {
		connection = new MongoConnection();
		client= connection.getConnection();
		return client;
	}

	public void closeConnection() {
		connection.closeConnection();
	}

	public DB getDB(){
		return client.getDB(Constants.dbName);
	}
	
	public boolean insertData(User user) {
		try {
			DB db = getDB();
			DBCollection collection = db.getCollection("users");
			BasicDBObject document = new BasicDBObject();
			document.put("name", user.getName());
			document.put("deviceId", user.getDeviceId());
			document.put("location", user.getLocation());
			document.put("phone", user.getPhone());
			document.put("password", user.getPassword());
			collection.insert(document);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
	}

}
