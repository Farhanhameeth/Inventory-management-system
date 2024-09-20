package com.dev.pos.bo.custom;

import com.dev.pos.bo.SuperBo;
import com.dev.pos.dto.ItemDto;

import java.time.LocalDate;
import java.util.List;

public interface ItemDetailBo extends SuperBo {

    List<ItemDto> findOrderByDate(LocalDate date) throws Exception;
}
