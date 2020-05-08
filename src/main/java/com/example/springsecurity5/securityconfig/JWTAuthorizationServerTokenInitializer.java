package com.example.springsecurity5.securityconfig;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/*
 * 
 * @auhor subham
 * 
 * This class initialize the token,
 * read .jks file from class path and load it in EnhancedJWTAccessTokenConverter
 * 
 */

@Configuration
public class JWTAuthorizationServerTokenInitializer {

	@Autowired
	private ConfigurableEnvironment configurableEnvironment;

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {

		if (configurableEnvironment.getProperty("security.application-security-type").equals("jwt-oauthserver")) {

			EnhancedJWTAccessTokenConverter converter = new EnhancedJWTAccessTokenConverter();
			KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("encrypt.jks"),
					configurableEnvironment.getProperty("jwt.jks.password").toCharArray());
			converter.setKeyPair(keyStoreKeyFactory.getKeyPair(configurableEnvironment.getProperty("jwt.jks.alias")));
			return converter;

		} else if (configurableEnvironment.getProperty("security.application-security-type")
				.equals("jwt-resserver")) {
			EnhancedJWTAccessTokenConverter converter = new EnhancedJWTAccessTokenConverter();
			String key = null;
			try {
				ClassPathResource classPathResource = new ClassPathResource("publickey.txt");
				InputStream inputStream = classPathResource.getInputStream();
				String string = IOUtils.toString(inputStream);
				key = new String(string);
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
			converter.setVerifierKey(key);
			return converter;
		} else if (configurableEnvironment.getProperty("security.application-security-type").equals("jwt-both")) {

			EnhancedJWTAccessTokenConverter converter = new EnhancedJWTAccessTokenConverter();
			KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("encrypt.jks"),
					configurableEnvironment.getProperty("jwt.jks.password").toCharArray());
			converter.setKeyPair(keyStoreKeyFactory.getKeyPair(configurableEnvironment.getProperty("jwt.jks.alias")));

			String key = null;
			try {

				ClassPathResource classPathResource = new ClassPathResource("publickey.txt");
				InputStream inputStream = classPathResource.getInputStream();
				String string = IOUtils.toString(inputStream);
				key = new String(string);

			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
			converter.setVerifierKey(key);
			return converter;
		}

		throw new RuntimeException("AccessTokenConverter couldnot be created.");

	}

}
