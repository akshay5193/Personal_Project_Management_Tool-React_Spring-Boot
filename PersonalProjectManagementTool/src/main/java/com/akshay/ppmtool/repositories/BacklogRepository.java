package com.akshay.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.akshay.ppmtool.domains.Backlog;

@Repository
public interface BacklogRepository extends CrudRepository <Backlog, Long> {

	Backlog findByProjectIdentifier(String Identifier);
	
}
