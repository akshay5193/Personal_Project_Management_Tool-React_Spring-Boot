package com.akshay.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akshay.ppmtool.domains.Backlog;
import com.akshay.ppmtool.domains.Project;
import com.akshay.ppmtool.domains.ProjectTask;
import com.akshay.ppmtool.exceptions.ProjectNotFoundException;
import com.akshay.ppmtool.repositories.BacklogRepository;
import com.akshay.ppmtool.repositories.ProjectRepository;
import com.akshay.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		
		try {
			// Project Not Found Exception
			
			
			// PTs to be added to a specific project, project != null, BL exists
			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
			
			// Set the BACKLOG to PROJECT TASK
			projectTask.setBacklog(backlog);
			
			// We want our project sequence to be like this: IDPRO-1  IDPRO-2  ...100  101 etc
			Integer BacklogSequence = backlog.getPTSequence();
			
			// Update the BL Sequence
			BacklogSequence++;
			backlog.setPTSequence(BacklogSequence);
			
			projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);
			
			if (projectTask.getPriority() == null) {
				projectTask.setPriority(3);
			}
			
			if(projectTask.getStatus()=="" || projectTask.getStatus()==null) {
				projectTask.setStatus("TO_DO");
			}
			
			return projectTaskRepository.save(projectTask);
		}
		
		catch(Exception e) {
			throw new ProjectNotFoundException("Project not found...");
		}
	}
	
	
	
	
	public Iterable<ProjectTask>findBacklogById(String id){
		
		Project project = projectRepository.findByProjectIdentifier(id);
		
		if (project == null) {
			throw new ProjectNotFoundException("project with ID: " + id + " does not exist.");
		}
		
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}

}