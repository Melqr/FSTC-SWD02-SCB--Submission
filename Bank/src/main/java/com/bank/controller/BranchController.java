package com.bank.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.entity.BranchEntity;
import com.bank.mapper.BranchMapper;
import com.bank.model.BranchDTO;
import com.bank.service.IBranchService;

import com.bank.exceptions.DemoAppException;
import lombok.AllArgsConstructor;

//Practical 8 - End to End Spring Boot 
//Create a branch entity with the data below
//a. BranchEntity.java

//branchID - Index ID auto generated
//branchName - length 100 - Not Nullable
//branchPostCode - length 30 - Not Nullable
//creationDate - Auto created when insert record - LocalDateTime 

//b. BranchController with get by ID, get all, add, and delete by ID only

//c. BranchDTO, BranchMapper and BranchMapperTest.java 
//Ensure table and column created on DB

//d. BranchService(done) and BranchServiceImpl(done)

//e. BranchRepo(done)

//Additional Requirement
//f1. Exception Handling - When adding record, if the branchName is contain empty space, throw a DemoAppException with meaningful error message. i.e. Branch Name cannot be empty
//Enable package scanning "com.demo.exceptions"
//Ensure swagger output contains the DemoAppException type and error exist in the app.log file

//f2. BranchRepo - Basic Search Function
//Add a DOGET into controller above able to search by branchName
//Add on the method to the controller, service and repo
//Note: Refer to CustomerController.java getCustomersByDescriptionAndCreationDateBetween() as a sample

//f3. BranchRepo - Search Function by date in between
//Add a DOGET into controller above able to between date from and date to
//Add on the method to the controller, service and repo


//g1 - UnitTesting - Create a BranchSearchTest.java for g2 and g3 above.

@AllArgsConstructor
@RestController
@RequestMapping("/api/branch")
public class BranchController {
	

    private final IBranchService branchService;

    private final BranchMapper branchMapper;
    
    
    //Get all Branches
    @GetMapping
    public ResponseEntity<List<BranchDTO>> getAllBranches() {
    	
        return ResponseEntity.ok(
            branchMapper.toDtoList(branchService.getAllBranches())
        );
    }
    
    //search branch name
    @GetMapping("/searchBranchName")
    public List<BranchDTO> getBranchNameBySearch(@RequestParam String branchName) {
        List<BranchEntity> entities = branchService.searchBranchByName(branchName);
        return branchMapper.toDtoList(entities);
    }


    
    
    //branches by id
    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable Long id) {
        return ResponseEntity.ok(
            branchMapper.toDto(branchService.getBranchById(id))
        );
    }
    
    //Create product
    @PostMapping
    public ResponseEntity<BranchDTO> createBranch(@RequestBody BranchDTO branchDto) {
    	if (branchDto.getBranchName() == null || branchDto.getBranchName().trim().isEmpty()) {
            throw new DemoAppException("Branch Name cannot be empty");
        }
        return ResponseEntity.ok(
            branchMapper.toDto(
                branchService.createBranch(branchMapper.toEntity(branchDto))
            )
        );
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BranchDTO> updateBranch(@PathVariable Long id, @RequestBody BranchDTO branchDto) {
        return ResponseEntity.ok(
            branchMapper.toDto(
                branchService.updateBranch(id, branchMapper.toEntity(branchDto))
            )
        );
    }
    
    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/searchByDate")
    public List<BranchDTO> getBranchByDateRange(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        List<BranchEntity> entities = branchService.searchBranchByCreationDateRange(from, to);
        return branchMapper.toDtoList(entities);
    }
    
    

}