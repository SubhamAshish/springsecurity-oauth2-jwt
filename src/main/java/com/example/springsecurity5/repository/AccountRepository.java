package com.example.springsecurity5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springsecurity5.domain.Account;

@Repository(value = "jpaAccountRepository")
public interface AccountRepository extends JpaRepository<Account, Integer> {

	Account findByUserName(String name);

	Account findByEmail(String string);

}
