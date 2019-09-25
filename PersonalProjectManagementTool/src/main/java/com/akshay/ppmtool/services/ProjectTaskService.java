package com.akshay.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akshay.ppmtool.domains.Backlog;
import com.akshay.ppmtool.domains.ProjectTask;
import com.akshay.ppmtool.repositories.BacklogRepository;
import com.akshay.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
		projectTask.setBacklog(backlog);
		Integer BacklogSequence = backlog.getPTSequence();
		
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
	
	
	public Iterable<ProjectTask>findBacklogById(String id){
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}

}
