package com.cts.proj.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService{
	@Autowired
	private AppUserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final AppUser appUser = userRepo.findUser(username);
		if(appUser == null) {
			throw new UsernameNotFoundException("User with "+username+" id not found");
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		UserDetails user = User.withUsername(appUser.getUserId())
								.password(encoder.encode(appUser.getPassword()))
								.authorities("USER")
								.build();
		
		return user;
	}
}
