package com.dev.pos.bo.impl;

import com.dev.pos.Enum.DaoType;
import com.dev.pos.bo.custom.ItemDetailBo;
import com.dev.pos.bo.custom.OrderDetailBo;
import com.dev.pos.dao.DaoFactory;
import com.dev.pos.dao.custom.*;
import com.dev.pos.db.DBConnection;
import com.dev.pos.dto.ItemDetailDto;
import com.dev.pos.dto.ItemDto;
import com.dev.pos.dto.OrderDetailDto;
import com.dev.pos.dto.OrderDto;
import com.dev.pos.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrderDetailBoImpl implements OrderDetailBo {

    OrderDetailDao orderDetailDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);
    ItemDetailDao itemDetailDao = DaoFactory.getInstance().getDao(DaoType.ITEM_DETAIL);
    BatchDao batchDao = DaoFactory.getInstance().getDao(DaoType.BATCH);
    CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    ProductDao productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);

    @Override
    public boolean placeOrder(OrderDetailDto orderDetailDto) {

        try{
            Connection connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            OrderDetail orderDetail = new OrderDetail(
                    orderDetailDto.getCode(),
                    orderDetailDto.getIssuedDate(),
                    orderDetailDto.getTotalCost(),
                    orderDetailDto.getCustomerEmail(),
                    orderDetailDto.getDiscount(),
                    orderDetailDto.getUserEmail()
            );

            if (orderDetailDao.save(orderDetail)) {

                for (ItemDetailDto itemDetailDto : orderDetailDto.getItemDetailList()) {

                    ItemDetail itemDetail = new ItemDetail(
                            orderDetailDto.getCode(),
                            itemDetailDto.getDetailCode(),
                            itemDetailDto.getQty(),
                            itemDetailDto.getDiscount(),
                            itemDetailDto.getAmount()
                    );

                    if (itemDetailDao.save(itemDetail)) {

                        if (!batchDao.updateQtyOnHand(itemDetailDto.getDetailCode(), itemDetailDto.getQty())) {

                            connection.rollback();
                            return false;

                        }

                    } else {
                        connection.rollback();
                        return false;
                    }
                }

                connection.commit();
                return true;

            } else {
                connection.rollback();
                return false;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<OrderDto> findAllOrders() throws SQLException, ClassNotFoundException {
        ObservableList<OrderDto> orderDetailDtos = FXCollections.observableArrayList();

        for (OrderDetail orderDetail : orderDetailDao.findAll()) {

            Customer customer = customerDao.find(orderDetail.getCustomerEmail());

            orderDetailDtos.add(new OrderDto(
                    orderDetail.getCode(),
                    customer.getName(),
                    orderDetail.getIssuedDate(),
                    orderDetail.getDiscount(),
                    orderDetail.getTotalCost(),
                    orderDetail.getUserEmail()
            ));
        }
        return orderDetailDtos;
    }

    @Override
    public List<OrderDto> searchOrders(int code) throws SQLException, ClassNotFoundException {
        ObservableList<OrderDto> orderDetailDtos = FXCollections.observableArrayList();

        for (OrderDetail orderDetail : orderDetailDao.search(code)) {

            Customer customer = customerDao.find(orderDetail.getCustomerEmail());

            orderDetailDtos.add(new OrderDto(
                    orderDetail.getCode(),
                    customer.getName(),
                    orderDetail.getIssuedDate(),
                    orderDetail.getDiscount(),
                    orderDetail.getTotalCost(),
                    orderDetail.getUserEmail()
            ));
        }
        return orderDetailDtos;
    }
}
