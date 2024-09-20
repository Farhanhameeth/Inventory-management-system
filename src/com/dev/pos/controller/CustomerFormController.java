package com.dev.pos.controller;

import com.dev.pos.Enum.BoType;
import com.dev.pos.bo.BoFactory;
import com.dev.pos.bo.custom.CustomerBo;
import com.dev.pos.dto.CustomerDto;
import com.dev.pos.dto.tm.CustomerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtSalary;
    public TextField txtSearch;
    public Button btnSaveCustomer;
    public TableView<CustomerTm> tblCustomer;
    public TableColumn<CustomerTm, Integer> colNo;
    public TableColumn<CustomerTm, String> colEmail;
    public TableColumn<CustomerTm, String> colName;
    public TableColumn<CustomerTm, String> colContact;
    public TableColumn<CustomerTm, Double> colSalary;
    public TableColumn<CustomerTm, Button> colDelete;

    public Button btnBackToDash;
    String searchText = "";

    CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);

    public void initialize() {
        colNo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("button"));
        loadCustomer(searchText);

        //Listener for search
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            loadCustomer(newValue);
        });

        //Listener for table
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setData(newValue);
            }
        });
    }

    private void setData(CustomerTm newValue) {
        txtEmail.setEditable(false);
        btnSaveCustomer.setText("Update Customer");

        txtEmail.setText(newValue.getEmail());
        txtName.setText(newValue.getName());
        txtContact.setText(newValue.getContact());
        txtSalary.setText(String.valueOf(newValue.getSalary()));
    }

    public void setOpenedFromPlaceOrder(boolean openedFromPlaceOrder) {
        if (openedFromPlaceOrder) {
            btnBackToDash.setVisible(false);
        }
    }

    public void btnBackToDashboardOnAction(ActionEvent actionEvent) throws IOException {
        setUI("DashboardForm");
    }

    public void btnSaveCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        CustomerDto customerDto = new CustomerDto(
                txtEmail.getText(),
                txtName.getText(),
                txtContact.getText(),
                Double.parseDouble(txtSalary.getText())
        );

        if (btnSaveCustomer.getText().equalsIgnoreCase("Save Customer")) {

            if (customerBo.findCustomer(txtEmail.getText()) != null) {
                new Alert(Alert.AlertType.ERROR, "Customer already exists...!").show();

            } else {

                boolean isSaved = customerBo.saveCustomer(customerDto);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer created...!").show();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Customer not created...!").show();
                }
            }

        } else {

            if (customerBo.updateCustomer(customerDto)) {
                new Alert(Alert.AlertType.INFORMATION, "Customer updated...!").show();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer not updated...!").show();
            }
            txtEmail.setEditable(true);
            btnSaveCustomer.setText("Save Customer");
        }
        clear();
        loadCustomer(searchText);
    }

    public void setUI(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    private void clear(){
        txtEmail.clear();
        txtName.clear();
        txtContact.clear();
        txtSalary.clear();
    }

    private void loadCustomer(String searchText) {
        ObservableList<CustomerTm> oblist = FXCollections.observableArrayList();

        try {
            int count = 1;
            for (CustomerDto customer : (txtSearch.getLength()>0? customerBo.searchCustomer(searchText) : customerBo.findAllCustomers())) {
                Button button = new Button("Delete");
                oblist.add( new CustomerTm(
                        count,
                        customer.getEmail(),
                        customer.getName(),
                        customer.getContact(),
                        customer.getSalary(),
                        button
                ));
                count++;
                tblCustomer.setItems(oblist);
                button.setOnAction(actionEvent -> {
                    try {
                        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.INFORMATION, "Are You Sure...?", ButtonType.YES, ButtonType.NO).showAndWait();
                        if (buttonType.get().equals(ButtonType.YES)) {
                            boolean isDeleted = customerBo.deleteCustomer(customer.getEmail());
                            if (isDeleted) {
                                new Alert(Alert.AlertType.INFORMATION, "Customer deleted...!").show();
                                loadCustomer(searchText);
                            } else {
                                new Alert(Alert.AlertType.INFORMATION, "Customer not deleted...!").show();
                            }
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
