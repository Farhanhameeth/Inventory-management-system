package com.dev.pos.bo.custom;

import com.dev.pos.bo.SuperBo;
import com.dev.pos.dto.BatchDto;
import com.dev.pos.dto.ProductDetailsJoinDto;

import java.sql.SQLException;
import java.util.List;

public interface BatchBo extends SuperBo {
    
    public boolean saveBatch(BatchDto batchDto) throws SQLException, ClassNotFoundException;

    public boolean updateBatch(BatchDto batchDto) throws SQLException, ClassNotFoundException;

    public boolean deleteBatch(String email) throws SQLException, ClassNotFoundException;

    public BatchDto findBatch(String email) throws SQLException, ClassNotFoundException;

    public List<BatchDto> searchBatch(String value) throws SQLException, ClassNotFoundException;

    public List<BatchDto> findAllBatch(int productCode) throws SQLException, ClassNotFoundException;

    public ProductDetailsJoinDto findProductDetailsJoin(String code) throws SQLException, ClassNotFoundException;

}
