package com.freitasprojects.estock.models.entities;

import jakarta.persistence.*;

@Entity
public class ProductStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    private Deposit deposit;

    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Float totalCostPrice;


    public ProductStock() {
    }

    public ProductStock(Long id, Product product, Deposit deposit, Integer quantity, Float totalCostPrice) {
        this.id = id;
        this.product = product;
        this.deposit = deposit;
        this.quantity = quantity;
        this.totalCostPrice = totalCostPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public void setDeposit(Deposit deposit) {
        this.deposit = deposit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getTotalCostPrice() {
        return totalCostPrice;
    }

    public void setTotalCostPrice(Float totalCostPrice) {
        this.totalCostPrice = totalCostPrice;
    }

    @Override
    public String toString() {
        return "ProductStock{" +
                "id=" + id +
                ", product=" + product +
                ", deposit=" + deposit +
                ", quantity=" + quantity +
                ", totalCostPrice=" + totalCostPrice +
                '}';
    }
}
