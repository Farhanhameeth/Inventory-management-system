package com.dev.pos.controller;

import com.dev.pos.Enum.BoType;
import com.dev.pos.bo.BoFactory;
import com.dev.pos.bo.custom.ItemDetailBo;
import com.dev.pos.dto.ItemDto;
import com.dev.pos.dto.tm.IncomeTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class IncomeFormController {
    public AnchorPane context;
    public DatePicker txtDate;
    public TableView<IncomeTm> tblReport;
    public TableColumn<IncomeTm, Integer> colOrderCode;
    public TableColumn<IncomeTm, Integer> colProductCode;
    public TableColumn<IncomeTm, String> colProductName;
    public TableColumn<IncomeTm, Integer> colQty;
    public TableColumn<IncomeTm, Double> colTotal;
    public Label lblTotalIncome;

    ItemDetailBo itemDetailBo = BoFactory.getInstance().getBo(BoType.ITEM_DETAIL);

    public void initialize() {
        colOrderCode.setCellValueFactory(new PropertyValueFactory<>("orderCode"));
        colProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

    }

    public void btnBackOnAction(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/DashBoardForm.fxml"))));
    }

    private void loadAllIncome() throws Exception {
        ObservableList<IncomeTm> incomeTms = FXCollections.observableArrayList();

        double NetIncome = 0.0;

        for (ItemDto itemDto : itemDetailBo.findOrderByDate(txtDate.getValue())) {
            incomeTms.add(new IncomeTm(
                    itemDto.getOrderCode(),
                    itemDto.getProductCode(),
                    itemDto.getProductName(),
                    itemDto.getQty(),
                    itemDto.getTotal()));
            NetIncome += itemDto.getTotal();
        }
        lblTotalIncome.setText(NetIncome + "/=");
        tblReport.setItems(incomeTms);
    }

    public void dateOnAction(ActionEvent actionEvent) throws Exception {
        loadAllIncome();
    }
}
