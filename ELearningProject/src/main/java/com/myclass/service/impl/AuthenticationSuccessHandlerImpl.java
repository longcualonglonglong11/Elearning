package com.myclass.service.impl;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.myclass.entity.Role;
@Service
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		if (roles.contains("ROLE_ADMIN")) {
			response.sendRedirect("/admin/role");
		} else if (roles.contains("ROLE_USER")) {
			response.sendRedirect("/home");
		} else if (roles.contains("ROLE_LECTURER")) {
			response.sendRedirect("/lecturer");

		} else {
			response.sendRedirect("/sublecturer");
		}
	}

}
