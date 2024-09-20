package com.dev.pos.bo.custom;

import com.dev.pos.dto.ItemDto;
import com.dev.pos.dto.OrderDetailDto;
import com.dev.pos.dto.OrderDto;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OrderDetailBo {

    public boolean placeOrder(OrderDetailDto orderDetailDTO);

    public List<OrderDto> findAllOrders() throws SQLException, ClassNotFoundException;

    public List<OrderDto> searchOrders(int code) throws SQLException, ClassNotFoundException;

}
