package com.vapl.dialer.websecurity;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.vapl.dialer.util.Constants.HEADER_STRING;
import static com.vapl.dialer.util.Constants.TOKEN_PREFIX;

import java.io.IOException;
//import java.util.Arrays;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        String username = null;
        String authToken = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX,"");
            logger.info(authToken);
            try
            {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
                logger.info("getting username from token "+username);
            } 
            catch (IllegalArgumentException e) 
            {
                logger.error("an error occured during getting username from token", e);
                req.setAttribute("resp","an error occured during getting username from token");
            } 
            catch (ExpiredJwtException e) 
            {
                logger.warn("the token is expired and not valid anymore", e);
                req.setAttribute("resp","Token Expired");
            }
            catch(SignatureException e)
            {
                logger.error("Token Not Valid");
                req.setAttribute("resp","Token Not Valid.");
            }
            catch(MalformedJwtException e)
            {
                logger.error("Token Not Valid");
                req.setAttribute("resp","Token Not Valid.");
            }
        } 
        else 
        {
            logger.warn("couldn't find bearer string, will ignore the header");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
        	logger.info("username not null");
        	String[] userArr = username.split(";");
        	logger.info("Username "+userArr[1]);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userArr[1]);

            if (jwtTokenUtil.validateToken(authToken, userDetails))
            {
                //UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            	UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            	//UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
    }
}