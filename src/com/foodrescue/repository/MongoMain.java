package com.foodrescue.repository;

import java.util.List;

import com.foodrescue.domain.Restaurant;
import com.foodrescue.domain.User;

public class MongoMain {

	public static void main(String[] args) {

		MongoRestaurant mongoRestaurant = new MongoRestaurant();
		Restaurant restaurant = new Restaurant();
		restaurant.setName("Jack in the box ");
		restaurant.setPhone("4082440255");
		restaurant.setPassword("abcd");
		restaurant.setLocation("911 El Camino Real, Santa Clara, CA 95050");
		restaurant.setLatitude("37.354107");
		restaurant.setLongitude("-121.955238");
		//MongoUser mongoUser=new MongoUser();
		//MongoRestaurant mongoRestaurant = new MongoRestaurant();
		/*MongoUser mongoUser=new MongoUser();
		User user = new User();
		user.setName("Jack New");
		user.setDeviceId("1234abcdefghixyzer");
		user.setPhone("4082440255");
		user.setPassword("abcd");
		user.setLocation("911 El Camino Real, Santa Clara, CA 95050");
		user.setLatitude("37.354107");
		user.setLongitude("-121.955238");*/
		
		
		//mongoUser.insertData(user);
		
		// mongoRestaurant.removeData("40856788899", "pass123");
		// mongoRestaurant.updateData(restaurant);
		//mongoRestaurant.insertData(restaurant);
		//mongoUser.insertData(user);
		System.out.println(mongoRestaurant.updateData(restaurant));
	}

	public boolean login(User user) {
		MongoUser mongoUser = new MongoUser();
		User returnParameter = mongoUser.login(user.getPhone(), user.getPassword());
		System.out.println("returnParameter-----" + returnParameter);
		if (returnParameter == null) {
			return false;
		} else {
			return true;

		}
	}

	public void insertData(User user) {
		System.out.println("hello");
		MongoUser mongoUser = new MongoUser();
		mongoUser.insertData(user);
	}
	
	//Retrieve Restaurant List
	public List<Restaurant> getRestaurantData(String latitude, String longitude){
		MongoRestaurant restaurant=new MongoRestaurant();
		return restaurant.retrieveData(latitude, longitude);
		
	}
	
	//Retrieve User List
	public List<String> getUserData(String latitude,String longitude){
		MongoUser mongoUser=new MongoUser();
		return mongoUser.retrieveData(latitude, longitude);
	}

}
