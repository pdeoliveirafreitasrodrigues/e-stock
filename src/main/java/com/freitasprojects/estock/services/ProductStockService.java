package com.freitasprojects.estock.services;

import com.freitasprojects.estock.models.dtos.DepositDTO;
import com.freitasprojects.estock.models.dtos.ProductDTO;
import com.freitasprojects.estock.models.dtos.ProductStockDTO;
import com.freitasprojects.estock.models.entities.Deposit;
import com.freitasprojects.estock.models.entities.Product;
import com.freitasprojects.estock.models.entities.ProductStock;
import com.freitasprojects.estock.repositories.DepositRepository;
import com.freitasprojects.estock.repositories.ProductRepository;
import com.freitasprojects.estock.repositories.ProductStockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.freitasprojects.estock.consts.ExceptionConsts.*;


@Service
public class ProductStockService {

    @Autowired
    ProductStockRepository repository;

    @Autowired
    ModelMapper mapper;

    //Repositories auxiliares
    @Autowired
    ProductService productService;
    @Autowired
    DepositService depositService;



    public List<Object[]> findProductDetailsInStock() {
        return repository.findProductDetailsInStock();
    }

    public List<Object[]> findAllProductsAndQuantitiesInStock() {
        return repository.findAllProductsAndQuantitiesInStock();
    }

    public List<ProductStockDTO> listAll() {

        List<ProductStockDTO> productStockDTOList = repository.findAll()
                .stream()
                .map(productStock -> mapper.map(productStock, ProductStockDTO.class)).toList();

        return productStockDTOList;
    }

    public ProductStockDTO findById(Long id){
        Optional<ProductStock> optional = repository.findById(id);
        ProductStockDTO productStockDTO = null;

        if (optional.isPresent()) {
            productStockDTO = mapper.map(optional.get(), ProductStockDTO.class);
        }

        return productStockDTO;
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }


    /*public Long save(ProductStockDTO productStockDTO) throws Exception {
        try {
            Long productId = productStockDTO.getProductId(); // Use getProductId para obter o ID do produto
            Long depositId = productStockDTO.getDepositId(); // Use getDepositId para obter o ID do depósito
            Integer quantity = productStockDTO.getQuantity();

            // Recupere o produto e o depósito com base nos IDs fornecidos
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new Exception("Produto não encontrado."));
            Deposit deposit = depositRepository.findById(depositId)
                    .orElseThrow(() -> new Exception("Depósito não encontrado."));

            if (quantity >= 0) {
                double valorTotal = quantity * product.getCostPrice();
                ProductStock productStock = new ProductStock();
                productStock.setProduct(product);
                productStock.setDeposit(deposit);
                productStock.setQuantity(quantity);
                productStock.setTotalCostPrice(valorTotal);

                ProductStock created = repository.save(productStock);

                return created.getId();
            } else {
                throw new Exception("Quantidades zeradas, preço total não alterado");
            }
        } catch (Exception e) {
            throw new Exception(PRODUCTSTOCK_INSERT_ERROR);
        }
    }

    */
    public Long save(ProductStockDTO productStockDTO) throws Exception {
        try {
                ProductDTO productDTO = productService.findById(productStockDTO.getProductId());
                DepositDTO depositDTO = depositService.findById(productStockDTO.getDepositId());
                Integer quantity = productStockDTO.getQuantity();


                double valorTotal = quantity * productDTO.getCostPrice();
                ProductStock productStock = new ProductStock();
                productStock.setQuantity(quantity);
                productStock.setTotalCostPrice(valorTotal);
                productStock.setProductId(productDTO.getId());
                productStock.setDepositId(depositDTO.getId());


                ProductStock created = repository.save(productStock);

                return created.getId();
            } catch(Exception e){
                throw new Exception(PRODUCTSTOCK_INSERT_ERROR);
            }
    }

    public Long create(ProductStockDTO productStockDTO) throws Exception{
        return save(productStockDTO);
    }

    /*public Long update(ProductStockDTO productStockDTO) throws Exception {
        var optional = repository.findById(productStockDTO.getId());

        if (optional.isPresent()) {
            ProductStock productStock = optional.get();

            // Atualize a quantidade
            if (productStockDTO.getQuantity() != null && productStockDTO.getQuantity() > 0) {
                int newQuantity = productStockDTO.getQuantity();
                productStock.setQuantity(newQuantity);
                pr

                // Verifique se o preço de custo do produto está definido
                if (productStock.getProductId() != null) {
                    // Recalcule o preço de custo total
                    double newTotalCostPrice = newQuantity * productStock.getProduct().getCostPrice();
                    productStock.setTotalCostPrice(newTotalCostPrice);
                }
            }
            // ...

            repository.save(productStock);
            return productStock.getId();
        } else {
            throw new Exception("Registro de ProductStock não encontrado.");
        }
    }*/

}
