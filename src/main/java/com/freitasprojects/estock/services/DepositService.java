package com.freitasprojects.estock.services;

import com.freitasprojects.estock.models.dtos.DepositDTO;
import com.freitasprojects.estock.models.dtos.ProductDTO;
import com.freitasprojects.estock.models.dtos.ProductStockDTO;
import com.freitasprojects.estock.models.entities.Deposit;
import com.freitasprojects.estock.models.entities.Product;
import com.freitasprojects.estock.models.entities.ProductStock;
import com.freitasprojects.estock.repositories.DepositRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.freitasprojects.estock.consts.ExceptionConsts.PRODUCT_INSERT_ERROR;

@Service
public class DepositService {

    @Autowired
    DepositRepository repository;
    @Autowired
    ModelMapper mapper;


    public List<DepositDTO> listAll() {
        List<DepositDTO> depositDTOList = repository.findAll()
                .stream()
                .map(deposit -> mapper.map(deposit, DepositDTO.class)).toList();
        return depositDTOList;
    }

    public DepositDTO findById(Long id) throws Exception{
        Optional<Deposit> optional = repository.findById(id);
        DepositDTO depositDTO = new DepositDTO();

        if (optional.isPresent()) {
            depositDTO = mapper.map(optional.get(), DepositDTO.class);
        }

        if (depositDTO != null){
            return depositDTO;
        }else {
            throw new Exception("Objeto não encontrado");
        }

    }

    public DepositDTO findByCode(String code) {
        Optional<Deposit> depositOptional = repository.findByCode(code);

        if (depositOptional.isPresent()) {
            return mapper.map(depositOptional.get(), DepositDTO.class);
        } else {
            return null;
        }
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Long save(DepositDTO depositDTO) throws Exception {
        try {
            Deposit deposit = mapper.map(depositDTO, Deposit.class);
            Deposit created = repository.save(deposit);

            return created.getId();
        } catch (Exception e) {
            throw new Exception(PRODUCT_INSERT_ERROR);
        }
    }


    public Long create(DepositDTO depositDTO) throws Exception{
        return save(depositDTO);
    }

    @Transactional
    public Long update(DepositDTO depositDTO) throws Exception {
        Optional<Deposit> optional = repository.findById(depositDTO.getId());

        if (optional.isPresent()) {
            Deposit deposit = optional.get();

            if (!depositDTO.getCode().isEmpty()) {
                deposit.setCode(depositDTO.getCode());
            }
            if (!depositDTO.getName().isEmpty()) {
                deposit.setName(depositDTO.getName());
            } else {
                throw new Exception("Nome inválido.");
            }

            repository.save(deposit);
            return deposit.getId();
        } else {
            throw new Exception("Depósito não encontrado.");
        }
    }








}
