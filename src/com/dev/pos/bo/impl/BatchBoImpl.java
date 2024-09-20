package com.dev.pos.bo.impl;

import com.dev.pos.Enum.DaoType;
import com.dev.pos.bo.custom.BatchBo;
import com.dev.pos.dao.DaoFactory;
import com.dev.pos.dao.custom.BatchDao;
import com.dev.pos.dto.BatchDto;
import com.dev.pos.dto.ProductDetailsJoinDto;
import com.dev.pos.entity.Batch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BatchBoImpl implements BatchBo {

    BatchDao batchDao = DaoFactory.getInstance().getDao(DaoType.BATCH);

    @Override
    public boolean saveBatch(BatchDto batchDto) throws SQLException, ClassNotFoundException {
        return batchDao.save(new Batch(
                batchDto.getCode(),
                batchDto.getBarcode(),
                batchDto.getQtyOnHand(),
                batchDto.getSellingPrice(),
                batchDto.isDiscount(),
                batchDto.getShowPrice(),
                batchDto.getBuyingPrice(),
                batchDto.getProductCode()
        ));
    }

    @Override
    public boolean updateBatch(BatchDto batchDto) throws SQLException, ClassNotFoundException {
        return batchDao.update(new Batch(
                batchDto.getCode(),
                batchDto.getBarcode(),
                batchDto.getQtyOnHand(),
                batchDto.getSellingPrice(),
                batchDto.isDiscount(),
                batchDto.getShowPrice(),
                batchDto.getBuyingPrice(),
                batchDto.getProductCode()
        ));
    }

    @Override
    public boolean deleteBatch(String code) throws SQLException, ClassNotFoundException {
        return batchDao.delete(code);
    }

    @Override
    public BatchDto findBatch(String code) throws SQLException, ClassNotFoundException {
        Batch batch = batchDao.find(code);
        if (batch != null) {
            return new BatchDto(
                    batch.getCode(),
                    batch.getBarcode(),
                    batch.getQtyOnHand(),
                    batch.getSellingPrice(),
                    batch.isDiscount(),
                    batch.getShowPrice(),
                    batch.getBuyingPrice(),
                    batch.getProductCode()
            );
        }
        return null;
    }

    @Override
    public List<BatchDto> searchBatch(String value) throws SQLException, ClassNotFoundException {

        List<BatchDto> batchDtos = new ArrayList<>();
        for (Batch batch : batchDao.search(value)) {
            batchDtos.add(
                    new BatchDto(
                            batch.getCode(),
                            batch.getBarcode(),
                            batch.getQtyOnHand(),
                            batch.getSellingPrice(),
                            batch.isDiscount(),
                            batch.getShowPrice(),
                            batch.getBuyingPrice(),
                            batch.getProductCode()
                    )
            );
        }
        return batchDtos;
    }

    @Override
    public List<BatchDto> findAllBatch(int productCode) throws SQLException, ClassNotFoundException {

        List<BatchDto> batchDtos = new ArrayList<>();
        for (Batch batch : batchDao.findAllBatch(productCode)) {
            batchDtos.add(
                    new BatchDto(
                            batch.getCode(),
                            batch.getBarcode(),
                            batch.getQtyOnHand(),
                            batch.getSellingPrice(),
                            batch.isDiscount(),
                            batch.getShowPrice(),
                            batch.getBuyingPrice(),
                            batch.getProductCode()
                    )
            );
        }
        return batchDtos;
    }

    @Override
    public ProductDetailsJoinDto findProductDetailsJoin(String code) throws SQLException, ClassNotFoundException {
        return batchDao.findProductDetailsJoin(code);
    }
}
