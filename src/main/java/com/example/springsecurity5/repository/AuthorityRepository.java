package com.example.springsecurity5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springsecurity5.domain.Authority;


@Repository(value="jpaAuthorityRepository")
public interface AuthorityRepository extends JpaRepository<Authority, Integer>{

	Authority findByAuthority(String string);

	List<Authority> findByIdIn(List<String> authorities);

}
