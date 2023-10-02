package com.freitasprojects.estock.services;

import com.freitasprojects.estock.models.dtos.DepositDTO;
import com.freitasprojects.estock.models.dtos.ProductDTO;
import com.freitasprojects.estock.models.dtos.ProductStockDTO;
import com.freitasprojects.estock.models.entities.ProductStock;
import com.freitasprojects.estock.repositories.ProductStockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;


@Service
public class ProductStockService {

    @Autowired
    ProductStockRepository repository;
    @Autowired
    ModelMapper mapper;


    //Services auxiliares
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

    public void deleteById(@PathVariable Long id) throws Exception {
        Optional<ProductStock> optional = repository.findById(id);

        if (optional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new Exception("Registro com o ID " + id + " não encontrado.");
        }
    }

    public Long linkProductToStockByCode(String productCode, String depositCode, int quantity) throws Exception {
        // Obter o produto pelo código
        ProductDTO productDTO = productService.findByCode(productCode);
        if (productDTO == null) {
            throw new Exception("Produto com código " + productCode + " não encontrado.");
        }

        // Obtenha o depósito pelo código
        DepositDTO depositDTO = depositService.findByCode(depositCode);
        if (depositDTO == null) {
            throw new Exception("Depósito com código " + depositCode + " não encontrado.");
        }

        // Verifique se já existe um registro com o mesmo productId e depositId
        Optional<ProductStock> existingProductStock = repository.findByProductIdAndDepositId(productDTO.getId(), depositDTO.getId());

        if (existingProductStock.isPresent()) {
            // Se o registro já existir, atualize a quantidade e o custo total
            ProductStock productStock = existingProductStock.get();
            int newQuantity = productStock.getQuantity() + quantity;
            double newTotalCostPrice = newQuantity * productDTO.getCostPrice();
            productStock.setQuantity(newQuantity);
            productStock.setTotalCostPrice(newTotalCostPrice);

            repository.save(productStock);
            return productStock.getId();
        } else {
            // Se o registro não existir, crie um novo
            return createNewProductStock(productDTO.getId(), depositDTO.getId(), quantity);
        }
    }

    // Adicionar quantidade do produto no estoque
    public Long addQuantityToStock(String productCode, String depositCode, int quantityToAdd) throws Exception {
        Optional<ProductStock> optional = repository.findByProductCodeAndDepositCode(productCode, depositCode);

        if (optional.isPresent()) {
            ProductStock productStock = optional.get();
            int newQuantity = productStock.getQuantity() + quantityToAdd;
            double newTotalCostPrice = newQuantity * getProductCostPrice(productStock.getProductId());
            productStock.setQuantity(newQuantity);
            productStock.setTotalCostPrice(newTotalCostPrice);

            repository.save(productStock);
            return productStock.getId();
        } else {
            throw new Exception("ProductStock com códigos de produto '" + productCode + "' e depósito '" + depositCode + "' não encontrado.");
        }
    }

    public Long removeQuantityFromStock(String productCode, String depositCode, int quantityToRemove) throws Exception {
        Optional<ProductStock> optional = repository.findByProductCodeAndDepositCode(productCode, depositCode);

        if (optional.isPresent()) {
            ProductStock productStock = optional.get();
            int newQuantity = productStock.getQuantity() - quantityToRemove;

            // Verifique se a nova quantidade é maior ou igual a zero
            if (newQuantity >= 0) {
                double newTotalCostPrice = newQuantity * getProductCostPrice(productStock.getProductId());
                productStock.setQuantity(newQuantity);
                productStock.setTotalCostPrice(newTotalCostPrice);

                repository.save(productStock);

                // Se a quantidade zerar, remova o registro do depósito
                if (newQuantity == 0) {
                    repository.delete(productStock);
                }

                return productStock.getId();
            } else {
                throw new Exception("A quantidade a ser removida é maior do que a quantidade disponível em estoque.");
            }
        } else {
            throw new Exception("ProductStock com códigos de produto '" + productCode + "' e depósito '" + depositCode + "' não encontrado.");
        }
    }


    private double getProductCostPrice(Long productId) {
        ProductDTO productDTO = productService.findById(productId);
        return (productDTO != null && productDTO.getCostPrice() != null) ? productDTO.getCostPrice() : 0.0;
    }

    private Long createNewProductStock(Long productId, Long depositId, int quantity) {
        double totalCostPrice = quantity * getProductCostPrice(productId);
        ProductStock productStock = new ProductStock();
        productStock.setQuantity(quantity);
        productStock.setTotalCostPrice(totalCostPrice);
        productStock.setProductId(productId);
        productStock.setDepositId(depositId);

        ProductStock created = repository.save(productStock);
        return created.getId();
    }


    //CÓDIGO CRUD BÁSICO QUE PODE SER UTILIZADO CASO QUEIRA
    /*

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

    public Long create(ProductStockDTO productStockDTO) throws Exception {
        // Verifique se já existe um registro com o mesmo productId e depositId
        Optional<ProductStock> existingProductStock = repository.findByProductIdAndDepositId(
                productStockDTO.getProductId(),
                productStockDTO.getDepositId()
        );

        if (existingProductStock.isPresent()) {
            // Se o registro já existir, chame o método update
            throw new Exception("Produto já vinculado ao seu estoque");
        } else {
            // Se o registro não existir, crie um novo
            return save(productStockDTO);
        }
    }


    public Long update(ProductStockDTO productStockDTO) throws Exception {
        Long productStockId = productStockDTO.getId(); // Obtenha o ID do ProductStockDTO
        if (productStockId == null) {
            throw new Exception("O ID do ProductStockDTO não pode ser nulo para atualização.");
        }

        var optional = repository.findById(productStockId);

        if (optional.isPresent()) {
            ProductStock productStock = optional.get();

            // Atualize a quantidade
            if (productStockDTO.getQuantity() != null && productStockDTO.getQuantity() >= 0) {
                int newQuantity = productStockDTO.getQuantity();
                productStock.setQuantity(newQuantity);

                // Atualize o preço de custo total se o preço do produto estiver definido
                ProductDTO productDTO = productService.findById(productStock.getProductId());
                if (productDTO != null && productDTO.getCostPrice() != null) {
                    double newTotalCostPrice = newQuantity * productDTO.getCostPrice();
                    productStock.setTotalCostPrice(newTotalCostPrice);
                }
                // ATUALIZAR DEPÓSITO
                if (productStockDTO.getDepositId() != null){
                    Long newDepositId = productStockDTO.getDepositId();
                    productStock.setDepositId(newDepositId);
                }
                // ...

                repository.save(productStock);
                return productStock.getId();
            } else {
                throw new Exception("A quantidade deve ser maior ou igual a zero para atualização.");
            }

        } else {
            throw new Exception("ProductStock data not found.");
        }
    }*/

}
