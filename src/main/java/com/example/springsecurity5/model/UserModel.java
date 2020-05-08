package com.example.springsecurity5.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;



/**
 * @author Subham
 *
 */
public class UserModel extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8439511551572574482L;

	private int  userId;

	private Set<Integer> designationIds;

	private Set<String> designations;

	private String email;

	private Set<Integer> desgSlugId;
	
	private List<String> authorities = new ArrayList<>();

	/**
	 * *
	 * 
	 * @Description private constructors to use existing properties
	 */
	private UserModel(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

		List<String> authorityNames = new ArrayList<>();
		for(GrantedAuthority authority : authorities) {
			authorityNames.add(authority.getAuthority());
		}
		this.authorities = authorityNames;
		
	}

	/***
	 * @Desription extra parameter has been passed here to be initialized and
	 *             return
	 */
	public UserModel(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
			int userId, Set<Integer> roleIds, Set<String> roles, String email,
			Set<Integer> desgSlugId) {

		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

		List<String> authorityNames = new ArrayList<>();
		for(GrantedAuthority authority : authorities) {
			authorityNames.add(authority.getAuthority());
		}
		this.authorities = authorityNames;
		
		this.userId = userId;
		this.designationIds = roleIds;
		this.designations = roles;
		this.email = email;

		if (desgSlugId != null)
			this.desgSlugId = desgSlugId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Set<Integer> getDesignationIds() {
		return designationIds;
	}

	public void setDesignationIds(Set<Integer> designationIds) {
		this.designationIds = designationIds;
	}

	public Set<String> getDesignations() {
		return designations;
	}

	public void setDesignations(Set<String> designations) {
		this.designations = designations;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Integer> getDesgSlugId() {
		return desgSlugId;
	}

	public void setDesgSlugId(Set<Integer> desgSlugId) {
		this.desgSlugId = desgSlugId;
	}

}
