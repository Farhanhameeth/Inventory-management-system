package com.dev.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class DashboardFormController {
    public AnchorPane context;

    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                 "Do you want to Logout...?"
                , ButtonType.NO, ButtonType.YES);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get() .equals(ButtonType.YES)) {
            setUI("LoginForm");
        }
    }
    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        setUI("CustomerForm");
    }
    public void btnProductManagementOnAction(ActionEvent actionEvent) throws IOException {
        setUI("ProductMainForm");
    }
    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        setUI("PlaceOrderForm");
    }
    public void btnOrderDetailsOnAction(ActionEvent actionEvent) throws IOException {
        setUI("OrdersForm");
    }
    public void btnIncomeReportOnAction(ActionEvent actionEvent) throws IOException {
        setUI("IncomeForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}
