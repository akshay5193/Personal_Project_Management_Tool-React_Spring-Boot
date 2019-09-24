package com.akshay.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.akshay.ppmtool.domains.ProjectTask;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

}
