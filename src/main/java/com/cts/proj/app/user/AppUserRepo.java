package com.cts.proj.app.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class AppUserRepo {
	private Map<String,AppUser> users;
	
	public AppUserRepo() {
		AppUser user1 = new AppUser("erik", "magneto", "Erik", "erik@xmen.com", "Soviet");
		AppUser user2 = new AppUser("charles","professor","Charles Xavier","charles@xmen.com","British");
		AppUser user3 = new AppUser("logan", "wolverine", "Logan", "logan@xmen.com", "Canada");
		
		users = new HashMap<String, AppUser>();
		users.put(user1.getUserId(), user1);
		users.put(user2.getUserId(), user2);
		users.put(user3.getUserId(), user3);
		
		
	}
	
	public AppUser findUser(String userId) {
		return users.get(userId);
	}
}
