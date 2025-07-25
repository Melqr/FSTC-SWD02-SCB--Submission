package com.bank.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchDTO {


    private Long branchID;
    private String branchName;
    private String branchPostCode;
    private LocalDateTime creationDate;


}
