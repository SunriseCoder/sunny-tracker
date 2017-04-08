package tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tracker.entity.IssuePriority;

@Repository
public interface IssuePriorityRepository extends JpaRepository<IssuePriority, Integer> {

}
