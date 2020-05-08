package com.example.springsecurity5.securityconfig;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.example.springsecurity5.util.UgmtClientCredentials;


/**
 * @author Subham Ashish
 * 
 *         it gets excuted before calling the method postAccessToken() of
 *         TokenEndPoint class For authenticate user request
 */
@Component
public class RequestLoggingInterceptor extends GenericFilterBean {

	/*
	 * You cannot use dependency injection from a filter out of the box. Although
	 * you are using GenericFilterBean your Servlet Filter is not managed by spring
	 * 
	 * @Autowired is not possible
	 */
	private UgmtClientCredentials ugmtClientCredentials;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (ugmtClientCredentials == null) {
			// we cannot expect spring to inject the service, but we can lazy
			// set it on the first call
			ServletContext servletContext = request.getServletContext();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils
					.getWebApplicationContext(servletContext);
			ugmtClientCredentials = webApplicationContext.getBean(UgmtClientCredentials.class);
		}

		String clientId = ugmtClientCredentials.getClientId();

		String clientSecrte = ugmtClientCredentials.getClientPassword();

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		/**
		 * verifying the grant type, shoud be password type
		 */
		if (httpRequest.getParameter("grant_type") != null && (httpRequest.getParameter("grant_type").equals("password")
				|| httpRequest.getParameter("grant_type").equals("refresh_token"))) {

			MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(httpRequest);

			String credentials = clientId + ":" + clientSecrte;
			String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

			mutableRequest.putHeader("Authorization", "Basic " + encodedCredentials);
			chain.doFilter(mutableRequest, response);

		} else {
			chain.doFilter(request, response);
		}

	}

}
