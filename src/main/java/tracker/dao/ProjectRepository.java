package tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tracker.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
