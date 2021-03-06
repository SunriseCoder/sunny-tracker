package tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import tracker.dao.ProjectRepository;
import tracker.entity.Project;
import tracker.exception.ProjectNotFound;

@Component
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository repository;
    private Sort sort;

    public ProjectServiceImpl() {
        sort = new Sort(new Order(Direction.ASC, "position"));
    }

    @Override
    @Transactional
    public List<Project> findAll() {
        return repository.findAll(sort);
    }

    @Override
    @Transactional
    public Project create(Project project) {
        return repository.save(project);
    }

    @Override
    @Transactional
    public Project findById(int id) throws ProjectNotFound {
        Project project = repository.findOne(id);
        if (project == null) {
            throw new ProjectNotFound();
        }
        return project;
    }

    @Override
    @Transactional(rollbackFor = ProjectNotFound.class)
    public Project update(Project project) throws ProjectNotFound {
        if (project == null || project.getId() == null) {
            throw new ProjectNotFound();
        }

        Project updatedProject = repository.findOne(project.getId());
        if (updatedProject == null) {
            throw new ProjectNotFound();
        }

        updatedProject.setName(project.getName());
        updatedProject.setPosition(project.getPosition());
        return updatedProject;
    }

    @Override
    @Transactional
    public Project delete(int id) throws ProjectNotFound {
        Project deletedProject = repository.findOne(id);
        if (deletedProject == null) {
            throw new ProjectNotFound();
        }
        repository.delete(id);
        return deletedProject;
    }
}
