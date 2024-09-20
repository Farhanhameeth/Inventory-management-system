package com.dev.pos.controller;

import com.dev.pos.Enum.BoType;
import com.dev.pos.bo.BoFactory;
import com.dev.pos.bo.custom.OrderDetailBo;
import com.dev.pos.dto.OrderDto;
import com.dev.pos.dto.tm.OrdersTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class OrdersFormController {
    public AnchorPane context;
    public TextField txtSearchHere;
    public TableView<OrdersTm> tblOrders;
    public TableColumn<OrdersTm, Integer> colOrderCode;
    public TableColumn<OrdersTm, String> colCustomerName;
    public TableColumn<OrdersTm, Date> colIssuedDate;
    public TableColumn<OrdersTm, Double> colDiscount;
    public TableColumn<OrdersTm, Double> colTotalCost;
    public TableColumn<OrdersTm, Button> colShowMore;
    public TableColumn<OrdersTm, String> colUserName;

    OrderDetailBo orderDetailBo = BoFactory.getInstance().getBo(BoType.ORDER_DETAIL);
    String searchText = "";

    public void initialize() throws SQLException, ClassNotFoundException {
        colOrderCode.setCellValueFactory(new PropertyValueFactory<>("orderCode"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colIssuedDate.setCellValueFactory(new PropertyValueFactory<>("issuedDate"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colShowMore.setCellValueFactory(new PropertyValueFactory<>("showMore"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));

        loadOrders(searchText);

        txtSearchHere.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadOrders(newValue);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void btnBackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/DashboardForm.fxml"))));
        stage.centerOnScreen();
    }

    public void searchOrdersOnAction(ActionEvent actionEvent) {
    }

    private void loadOrders(String searchText) throws SQLException, ClassNotFoundException {
        ObservableList<OrdersTm> ordersList = FXCollections.observableArrayList();

        for (OrderDto orderDto : (txtSearchHere.getLength()>0? orderDetailBo.searchOrders(Integer.parseInt(searchText)) : orderDetailBo.findAllOrders())) {

            Button btn = new Button("Show More");

            ordersList.add(new OrdersTm(
                    orderDto.getOrderCode(),
                    orderDto.getCustomerName(),
                    orderDto.getIssuedDate(),
                    orderDto.getDiscount(),
                    orderDto.getTotalCost(),
                    btn,
                    orderDto.getUserName()
            ));

            btn.setOnAction(event -> {

                Stage stage = new Stage();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/OrderDetailsForm.fxml"));
                    stage.setScene(new Scene(loader.load()));
                    stage.show();
                    stage.centerOnScreen();

                    OrderDetailsFormController controller = loader.getController();
                    controller.setOrderCode(orderDto.getOrderCode());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        tblOrders.setItems(ordersList);
    }
}
