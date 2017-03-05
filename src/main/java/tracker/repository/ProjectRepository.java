package tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tracker.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
