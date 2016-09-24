package com.foodrescue.repository;

import com.foodrescue.domain.Restaurant;

public class MongoMain {

	public static void main(String[] args) {
				
		MongoRestaurant mongoRestaurant=new MongoRestaurant();
		Restaurant restaurant=new Restaurant();
		
		mongoRestaurant.insertData(restaurant);
	
	}

}
