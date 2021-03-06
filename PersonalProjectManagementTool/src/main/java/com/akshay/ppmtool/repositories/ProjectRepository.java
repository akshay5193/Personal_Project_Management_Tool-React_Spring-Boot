package com.akshay.ppmtool.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.akshay.ppmtool.domains.Project;

@Repository
public interface ProjectRepository extends CrudRepository <Project, Long> {
	
	@Override
	Iterable<Project> findAll();
	
	Project findByProjectIdentifier(String projectId);

}
