package com.example.springsecurity5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springsecurity5.domain.DesignationAuthorityMapping;


@Repository(value="jpaDesignationAuthorityMappingRepository")
public interface DesignationAuthorityMappingRepository extends JpaRepository<DesignationAuthorityMapping, Integer> {

	List<DesignationAuthorityMapping> findByIdIn(List<Integer> permissions);

}
