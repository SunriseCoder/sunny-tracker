package tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tracker.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
