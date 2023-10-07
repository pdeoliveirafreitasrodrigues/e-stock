package com.freitasprojects.estock.configurations;

import com.freitasprojects.estock.models.dtos.ProductDTO;
import com.freitasprojects.estock.models.entities.Product;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

/*    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configurar mapeamentos aqui
        modelMapper.createTypeMap(Product.class, ProductDTO.class)
                .addMapping(src -> src.getCode(), ProductDTO::setCode)
                .addMapping(src -> src.getDescription(), ProductDTO::setDescription)
                .addMapping(src -> src.getCostPrice(), ProductDTO::setCostPrice);

        return modelMapper;
    }*/

}
