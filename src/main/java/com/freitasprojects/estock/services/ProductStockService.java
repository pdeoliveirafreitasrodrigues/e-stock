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

    public void deleteById(Long id){
        repository.deleteById(id);
    }

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

        /*// Verificando se o registro já não existe;
        ProductDTO productInStorage = productService.findById(productStockDTO.getProductId());
        DepositDTO depositInStorage = depositService.findById(productStockDTO.getDepositId());

        if (productInStorage.getId() == productStockDTO.getProductId() && depositInStorage.getId() == productStockDTO.getDepositId()){
            update(productStockDTO);
            return productStockDTO.getId();
        }else{
            return save(productStockDTO);
        }*/

        return save(productStockDTO);
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
    }


}
