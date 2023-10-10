package com.freitasprojects.estock.repositories;

import com.freitasprojects.estock.models.entities.Deposit;
import com.freitasprojects.estock.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepositRepository extends JpaRepository<Deposit, Long> {

    Optional<Deposit> findByCode(String code);
    Optional<Product> findByName(String name);



}