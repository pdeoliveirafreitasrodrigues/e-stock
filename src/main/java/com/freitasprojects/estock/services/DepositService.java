package com.freitasprojects.estock.services;

import com.freitasprojects.estock.models.dtos.DepositDTO;
import com.freitasprojects.estock.models.dtos.ProductStockDTO;
import com.freitasprojects.estock.models.entities.Deposit;
import com.freitasprojects.estock.models.entities.ProductStock;
import com.freitasprojects.estock.repositories.DepositRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepositService {

    @Autowired
    DepositRepository repository;
    @Autowired
    ModelMapper mapper;

    public DepositDTO findById(Long id){
        Optional<Deposit> optional = repository.findById(id);
        DepositDTO depositDTO = null;

        if (optional.isPresent()) {
            depositDTO = mapper.map(optional.get(), DepositDTO.class);
        }

        return depositDTO;
    }

}
