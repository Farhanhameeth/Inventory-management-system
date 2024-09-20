package com.dev.pos.bo.impl;

import com.dev.pos.Enum.DaoType;
import com.dev.pos.bo.custom.ProductBo;
import com.dev.pos.dao.DaoFactory;
import com.dev.pos.dao.custom.ProductDao;
import com.dev.pos.dto.ProductDto;
import com.dev.pos.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductBoImpl implements ProductBo {

    ProductDao productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);

    @Override
    public boolean saveProduct(ProductDto dto) throws SQLException, ClassNotFoundException {
        return productDao.save(
                new Product(
                        dto.getCode(),
                        dto.getDescription()
                )
        );
    }

    @Override
    public boolean updateProduct(ProductDto dto) throws SQLException, ClassNotFoundException {
        return productDao.update(
                new Product(
                        dto.getCode(),
                        dto.getDescription()
                )
        );
    }

    @Override
    public boolean deleteProduct(int code) throws SQLException, ClassNotFoundException {
        return productDao.delete(code);
    }

    @Override
    public ProductDto findProduct(int code) throws SQLException, ClassNotFoundException {
        Product product = productDao.find(code);
        if (product != null) {
            return new ProductDto(
                    product.getCode(),
                    product.getDescription()
            );
        }
        return null;
    }

    @Override
    public List<ProductDto> findAllProducts() throws SQLException, ClassNotFoundException {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product p : productDao.findAll()) {
            productDtos.add(
                    new ProductDto(
                            p.getCode(),
                            p.getDescription()
                    )
            );
        }
        return productDtos;
    }

    @Override
    public List<ProductDto> searchByDescription(String value) throws SQLException, ClassNotFoundException {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product p : productDao.searchByDescription(value)) {
            productDtos.add(
                    new ProductDto(
                            p.getCode(),
                            p.getDescription()
                    )
            );
        }
        return productDtos;
    }

    @Override
    public int getLastProductId() throws SQLException, ClassNotFoundException {
        return productDao.getLastProductID();
    }
}
