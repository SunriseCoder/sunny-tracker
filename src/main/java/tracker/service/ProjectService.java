package tracker.service;

import java.util.List;

import tracker.entity.Project;
import tracker.exception.ProjectNotFound;

public interface ProjectService {
	public List<Project> findAll();
	public Project create(Project project);
	public Project findById(int id) throws ProjectNotFound;
	public Project update(Project project) throws ProjectNotFound;
	public Project delete(int id) throws ProjectNotFound;
}
