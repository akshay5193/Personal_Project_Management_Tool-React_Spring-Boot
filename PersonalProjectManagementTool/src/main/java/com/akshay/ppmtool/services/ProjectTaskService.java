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
	
	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
		
		// make sure we are searching on the right backlog which exists
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		
		if (backlog == null) {
			throw new ProjectNotFoundException("project with ID: " + backlog_id + " does not exist.");
		}
		
		// make sure that our task exist
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		
		if (projectTask == null) {
			throw new ProjectNotFoundException("Project Task '" + pt_id + "' not found");
		}
		
		// make sure that the backlog/project id in the path corresponds to the right project
		if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Project Task '" + pt_id + "' does not exist in the project: '" + backlog_id + "'");
		}
		
		return projectTask;
	}

}
