
package com.example.springsecurity5;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.springsecurity5.util.UgmtClientCredentials;

/**
 * @author subham
 *
 */
@Component
public class PasswordClientSecretLoader {

	@Bean
	public UgmtClientCredentials ugmtClientCredentials() {
		return new UgmtClientCredentials("web", "pass");
	}

}
