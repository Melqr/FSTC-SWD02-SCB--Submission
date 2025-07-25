package com.bank.repo;

import com.bank.entity.BranchEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // Use application-test.properties for H2 in-memory DB
public class BranchSearchTest {

    @Autowired
    private IBranchRepo branchRepo;

    @Test
    @Order(1)
    void testFindByBranchNameContainingIgnoreCase() {
        // Arrange
        BranchEntity branch1 = new BranchEntity(null, "Shah Alam Branch", "40000", LocalDateTime.now().minusDays(10));
        BranchEntity branch2 = new BranchEntity(null, "Cyberjaya Branch", "63000", LocalDateTime.now().minusDays(5));
        branchRepo.saveAll(List.of(branch1, branch2));

        // Act
        List<BranchEntity> result = branchRepo.findByBranchNameContainingIgnoreCase("branch");

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(b -> b.getBranchName().contains("Cyberjaya")));
    }

    @Test
    @Order(2)
    void testFindByCreationDateBetween() {
        // Arrange
        LocalDateTime baseTime = LocalDateTime.of(2024, 7, 25, 12, 0);
        BranchEntity branch1 = new BranchEntity(null, "Seremban Branch", "70000", baseTime.minusDays(7));
        BranchEntity branch2 = new BranchEntity(null, "Melaka Branch", "75000", baseTime.minusDays(2));
        branchRepo.saveAll(List.of(branch1, branch2));

        // Act
        List<BranchEntity> result = branchRepo.findByCreationDateBetween(
                baseTime.minusDays(10),
                baseTime.minusDays(1)
        );

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(b -> b.getBranchName().contains("Melaka")));
    }

}
