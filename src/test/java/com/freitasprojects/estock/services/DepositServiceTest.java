package com.freitasprojects.estock.services;

import com.freitasprojects.estock.models.dtos.DepositDTO;
import com.freitasprojects.estock.models.entities.Deposit;
import com.freitasprojects.estock.repositories.DepositRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DepositServiceTest {
    @Mock
    private DepositRepository repository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private DepositService depositService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_valid_id_returns_deposit_dto() throws Exception {
        // Arrange
        Long id = 1L;
        Deposit deposit = new Deposit(id, "code", "name");
        DepositDTO expectedDTO = new DepositDTO(id, "code", "name");
        when(repository.findById(id)).thenReturn(Optional.of(deposit));

        // Act
        DepositDTO resultDTO = depositService.findById(id);

        // Assert
        assertEquals(expectedDTO, resultDTO);
    }

    @Test
    public void test_valid_id_returns_correct_deposit_dto() throws Exception {
        // Arrange
        Long id = 1L;
        Deposit deposit = new Deposit(id, "code", "name");
        DepositDTO expectedDTO = new DepositDTO(id, "code", "name");
        when(repository.findById(id)).thenReturn(Optional.of(deposit));

        // Act
        DepositDTO resultDTO = depositService.findById(id);

        // Assert
        assertEquals(expectedDTO, resultDTO);
    }

}
