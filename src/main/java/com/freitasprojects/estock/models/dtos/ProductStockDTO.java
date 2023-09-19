package com.freitasprojects.estock.models.dtos;

import com.freitasprojects.estock.models.entities.Deposit;
import com.freitasprojects.estock.models.entities.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockDTO implements Serializable {

    public static final String MIN_COST_PRICE = "0.0";
    private static final long serialVersionUID = 42L;

    private Long id;

    private Product product; //PERGUNTAR AO PROFESSOR SE NESTE CASO POSSO SÓ CHAMAR O ID. O QUE É MAIS USUAL E RÁPIDO

    private Deposit deposit;

    private Integer quantity;

    @DecimalMin(value = MIN_COST_PRICE, message = "O campo preço de custo total não pode ser negativo")
    private Float totalCostPrice;

}
