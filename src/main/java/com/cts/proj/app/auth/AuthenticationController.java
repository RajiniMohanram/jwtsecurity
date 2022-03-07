package com.cts.proj.app.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.proj.app.user.AppUserService;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private AppUserService userDetailsService;
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> doLogin(@RequestParam("username") String userName, 
									 @RequestParam("password") String password) {
		
		defaultAuth(userName, password);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
		final String jwtToken = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(jwtToken);
	}
	
	public void defaultAuth(String userName, String pass) {
		
		try {
			UsernamePasswordAuthenticationToken token 
				= new UsernamePasswordAuthenticationToken(userName, pass);
			authenticationManager.authenticate(token);
		}catch(DisabledException ex) {
			throw ex;
		}catch(BadCredentialsException ex) {
			throw ex;
		}
	}
}
