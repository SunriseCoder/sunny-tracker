package tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tracker.entity.IssuePriority;

public interface IssuePriorityRepository extends JpaRepository<IssuePriority, Integer> {

}
