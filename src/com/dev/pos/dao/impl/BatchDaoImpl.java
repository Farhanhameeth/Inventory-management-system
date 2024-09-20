package com.dev.pos.dao.impl;

import com.dev.pos.dao.CrudUtil;
import com.dev.pos.dao.custom.BatchDao;
import com.dev.pos.dto.BatchDto;
import com.dev.pos.dto.ProductDetailsJoinDto;
import com.dev.pos.entity.Batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BatchDaoImpl implements BatchDao {
    @Override
    public boolean save(Batch batch) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO batch VALUES(?,?,?,?,?,?,?,?)";
        return CrudUtil.execute(
                sql,
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

    @Override
    public boolean update(Batch batch) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE batch SET barcode=?, qty_on_hand=?, selling_price=?, discount_availability=?, show_price=?, buying_price=?, product_code=? WHERE code=?";
        return CrudUtil.execute(
                sql,
                batch.getBarcode(),
                batch.getQtyOnHand(),
                batch.getSellingPrice(),
                batch.isDiscount(),
                batch.getShowPrice(),
                batch.getBuyingPrice(),
                batch.getProductCode(),
                batch.getCode()
        );
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM batch WHERE code=?";
        return CrudUtil.execute(sql, s);
    }

    @Override
    public Batch find(String s) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM batch WHERE code=?";
        ResultSet resultSet = CrudUtil.execute(sql, s);

        if (resultSet.next()) {
            return  new Batch(
                    resultSet.getString("code"),
                    resultSet.getString("barcode"),
                    resultSet.getInt("qty_on_hand"),
                    resultSet.getDouble("selling_price"),
                    resultSet.getBoolean("discount_availability"),
                    resultSet.getDouble("show_price"),
                    resultSet.getDouble("buying_price"),
                    resultSet.getInt("product_code")
            );
        }
        return null;
    }

    @Override
    public List<Batch> findAll() throws SQLException, ClassNotFoundException {

        List<Batch> batchList = new ArrayList<>();

        String sql = "SELECT * FROM batch";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            batchList.add(new Batch(
                    resultSet.getString("code"),
                    resultSet.getString("barcode"),
                    resultSet.getInt("qty_on_hand"),
                    resultSet.getDouble("selling_price"),
                    resultSet.getBoolean("discount_availability"),
                    resultSet.getDouble("show_price"),
                    resultSet.getDouble("buying_price"),
                    resultSet.getInt("product_code")
            ));
        }
        return batchList;
    }

    @Override
    public List<Batch> search(String s) throws SQLException, ClassNotFoundException {

        List<Batch> batchList = new ArrayList<>();

        String sql = "SELECT * FROM batch WHERE code LIKE ? || product_code LIKE ?";
        ResultSet resultSet = CrudUtil.execute(sql, "%" + s + "%", "%" + s + "%");

        while (resultSet.next()) {
            batchList.add(new Batch(
                    resultSet.getString("code"),
                    resultSet.getString("barcode"),
                    resultSet.getInt("qty_on_hand"),
                    resultSet.getDouble("selling_price"),
                    resultSet.getBoolean("discount_availability"),
                    resultSet.getDouble("show_price"),
                    resultSet.getDouble("buying_price"),
                    resultSet.getInt("product_code")
            ));
        }
        return batchList;
    }

    @Override
    public List<Batch> findAllBatch(int productCode) throws SQLException, ClassNotFoundException {

        List<Batch> batchList = new ArrayList<>();

        String sql = "SELECT * FROM batch WHERE product_code = ?";
        ResultSet resultSet = CrudUtil.execute(sql, productCode);

        while (resultSet.next()) {
            batchList.add(new Batch(
                    resultSet.getString("code"),
                    resultSet.getString("barcode"),
                    resultSet.getInt("qty_on_hand"),
                    resultSet.getDouble("selling_price"),
                    resultSet.getBoolean("discount_availability"),
                    resultSet.getDouble("show_price"),
                    resultSet.getDouble("buying_price"),
                    resultSet.getInt("product_code")
            ));
        }
        return batchList;
    }

    @Override
    public ProductDetailsJoinDto findProductDetailsJoin(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM batch b JOIN product p ON b.code = ? AND b.product_code = p.code";
        ResultSet resultSet = CrudUtil.execute(sql, code);

        if (resultSet.next()) {
            return new ProductDetailsJoinDto(
                    resultSet.getInt(9),
                    resultSet.getString(10),
                    new BatchDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getDouble(4),
                            resultSet.getBoolean(5),
                            resultSet.getDouble(6),
                            resultSet.getDouble(7),
                            resultSet.getInt(8)
                    )
            );
        }
        return null;
    }

    @Override
    public boolean updateQtyOnHand(String barcode, int qtyOnHand) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE batch SET qty_on_hand = (qty_on_hand - ?) WHERE code = ?";
        return CrudUtil.execute(sql, qtyOnHand, barcode);
    }
}
