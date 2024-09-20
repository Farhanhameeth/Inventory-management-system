package com.dev.pos.dao.impl;

import com.dev.pos.dao.CrudUtil;
import com.dev.pos.dao.custom.OrderDetailDao;
import com.dev.pos.entity.OrderDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrderDetailDaoImpl implements OrderDetailDao {
    @Override
    public boolean save(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO order_detail VALUES (?,?,?,?,?,?)";
        return CrudUtil.execute(
                sql,
                orderDetail.getCode(),
                orderDetail.getIssuedDate(),
                orderDetail.getTotalCost(),
                orderDetail.getCustomerEmail(),
                orderDetail.getDiscount(),
                orderDetail.getUserEmail()
        );
    }

    @Override
    public boolean update(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Integer integer) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetail find(Integer integer) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<OrderDetail> findAll() throws SQLException, ClassNotFoundException {
        ObservableList<OrderDetail> ordersList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM order_detail";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            ordersList.add(new OrderDetail(
                    resultSet.getInt(1),
                    resultSet.getDate(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6)
            ));
        }
        return ordersList;
    }

    @Override
    public List<OrderDetail> search(Integer code) throws SQLException, ClassNotFoundException {
        ObservableList<OrderDetail> ordersList = FXCollections.observableArrayList();

        String newCode = code + "%";

        String sql = "SELECT * FROM order_detail WHERE code LIKE ?";
        ResultSet resultSet = CrudUtil.execute(sql, newCode);

        while (resultSet.next()) {
            ordersList.add(new OrderDetail(
                    resultSet.getInt(1),
                    resultSet.getDate(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6)
            ));
        }
        return ordersList;
    }

    @Override
    public List<OrderDetail> findOrderByDate(LocalDate date) throws Exception {
        ObservableList<OrderDetail> ordersList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM order_detail WHERE issued_date = ?";
        ResultSet resultSet = CrudUtil.execute(sql, date);

        while (resultSet.next()) {
            ordersList.add(new OrderDetail(
                    resultSet.getInt(1),
                    resultSet.getDate(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6)
            ));
        }
        return ordersList;
    }
}
