package com.foodrescue.repository;

import com.foodrescue.domain.Restaurant;
import com.foodrescue.domain.User;

public class MongoMain {

	public static void main(String[] args) {

		MongoRestaurant mongoRestaurant = new MongoRestaurant();
		Restaurant restaurant = new Restaurant();
		restaurant.setName("Jack in the box San Jose");
		restaurant.setPhone("4082440255");
		restaurant.setPassword("abcd");
		restaurant.setLocation("911 El Camino Real, Santa Clara, CA 95050");
		restaurant.setLatitude("37.354107");
		restaurant.setLongitude("-121.955238");
		// mongoRestaurant.removeData("40856788899", "pass123");
		// mongoRestaurant.updateData(restaurant);
		// mongoRestaurant.insertData(restaurant);

		System.out.println(mongoRestaurant.retrieveData("37.34", "-121.90").size());
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

}
