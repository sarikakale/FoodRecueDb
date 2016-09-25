package com.foodrescue.repository;

import com.foodrescue.domain.Restaurant;

public class MongoMain {

	public static void main(String[] args) {
				
		MongoRestaurant mongoRestaurant=new MongoRestaurant();
		Restaurant restaurant=new Restaurant();
		restaurant.setName("Jack in the box");
		restaurant.setPhone("4082440255");
		restaurant.setPassword("abcd");
		restaurant.setLocation("911 El Camino Real, Santa Clara, CA 95050");
		restaurant.setLatitude("37.354107");
		restaurant.setLongitude("-121.955238");
		//mongoRestaurant.removeData("40856788899", "pass123");
		mongoRestaurant.retrieveData("37.34", "-121.90");
		//mongoRestaurant.insertData(restaurant);
	}

}
