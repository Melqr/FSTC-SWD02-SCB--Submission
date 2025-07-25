package com.bank.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.entity.BranchEntity;
import com.bank.repo.IBranchRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BranchServiceImpl implements IBranchService {
	
	
    private final IBranchRepo branchRepo;

    @Override
    public BranchEntity createBranch(BranchEntity branch) {
        return branchRepo.save(branch);
    }

    @Override
    public BranchEntity getBranchById(Long id) {
        return branchRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found"));
    }

    @Override
    public List<BranchEntity> getAllBranches() {
        return branchRepo.findAll();
    }

    @Override
    public BranchEntity updateBranch(Long id, BranchEntity updatedBranch) {
        return branchRepo.findById(id).map(existing -> {
            existing.setBranchName(updatedBranch.getBranchName());
            existing.setBranchPostCode(updatedBranch.getBranchPostCode());
            return branchRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Branch not found"));
    }

    @Override
    public void deleteBranch(Long id) {
        branchRepo.deleteById(id);
    }
    
    //search by name
    @Override
    public List<BranchEntity> searchBranchByName(String branchName) {
        return branchRepo.findByBranchNameContainingIgnoreCase(branchName);
    }

    
    //search by date
    @Override
    public List<BranchEntity> searchBranchByCreationDateRange(LocalDateTime fromDate, LocalDateTime toDate) {
        return branchRepo.findByCreationDateBetween(fromDate, toDate);
    }

}
