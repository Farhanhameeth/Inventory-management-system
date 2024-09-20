package com.dev.pos.dao.custom;

import com.dev.pos.dao.CrudDao;
import com.dev.pos.entity.ItemDetail;

import java.util.List;

public interface ItemDetailDao extends CrudDao<ItemDetail, String> {
    List<ItemDetail> findAllByOrderCode(Integer orderCode) throws Exception;
}
