package com.cts.proj.app.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cts.proj.app.user.AppUserService;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private AppUserService userRepo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
				HttpServletResponse response, FilterChain filterChain) 
						throws ServletException, IOException {
		final String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if (jwtToken==null || jwtToken.isEmpty()) {
			filterChain.doFilter(request, response);
			return;
		}
				
		UserDetails userDetails = userRepo
		        .loadUserByUsername(jwtTokenUtil.getUsernameFromToken(jwtToken));

		if (!jwtTokenUtil.validateToken(jwtToken, userDetails)) {
			filterChain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken authentication 
			= new UsernamePasswordAuthenticationToken(
		        userDetails, null, userDetails.getAuthorities());

		authentication.setDetails(new WebAuthenticationDetailsSource()
										.buildDetails(request));

		SecurityContextHolder.getContext()
							 .setAuthentication(authentication);
		
		filterChain.doFilter(request, response);
	}

}
