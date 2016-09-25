package com.foodrescue.repository;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoConnection {
	String textUri = "mongodb://foodrescue:foodrescue@ds041556.mlab.com:41556/foodrescue";
	Mongo mongo = null;
	MongoClient mongoClient=null;
	MongoClientURI uri = null;
	
	
	
	public   MongoConnection(){
		/* mongo=new Mongo("ds041556.mlab.com",41556);
		 mongoClient = new MongoClient();*/
		
		 try {
			 uri  = new MongoClientURI(textUri);
			 mongoClient = new MongoClient(uri);
			 System.out.println("CLiet is OK");
			DB db = mongoClient.getDB(Constants.dbName);
						
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	//get the mongo connection
	public MongoClient getConnection(){
		 try {
			uri  = new MongoClientURI(textUri);
			mongoClient = new MongoClient(uri);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return mongoClient;
	}
	
	//Close Connection
	public void closeConnection(){
		mongoClient.close();
	}
}
	
	