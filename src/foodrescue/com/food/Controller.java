package foodrescue.com.food;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.foodrescue.domain.Restaurant;
import com.foodrescue.domain.User;
import com.foodrescue.repository.MongoMain;

@Path("/res")
public class Controller {
	@GET
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createRestaurantInJSON(Restaurant e) throws Exception {
		System.out.println("Helloooo");
		return Response.ok().entity("").build();
	}

	@POST
	@Path("/notify")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response notifyInJSON(Restaurant e) throws Exception {
		return Response.ok().entity("").build();
	}

	// User
	@POST
	@Path("/userCreate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUserInJSON(User u) throws Exception {
		System.out.println("Helloooo user");
		MongoMain m = new MongoMain();

		m.insertData(u);

		return Response.ok().entity("").build();
	}

	@POST
	@Path("/userLogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginInJSON(User u) throws Exception {
		MongoMain m = new MongoMain();
		boolean access = m.login(u);
		if (access)
			return Response.ok().entity("").build();
		else
			return Response.serverError().entity("").build();
	}

	@GET
	@Path("/restaurantList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRestaurants(String latitude, String longitude) throws Exception {
		MongoMain mongoMain = new MongoMain();
		List<Restaurant> restaurants = mongoMain.getRestaurantData(latitude, longitude);

		if (restaurants.size() == 0) {
			return Response.ok().entity(restaurants).build();
		} else {
			return Response.serverError().entity("").build();
		}

	}

	@POST
	@Path("/pushNotifications")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendPushNotification(String latitude, String longitude) throws Exception {
		MongoMain mongoMain = new MongoMain();
		List<String> users = mongoMain.getUserData(latitude, longitude);

		if (users.size() == 0) {
			return Response.ok().entity(users).build();
		} else {
			return Response.serverError().entity("").build();
		}

	}

}
