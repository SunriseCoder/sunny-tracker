package tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tracker.entity.Project;
import tracker.exception.ProjectNotFound;

@Service
public interface ProjectService {
    public List<Project> findAll();

    public Project create(Project project);

    public Project findById(int id) throws ProjectNotFound;

    public Project update(Project project) throws ProjectNotFound;

    public Project delete(int id) throws ProjectNotFound;
}
