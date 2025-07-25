package com.bank.mapper;

import com.bank.entity.BranchEntity;
import com.bank.model.BranchDTO;
import com.bank.repo.IBranchRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BranchMapperTest {

    @Autowired
    private BranchMapper branchMapper;

    @Autowired
    private IBranchRepo branchRepo;

    @Test
    void testToDto() {
        BranchEntity entity = new BranchEntity(
                1L,
                "KL Branch",
                "50450",
                LocalDateTime.of(2024, 1, 1, 10, 0)
        );

        BranchDTO dto = branchMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getBranchID(), dto.getBranchID());
        assertEquals(entity.getBranchName(), dto.getBranchName());
        assertEquals(entity.getBranchPostCode(), dto.getBranchPostCode());
        assertEquals(entity.getCreationDate(), dto.getCreationDate());
    }

    @Test
    void testToEntity() {
        BranchDTO dto = new BranchDTO(
                2L,
                "PJ Branch",
                "46000",
                LocalDateTime.of(2023, 12, 25, 9, 30)
        );

        BranchEntity entity = branchMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getBranchID(), entity.getBranchID());
        assertEquals(dto.getBranchName(), entity.getBranchName());
        assertEquals(dto.getBranchPostCode(), entity.getBranchPostCode());
        assertEquals(dto.getCreationDate(), entity.getCreationDate());
    }

    @Test
    void testListMapping() {
        List<BranchEntity> entityList = List.of(
                new BranchEntity(1L, "Branch A", "12345", LocalDateTime.now()),
                new BranchEntity(2L, "Branch B", "67890", LocalDateTime.now())
        );

        List<BranchDTO> dtoList = branchMapper.toDtoList(entityList);

        assertEquals(2, dtoList.size());
        assertEquals("Branch A", dtoList.get(0).getBranchName());
        assertEquals("Branch B", dtoList.get(1).getBranchName());
    }

    @Test
    void testCRUD() {
        // CREATE
        BranchEntity branch = new BranchEntity();
        branch.setBranchName("Test Branch");
        branch.setBranchPostCode("12345");
        branch.setCreationDate(LocalDateTime.now());

        BranchEntity saved = branchRepo.save(branch);
        assertNotNull(saved.getBranchID());

        // READ
        Optional<BranchEntity> found = branchRepo.findById(saved.getBranchID());
        assertTrue(found.isPresent());
        assertEquals("Test Branch", found.get().getBranchName());

        // UPDATE
        BranchEntity toUpdate = found.get();
        toUpdate.setBranchName("Updated Branch");
        BranchEntity updated = branchRepo.save(toUpdate);
        assertEquals("Updated Branch", updated.getBranchName());

        // DELETE
        branchRepo.delete(updated);
        Optional<BranchEntity> deleted = branchRepo.findById(updated.getBranchID());
        assertFalse(deleted.isPresent());
    }
}
