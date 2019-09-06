package com.akshay.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akshay.ppmtool.domains.Project;
import com.akshay.ppmtool.exceptions.ProjectIdException;
import com.akshay.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	
	public Project saveOrUpdateProject (Project project) {
		
		// Logic, especially for update operations
		
		
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
		}
		catch (Exception e){
			throw new ProjectIdException ("Project ID: '" + project.getProjectIdentifier().toUpperCase() + "' already exist.");
		}
	}
	
}
