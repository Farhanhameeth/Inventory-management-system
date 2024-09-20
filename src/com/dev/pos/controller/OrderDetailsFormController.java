package com.dev.pos.controller;

import com.dev.pos.Enum.BoType;
import com.dev.pos.Enum.DaoType;
import com.dev.pos.bo.BoFactory;
import com.dev.pos.bo.custom.BatchBo;
import com.dev.pos.bo.custom.ProductBo;
import com.dev.pos.dao.DaoFactory;
import com.dev.pos.dao.custom.ItemDetailDao;
import com.dev.pos.dto.BatchDto;
import com.dev.pos.dto.ProductDto;
import com.dev.pos.dto.tm.ItemTm;
import com.dev.pos.entity.ItemDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class OrderDetailsFormController {
    public AnchorPane context;
    public TableView<ItemTm> tblOrderDetails;
    public TableColumn<ItemTm, Integer> colProductCode;
    public TableColumn<ItemTm, String> colProductName;
    public TableColumn<ItemTm, Integer> colBatchCode;
    public TableColumn<ItemTm, Integer> colQuantity;
    public TableColumn<ItemTm, Double> colDiscount;
    public TableColumn<ItemTm, Double> colAmount;
    public Label lblTotalCost;

    ItemDetailDao itemDetailDao = DaoFactory.getInstance().getDao(DaoType.ITEM_DETAIL);
    BatchBo batchBo = BoFactory.getInstance().getBo(BoType.BATCH);
    ProductBo productBo = BoFactory.getInstance().getBo(BoType.PRODUCT);

    private int orderCode;

    public void initialize() {
        colProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colBatchCode.setCellValueFactory(new PropertyValueFactory<>("batchCode"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
    }

    public void setOrderCode(int orderCode) throws Exception {
        this.orderCode = orderCode;
        loadItems();
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.close();
    }

    private void loadItems() throws Exception {
        ObservableList<ItemTm> items = FXCollections.observableArrayList();

        Double TotalCost = 0.0;

        for (ItemDetail itemDetail : itemDetailDao.findAllByOrderCode(orderCode)) {

            BatchDto batch = batchBo.findBatch(itemDetail.getBatchCode());
            ProductDto product = productBo.findProduct(batch.getProductCode());


            items.add(new ItemTm(
                    batch.getProductCode(),
                    product.getDescription(),
                    itemDetail.getBatchCode(),
                    itemDetail.getQty(),
                    itemDetail.getDiscount(),
                    itemDetail.getAmount()
            ));
            TotalCost += itemDetail.getAmount();
        }
        tblOrderDetails.setItems(items);


        lblTotalCost.setText(String.valueOf(TotalCost));
    }

}
