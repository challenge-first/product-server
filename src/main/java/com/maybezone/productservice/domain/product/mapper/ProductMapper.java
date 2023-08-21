package com.maybezone.productservice.domain.product.mapper;

import com.maybezone.productservice.domain.product.dto.response.ResponseProductDto;
import com.maybezone.productservice.domain.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper PRODUCT_INSTANCE = Mappers.getMapper(ProductMapper.class);

    ResponseProductDto entityToResponseProductDto(Product product);

}
