package tracker.service;

import java.util.List;

import tracker.exception.ProjectNotFound;
import tracker.model.Project;

public interface ProjectService {
	public List<Project> findAll();
	public Project create(Project project);
	public Project findById(int id) throws ProjectNotFound;
	public Project update(Project project) throws ProjectNotFound;
	public Project delete(int id) throws ProjectNotFound;
}
