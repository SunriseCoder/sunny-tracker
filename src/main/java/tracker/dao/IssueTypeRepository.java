package tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tracker.entity.IssueType;

@Repository
public interface IssueTypeRepository extends JpaRepository<IssueType, Integer> {

}
