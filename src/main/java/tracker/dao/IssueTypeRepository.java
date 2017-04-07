package tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tracker.entity.IssueType;

public interface IssueTypeRepository extends JpaRepository<IssueType, Integer> {

}
