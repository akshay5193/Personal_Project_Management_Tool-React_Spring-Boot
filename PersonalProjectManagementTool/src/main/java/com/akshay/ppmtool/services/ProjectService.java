package com.akshay.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akshay.ppmtool.domains.Backlog;
import com.akshay.ppmtool.domains.Project;
import com.akshay.ppmtool.exceptions.ProjectIdException;
import com.akshay.ppmtool.repositories.BacklogRepository;
import com.akshay.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	
	public Project saveOrUpdateProject (Project project) {
		
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			if (project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}
			
			if (project.getId() != null) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			
			return projectRepository.save(project);
		}
		catch (Exception e){
			throw new ProjectIdException ("Project ID: '" + project.getProjectIdentifier().toUpperCase() + "' already exist.");
		}
	}
	
	
	public Project findProjectByIdentifier (String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId);
		
		if (project == null) {
			throw new ProjectIdException ("Project ID: '" + projectId + "' does not exist.");
		}
		return project;
	}
	
	
	public Iterable<Project> findAllProjects() {
		// Whenever you have something that returns a list, 
		// the basic way to extract the list is usually to traverse through it
		// But in this case with iterable, it actually out of the box returns 
		// the JSON objects that has JSON elements or objects within that list
		
		return projectRepository.findAll();
	}
	
	
	public void deleteProjectByIdentifier(String identifier) {
		Project project = projectRepository.findByProjectIdentifier(identifier);
		
		if (project == null) {
			throw new ProjectIdException("Cannot delete project with ID: '" + identifier + "'. This project does not exist.");
		}
		
		projectRepository.delete(project);
	}
}
