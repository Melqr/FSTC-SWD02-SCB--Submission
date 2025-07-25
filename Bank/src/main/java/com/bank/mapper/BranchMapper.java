package com.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.bank.entity.BranchEntity;
import com.bank.model.BranchDTO;

@Mapper
public interface BranchMapper {
	

    BranchDTO toDto(BranchEntity entity);

    BranchEntity toEntity(BranchDTO dto);

    List<BranchDTO> toDtoList(List<BranchEntity> entityList);

    List<BranchEntity> toEntityList(List<BranchDTO> dtoList);
}
