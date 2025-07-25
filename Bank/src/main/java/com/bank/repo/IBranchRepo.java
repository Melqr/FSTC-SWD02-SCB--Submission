package com.bank.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import com.bank.entity.BranchEntity;

@Repository
public interface IBranchRepo extends JpaRepository<BranchEntity, Long> {

	List<BranchEntity> findByBranchNameContainingIgnoreCase(String branchName);
	List<BranchEntity> findByCreationDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

}
