package com.bank.service;

import java.time.LocalDateTime;
import java.util.List;

import com.bank.entity.BranchEntity;

public interface IBranchService{
	
    BranchEntity createBranch(BranchEntity branch);
    BranchEntity getBranchById(Long id);
    List<BranchEntity> getAllBranches();
    BranchEntity updateBranch(Long id, BranchEntity updated);
    void deleteBranch(Long id);
    List<BranchEntity> searchBranchByName(String branchName);
    List<BranchEntity> searchBranchByCreationDateRange(LocalDateTime fromDate, LocalDateTime toDate);

}
