package tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tracker.model.IssuePriority;

public interface IssuePriorityRepository extends JpaRepository<IssuePriority, Integer> {

}
