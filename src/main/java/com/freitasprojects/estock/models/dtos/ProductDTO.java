package com.freitasprojects.estock.models.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

@Data
@NoArgsConstructor // Gera um construtor sem argumentos
@AllArgsConstructor // Gera um construtor com todos os argumentos
public class ProductDTO implements Serializable {

    public static final String MIN_COST_PRICE = "0.0";

    private static final Long serialVersionUID = 42L;

    private Long id;

    @NotBlank(message = "O campo código não pode estar em branco")
    @NotEmpty(message = "O campo código não pode estar vazio")
    private String code;

    @NotBlank(message = "O campo descrição não pode estar em branco")
    @NotEmpty(message = "O campo descrição não pode estar vazio")
    private String description;

    @NotBlank(message = "O campo preço de custo não pode estar em branco")
    @NotEmpty(message = "O campo preço de custo não pode estar vazio")
    @DecimalMin(value = MIN_COST_PRICE, message = "O campo preço de custo não pode ser negativo")
    private Float costPrice;

}
