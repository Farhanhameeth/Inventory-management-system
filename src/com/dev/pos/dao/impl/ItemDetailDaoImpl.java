package com.dev.pos.dao.impl;

import com.dev.pos.dao.CrudUtil;
import com.dev.pos.dao.custom.ItemDetailDao;
import com.dev.pos.entity.ItemDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class ItemDetailDaoImpl implements ItemDetailDao {
    @Override
    public boolean save(ItemDetail itemDetail) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO order_detail_has_batch VALUES (?,?,?,?,?)";
        return CrudUtil.execute(
                sql,
                itemDetail.getOrder(),
                itemDetail.getBatchCode(),
                itemDetail.getQty(),
                itemDetail.getDiscount(),
                itemDetail.getAmount()
        );
    }

    @Override
    public boolean update(ItemDetail itemDetail) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String string) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ItemDetail find(String orderCode) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM order_detail_has_batch WHERE order_detail_code = ?";
        ResultSet resultSet = CrudUtil.execute(sql, orderCode);

        if (resultSet.next()) {
            return new ItemDetail(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)
            );
        }
        return null;
    }

    @Override
    public List<ItemDetail> findAll() throws SQLException, ClassNotFoundException {
        return Collections.emptyList();
    }

    @Override
    public List<ItemDetail> search(String string) throws SQLException, ClassNotFoundException {
        return Collections.emptyList();
    }

    @Override
    public List<ItemDetail> findAllByOrderCode(Integer orderCode) throws Exception {
        ObservableList<ItemDetail> itemDetailsList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM order_detail_has_batch WHERE order_detail_code = ?";
        ResultSet resultSet = CrudUtil.execute(sql, orderCode);

        while (resultSet.next()) {
            itemDetailsList.add(new ItemDetail(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)
            ));
        }
        return itemDetailsList;
    }
}
