package com.dev.pos.dao.impl;

import com.dev.pos.dao.CrudUtil;
import com.dev.pos.dao.custom.ProductDao;
import com.dev.pos.db.DBConnection;
import com.dev.pos.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public boolean save(Product product) throws SQLException, ClassNotFoundException {

            String sql = "INSERT INTO product VALUES(?,?)";
            return CrudUtil.execute(
                    sql,
                    product.getCode(),
                    product.getDescription()
            );
    }

    @Override
    public boolean update(Product product) throws SQLException, ClassNotFoundException {

            String sql = "UPDATE product SET description =? WHERE code = ?";
            return CrudUtil.execute(
                    sql,
                    product.getDescription(),
                    product.getCode()
            );
    }

    @Override
    public boolean delete(Integer code) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM product WHERE code = ?";
        return CrudUtil.execute(sql, code);
    }

    @Override
    public Product find(Integer code) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM product WHERE code = ?";
        ResultSet resultSet = CrudUtil.execute(sql, code);
        if (resultSet.next()) {
            return new Product(
                    resultSet.getInt(1),
                    resultSet.getString(2)
            );
        }
        return null;
    }

    @Override
    public List<Product> findAll() throws SQLException, ClassNotFoundException {

        List<Product> productDtos = new ArrayList<>();

        String sql = "SELECT * FROM product";
        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            productDtos.add(new Product(
                    resultSet.getInt(1),
                    resultSet.getString(2)
            ));
        }
        return productDtos;
    }

    @Override
    public List<Product> search(Integer searchText) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int getLastProductID() throws SQLException, ClassNotFoundException {

        String sql = "SELECT code FROM product ORDER BY code DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()) {
            return resultSet.getInt(1)+1;
        }
        return 1;
    }

    @Override
    public List<Product> searchByDescription(String searchText) throws SQLException, ClassNotFoundException {

        List<Product> product = new ArrayList<>();

        String sql = "SELECT * FROM product WHERE description LIKE ?";
        ResultSet resultSet = CrudUtil.execute(sql, "%" + searchText + "%");
        while (resultSet.next()) {
            product.add(new Product(
                    resultSet.getInt(1),
                    resultSet.getString(2)
            ));
        }
        return product;
    }
}
