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
		restaurant.setLatitude("37.354988");
		restaurant.setLongitude("-121.946549");
		mongoRestaurant.retrieveData("37.3333268", "-121.884589");
	
	}

}
