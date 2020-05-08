package com.example.springsecurity5.securityconfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.springsecurity5.domain.Account;
import com.example.springsecurity5.domain.AccountDesignationMapping;
import com.example.springsecurity5.domain.DesignationAuthorityMapping;
import com.example.springsecurity5.model.UserModel;
import com.example.springsecurity5.repository.AccountRepository;

/**
 * @author subham
 *
 */
@Component
public class CustomUserDetails implements UserDetailsService {

	@Autowired(required = false)
	private AccountRepository accountRepository;

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

		Account account = accountRepository.findByUserName(name);

		if (account == null) {
			throw new UsernameNotFoundException("Invalid username or password !");
		}

		Set<GrantedAuthority> grantedAuthority = new HashSet<>();

		/**
		 * As one user can have multiple roles
		 */
		Set<Integer> designationIds = new HashSet<>();

		Set<String> designations = new HashSet<>();

		/**
		 * Adding authority like @PreAuthorize("hasAuthority('authorityvalue')")
		 * 
		 * if authority control type is mentioned designation than get all the authority
		 * mapped with the designations
		 */

		grantedAuthority = setGrantedAuthorityValueFromDesignation(account.getAccountDesignationMapping(),
				designationIds, designations, grantedAuthority);

		return new UserModel(account.getUserName(), account.getPassword(), account.isEnabled(), !account.isExpired(),
				!account.isCredentialexpired(), !account.isLocked(), grantedAuthority, account.getId(), designationIds,
				designations, account.getEmail(), null);
	}

	/**
	 * 
	 * @param accountDesignationMapping
	 * @param designationIds
	 * @param designations
	 * @param grantedAuthority
	 * @return
	 */
	private Set<GrantedAuthority> setGrantedAuthorityValueFromDesignation(
			List<AccountDesignationMapping> accountDesignationMapping, Set<Integer> designationIds,
			Set<String> designations, Set<GrantedAuthority> grantedAuthority) {

		accountDesignationMapping.forEach(ed -> {

			if (ed.getEnable()) {
				List<DesignationAuthorityMapping> designationAuthorityMapping = ed.getDesignation()
						.getDesignationAuthorityMapping();

				designationAuthorityMapping.stream().filter(v -> v.getEnabled().equals(true)).forEach(da -> {

					// adding role-ids
					designationIds.add(da.getDesignation().getId());

					// adding rolename
					designations.add(da.getDesignation().getName());
					grantedAuthority.add(new SimpleGrantedAuthority(da.getAuthority().getAuthority()));
				});
			}

		});

		return grantedAuthority;
	}

}
