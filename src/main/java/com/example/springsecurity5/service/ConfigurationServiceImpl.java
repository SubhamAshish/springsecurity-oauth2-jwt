package com.example.springsecurity5.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springsecurity5.domain.Account;
import com.example.springsecurity5.domain.AccountDesignationMapping;
import com.example.springsecurity5.domain.Authority;
import com.example.springsecurity5.domain.Designation;
import com.example.springsecurity5.domain.DesignationAuthorityMapping;
import com.example.springsecurity5.repository.AccountDesignationMappingRepository;
import com.example.springsecurity5.repository.AccountRepository;
import com.example.springsecurity5.repository.AuthorityRepository;
import com.example.springsecurity5.repository.DesignationAuthorityMappingRepository;
import com.example.springsecurity5.repository.DesignationRepository;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

	@Autowired
	private DesignationRepository designationRepository;

	@Autowired
	private AuthorityRepository authorityRepository;

	@Autowired
	private DesignationAuthorityMappingRepository designationAuthorityMappingRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountDesignationMappingRepository accountDesignationMappingRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public boolean config() {
		try {

			/**
			 * Create designation in Designation table
			 */
			List<Designation> designationList = new ArrayList<>();

			Designation desg = new Designation();

			desg = new Designation();
			desg.setCode("Admin");
			desg.setName("ADMIN");
			designationList.add(desg);

			desg = new Designation();
			desg.setCode("User");
			desg.setName("USER");
			designationList.add(desg);

			designationRepository.saveAll(designationList);

			/**
			 * Create Authority in Authority table
			 */
			List<Authority> authorityList = new ArrayList<>();

			Authority authority = new Authority();
			authority.setAuthority("ADMIN");
			authority.setDescription("Allow user to access all modules");
			authorityList.add(authority);

			authority = new Authority();
			authority.setAuthority("USER");
			authority.setDescription("Allow user to access only certain permission");
			authorityList.add(authority);

			authorityRepository.saveAll(authorityList);

			/**
			 * Designation-Authority Mapping
			 */
			List<DesignationAuthorityMapping> damList = new ArrayList<>();

			DesignationAuthorityMapping dam = new DesignationAuthorityMapping();

			/**
			 * admin user to access report,dashboard and user_management module
			 */
			dam.setAuthority(authorityRepository.findByAuthority("ADMIN"));
			dam.setDesignation(designationRepository.findByCode("Admin"));
			damList.add(dam);

			dam = new DesignationAuthorityMapping();
			dam.setAuthority(authorityRepository.findByAuthority("USER"));
			dam.setDesignation(designationRepository.findByCode("Admin"));
			damList.add(dam);

			dam = new DesignationAuthorityMapping();
			dam.setAuthority(authorityRepository.findByAuthority("USER"));
			dam.setDesignation(designationRepository.findByCode("User"));
			damList.add(dam);

			designationAuthorityMappingRepository.saveAll(damList);

			Account acc = new Account();
			acc.setUserName("admin");
			System.out.println(bCryptPasswordEncoder.encode("test@123#"));
			acc.setPassword(bCryptPasswordEncoder.encode("test@123#"));
			acc.setEmail("subhamashsih1@gmail.com");
			Account accSave = accountRepository.save(acc);

			AccountDesignationMapping adm = new AccountDesignationMapping();
			adm.setAccount(accSave);
			adm.setDesignation(designationRepository.findByCode("Admin"));
			adm.setEnable(true);
			accountDesignationMappingRepository.save(adm);
			return true;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
