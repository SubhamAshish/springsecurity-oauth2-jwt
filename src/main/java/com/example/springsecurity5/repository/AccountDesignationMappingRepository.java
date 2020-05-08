package com.example.springsecurity5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springsecurity5.domain.AccountDesignationMapping;


@Repository(value="jpaAccountDesignationMappingRepository")
public interface AccountDesignationMappingRepository extends JpaRepository<AccountDesignationMapping, Integer>{

	List<AccountDesignationMapping> findByDesignationId(int id);

	List<AccountDesignationMapping> findByAccountIdIn(List<Integer> accIdList);

	List<AccountDesignationMapping> findByAccountId(Integer id);

	List<AccountDesignationMapping> findByDesignationIdIn(List<Integer> desgIdsInDb);

	List<AccountDesignationMapping> findByAccountIdAndEnableTrue(Integer id);

	List<AccountDesignationMapping> findByAccountIdInAndEnableTrue(List<Integer> accIdList);

}
