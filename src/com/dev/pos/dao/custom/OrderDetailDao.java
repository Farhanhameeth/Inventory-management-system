package com.dev.pos.dao.custom;

import com.dev.pos.dao.CrudDao;
import com.dev.pos.entity.OrderDetail;

import java.time.LocalDate;
import java.util.List;

public interface OrderDetailDao extends CrudDao<OrderDetail, Integer> {
    List<OrderDetail> findOrderByDate (LocalDate date) throws Exception;
}
