package com.foodrescue.repository;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoConnection {
	String textUri = "mongodb://foodrescue:foodrescue@ds041556.mlab.com:41556/foodrescue";
//	Mongo mongo = null;
	MongoClient mongoClient=null;
	MongoClientURI uri = null;
	
	
	
	public   MongoConnection(){
	
	}
	
	
	
	//get the mongo connection
	public MongoClient getConnection(){
		 try {
			uri  = new MongoClientURI(Constants.connectionURI);
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
	
	