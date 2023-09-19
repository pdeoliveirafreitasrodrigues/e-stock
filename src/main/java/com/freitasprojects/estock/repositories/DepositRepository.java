package com.freitasprojects.estock.repositories;

import com.freitasprojects.estock.models.entities.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<Deposit, Long> {

}