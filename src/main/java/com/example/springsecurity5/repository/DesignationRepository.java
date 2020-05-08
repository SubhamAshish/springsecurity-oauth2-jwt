package com.example.springsecurity5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springsecurity5.domain.Designation;


@Repository(value = "jpaDesignationRepository")
public interface DesignationRepository extends JpaRepository<Designation, Integer> {

	Designation findByCode(String string);

	List<Designation> findByIdIn(List<Integer> designationIds);

	List<Designation> findAllByOrderByIdAsc();

	Designation findByName(String string);

}
