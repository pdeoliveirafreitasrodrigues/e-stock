package com.freitasprojects.estock.models.entities;

import jakarta.persistence.*;

@Entity
public class ProductStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_id") // Mapeia o ID do produto
    private Long productId;

    @Column(name = "deposit_id") // Mapeia o ID do depósito
    private Long depositId;

    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Double totalCostPrice;


    public ProductStock() {
    }

    public ProductStock(Long id, Long productId, Long depositId, Integer quantity, Double totalCostPrice) {
        this.id = id;
        this.productId = productId;
        this.depositId = depositId;
        this.quantity = quantity;
        this.totalCostPrice = totalCostPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getDepositId() {
        return depositId;
    }

    public void setDepositId(Long depositId) {
        this.depositId = depositId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalCostPrice() {
        return totalCostPrice;
    }

    public void setTotalCostPrice(Double totalCostPrice) {
        this.totalCostPrice = totalCostPrice;
    }

    @Override
    public String toString() {
        return "ProductStock{" +
                "id=" + id +
                ", productId=" + productId +
                ", depositId=" + depositId +
                ", quantity=" + quantity +
                ", totalCostPrice=" + totalCostPrice +
                '}';
    }
}
