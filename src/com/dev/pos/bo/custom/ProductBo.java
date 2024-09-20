package com.dev.pos.bo.custom;

import com.dev.pos.bo.SuperBo;
import com.dev.pos.dto.ProductDto;

import java.sql.SQLException;
import java.util.List;

public interface ProductBo extends SuperBo {

    public boolean saveProduct(ProductDto  dto) throws SQLException, ClassNotFoundException;

    public boolean updateProduct(ProductDto dto) throws SQLException, ClassNotFoundException;

    public boolean deleteProduct(int code) throws SQLException, ClassNotFoundException;

    public ProductDto findProduct(int code) throws SQLException, ClassNotFoundException;

    public List<ProductDto> findAllProducts() throws SQLException, ClassNotFoundException;

    public List<ProductDto> searchByDescription(String value) throws SQLException, ClassNotFoundException;

    public  int getLastProductId() throws SQLException, ClassNotFoundException;

}
