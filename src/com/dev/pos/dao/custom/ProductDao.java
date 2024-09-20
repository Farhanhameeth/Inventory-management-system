package com.dev.pos.dao.custom;

import com.dev.pos.dao.CrudDao;
import com.dev.pos.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao extends CrudDao<Product, Integer> {

    int getLastProductID() throws SQLException, ClassNotFoundException;

    List<Product> searchByDescription(String searchText) throws SQLException, ClassNotFoundException;
}
