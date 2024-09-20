package com.dev.pos.bo.impl;

import com.dev.pos.Enum.DaoType;
import com.dev.pos.bo.custom.ItemDetailBo;
import com.dev.pos.dao.DaoFactory;
import com.dev.pos.dao.custom.*;
import com.dev.pos.dto.ItemDto;
import com.dev.pos.entity.Batch;
import com.dev.pos.entity.ItemDetail;
import com.dev.pos.entity.OrderDetail;
import com.dev.pos.entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class ItemDetailBoImpl implements ItemDetailBo {

    OrderDetailDao orderDetailDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);
    ItemDetailDao itemDetailDao = DaoFactory.getInstance().getDao(DaoType.ITEM_DETAIL);
    BatchDao batchDao = DaoFactory.getInstance().getDao(DaoType.BATCH);
    ProductDao productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);

    @Override
    public List<ItemDto> findOrderByDate(LocalDate date) throws Exception {
        ObservableList<ItemDto> itemsList = FXCollections.observableArrayList();

        for (OrderDetail orderDetail : orderDetailDao.findOrderByDate(date)) {

            for (ItemDetail itemDetail : itemDetailDao.findAllByOrderCode(orderDetail.getCode())) {

                Batch batch = batchDao.find(itemDetail.getBatchCode());
                Product product = productDao.find(batch.getProductCode());


                itemsList.add(new ItemDto(
                        orderDetail.getCode(),
                        batch.getProductCode(),
                        product.getDescription(),
                        itemDetail.getQty(),
                        itemDetail.getAmount()));
            }
        }
        return itemsList;
    }
}
