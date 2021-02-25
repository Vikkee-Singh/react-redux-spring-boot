package com.vapl.dialer.controller;

import com.vapl.dialer.pojo.AuthToken;
import com.vapl.dialer.pojo.TokenResponse;
import com.vapl.dialer.pojo.UserInfo;
import com.vapl.dialer.service.AppUserDetailsService;
import com.vapl.dialer.websecurity.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AppUserDetailsService userDetailsService;

    @RequestMapping(value = "login", produces = "application/json", method = RequestMethod.POST)
    public TokenResponse<AuthToken> authenticate(@RequestBody UserInfo loginUser) throws AuthenticationException {

        logger.info("inside login controller");
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginUser.getUsername());
        Set<String> role = AuthorityUtils
                .authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        final String token = jwtTokenUtil.generateToken(userDetails);
        String userArr[] = userDetails.getUsername().split(";");
        return new TokenResponse<>("Success", token, userArr[0], role);
        // return new ResponseEntity<String>(token, HttpStatus.OK);

    }

}
