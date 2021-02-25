package com.vapl.dialer.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vapl.dialer.dao.DialerDao;
import com.vapl.dialer.exception.UserNotFoundException;
import com.vapl.dialer.pojo.UserInfo;

@Service(value = "userService")
public class AppUserDetailsService implements UserDetailsService {
	@Autowired
	private DialerDao obdDao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("Inside AppUserDetailsService");
		UserInfo user = obdDao.getUserInfo(email);
		System.out.println(user);
		if (user == null) {
			throw new UserNotFoundException("Bad credentials");
		}
		System.out.println("AuthenticationService User Returned: " + user.getUserId());
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
		UserDetails userDetails = (UserDetails) new User(user.getUserId().toString() + ";" + user.getUsername(),
				user.getPassword(), Arrays.asList(authority));
		return userDetails;
	}
}
