package com.vapl.dialer.websecurity;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		final String resp = (String) request.getAttribute("resp");
		response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, resp);
		// response.addIntHeader(resp, HttpServletResponse.SC_UNAUTHORIZED);

		/*
		 * ApiResponse apires = new ApiResponse("Unauthorized");
		 * apires.setMessage(resp); OutputStream out = response.getOutputStream();
		 * ObjectMapper mapper = new ObjectMapper(); mapper.writeValue(out, apires);
		 * out.flush();
		 */
	}
}