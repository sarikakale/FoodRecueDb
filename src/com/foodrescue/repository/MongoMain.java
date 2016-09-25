package com.foodrescue.repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.foodrescue.domain.Restaurant;
import com.foodrescue.domain.User;

public class MongoMain {

	public static void main(String[] args) {

		MongoRestaurant mongoRestaurant = new MongoRestaurant();
		Restaurant restaurant = new Restaurant();
		restaurant.setName("Peel's Coffee ");
		restaurant.setPhone("69088888856");
		restaurant.setPassword("abcd");
		restaurant.setLocation("MLK Library, San Jose, CA 95112");
		restaurant.setLatitude("37.453326");
		restaurant.setLongitude("-121.94589");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date)); // 2014/08/06 15:59:48
		restaurant.setDate(dateFormat.format(date));
		// MongoUser mongoUser=new MongoUser();
		// MongoRestaurant mongoRestaurant = new MongoRestaurant();
		/*
		 * MongoUser mongoUser=new MongoUser(); User user = new User();
		 * user.setName("Jack New"); user.setDeviceId("1234abcdefghixyzer");
		 * user.setPhone("4082440255"); user.setPassword("abcd");
		 * user.setLocation("911 El Camino Real, Santa Clara, CA 95050");
		 * user.setLatitude("37.354107"); user.setLongitude("-121.955238");
		 */

		// mongoUser.insertData(user);

		// mongoRestaurant.removeData("40856788899", "pass123");
		mongoRestaurant.updateData(restaurant);
		// mongoRestaurant.insertData(restaurant);
		// mongoUser.insertData(user);
		// System.out.println(mongoRestaurant.updateData(restaurant));
		System.out.println(mongoRestaurant.retrieveData("37.354107", "-121.96"));
	}

	public boolean login(User user) {
		MongoUser mongoUser = null;
		try {
			mongoUser = new MongoUser();
			User returnParameter = mongoUser.login(user.getPhone(), user.getPassword());
			System.out.println("returnParameter-----" + returnParameter);
			if (returnParameter == null) {
				return false;
			} else {
				return true;

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoUser.closeConnection();
		}
		return true;
	}

	public boolean login(Restaurant rest) {
		MongoRestaurant mongoRestaurant = null;
		try {
			mongoRestaurant = new MongoRestaurant();
			Restaurant returnParameter = mongoRestaurant.login(rest.getPhone(), rest.getPassword());
			
			System.out.println("returnParameter-----" + returnParameter);

			if (returnParameter == null) {
				return false;
			} else {
				return true;

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoRestaurant.closeConnection();
		}
		return true;
	}

	public boolean insertData(Restaurant restaurant) {
		System.out.println("hello");
		MongoRestaurant mongoRest = null;
		try {
			mongoRest = new MongoRestaurant();
			if (mongoRest.insertData(restaurant)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			mongoRest.closeConnection();
		}
		return false;
	}

	public void insertData(User user) {
		System.out.println("hello");
		MongoUser mongoUser = null;
		try {
			mongoUser = new MongoUser();
			mongoUser.insertData(user);
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			mongoUser.closeConnection();
		}
	}

	public void updateData(User user) {
		MongoUser mongoUser = null;
		try {
			mongoUser = new MongoUser();
			mongoUser.updateData(user);
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			mongoUser.closeConnection();
		}
	}

	public void updateRestaurantData(String phone, String date) {
		MongoRestaurant mongoRestaurant = null;
		try {
			mongoRestaurant = new MongoRestaurant();
			Restaurant rest = new Restaurant();
			rest.setDate(date);
			mongoRestaurant.updateData(rest);
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			mongoRestaurant.closeConnection();
		}

	}

	public Date convertDateToString(String dateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

		try {

			Date date = formatter.parse(dateStr);
			System.out.println(date);
			System.out.println(formatter.format(date));
			return date;

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Retrieve Restaurant List
	public List<Restaurant> getRestaurantData(String latitude, String longitude) {
		MongoRestaurant restaurant = null;
		List<Restaurant> rests = new ArrayList<>();
		try {
			restaurant = new MongoRestaurant();
			rests = restaurant.retrieveData(latitude, longitude);
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			restaurant.closeConnection();
		}

		return rests;
	}

	// Retrieve User List
	public List<String> getUserData(String latitude, String longitude) {
		MongoUser mongoUser = null;
		List<String> users = new ArrayList<>();
		try {
			mongoUser = new MongoUser();
			users = mongoUser.retrieveData(latitude, longitude);
		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			mongoUser.closeConnection();
		}
		return users;
	}

}
