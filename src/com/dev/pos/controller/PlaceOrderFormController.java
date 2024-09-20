package com.dev.pos.controller;

import com.dev.pos.Enum.BoType;
import com.dev.pos.bo.BoFactory;
import com.dev.pos.bo.custom.BatchBo;
import com.dev.pos.bo.custom.CustomerBo;
import com.dev.pos.bo.custom.OrderDetailBo;
import com.dev.pos.dto.CustomerDto;
import com.dev.pos.dto.ItemDetailDto;
import com.dev.pos.dto.OrderDetailDto;
import com.dev.pos.dto.ProductDetailsJoinDto;
import com.dev.pos.dto.tm.CartTm;
import com.dev.pos.util.security.UserSessionData;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class PlaceOrderFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public RadioButton rdnManualMode;
    public ToggleGroup qrMode;
    public RadioButton rdnAutoMode;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtSalary;
    public Label lblMembership;
    public TextField txtBarcode;
    public TextField txtDescription;
    public TextField txtSellingPrice;
    public TextField txtDiscount;
    public TextField txtShowPrice;
    public TextField txtBuyingPrice;
    public TextField txtQTYOnHand;
    public Hyperlink hyprLoyaltyDetails;

    public TableView<CartTm> tblOrder;
    public TableColumn<CartTm, String> colBarcode;
    public TableColumn<CartTm, String> colDescription;
    public TableColumn<CartTm, Double> colSellingPrice;
    public TableColumn<CartTm, Double> colDiscount;
    public TableColumn<CartTm, Double> colShowPrice;
    public TableColumn<CartTm, Double> colTotal;
    public TableColumn<CartTm, Integer> colQTY;
    public TableColumn<CartTm, Button> colDelete;

    public Label lblTotalCost;
    public TextField txtQTY;

    FXMLLoader loader = null;

    CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    BatchBo batchBo = BoFactory.getInstance().getBo(BoType.BATCH);
    OrderDetailBo orderDetailBo = BoFactory.getInstance().getBo(BoType.ORDER_DETAIL);

    ObservableList<CartTm> oblist = FXCollections.observableArrayList();

    public void initialize(){

        colBarcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colShowPrice.setCellValueFactory(new PropertyValueFactory<>("showPrice"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("button"));
    }

    public void btnCompleteOrder(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        ArrayList<ItemDetailDto> itemDetailDtoList = new ArrayList<>();

        double discount = 0;
        for (CartTm cartTm : oblist) {
            itemDetailDtoList.add(new ItemDetailDto(
                    cartTm.getBarcode(),
                    cartTm.getQty(),
                    cartTm.getDiscount(),
                    cartTm.getTotal()
            ));
            discount += cartTm.getDiscount();
        }

        OrderDetailDto orderDetailDto = new OrderDetailDto(
                new Random().nextInt(1000001),
                new Date(),
                Double.parseDouble(lblTotalCost.getText().split("/=")[0]),
                txtEmail.getText(),
                0,
                UserSessionData.userEmail,
                itemDetailDtoList
        );

        if (orderDetailBo.placeOrder(orderDetailDto)) {
            new Alert(Alert.AlertType.INFORMATION, "Order Placed Successfully").showAndWait();
            clearAllFields();
            oblist.clear();
            txtEmail.requestFocus();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to place the order").show();
        }
    }

    public void btnBackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUI("DashboardForm", false);
    }

    public void btnAddNewCustomer(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        setUI("CustomerForm", true);
        CustomerFormController controller = loader.getController();
        controller.setOpenedFromPlaceOrder(true);
    }

    public void btnAddNewProduct(ActionEvent actionEvent) throws IOException {
        setUI("ProductMainForm", true);
        ProductMainFormController controller = loader.getController();
        controller.setOpenedFromPlaceOrder(true);
    }

    public void searchCustomerOnAction(ActionEvent actionEvent) {

        try {

            CustomerDto customerDto = customerBo.findCustomer(txtEmail.getText());

            if (customerDto != null) {
                txtName.setText(customerDto.getName());
                txtContact.setText(customerDto.getContact());
                txtSalary.setText(String.valueOf(customerDto.getSalary()));
                fetchLoyaltyCardDetails(txtEmail.getText());

                txtBarcode.requestFocus();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer Not Found").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void fetchLoyaltyCardDetails(String email) {

        hyprLoyaltyDetails.setVisible(true);
        hyprLoyaltyDetails.setText("+ New Loyalty");
    }

    private void setUI(String location, boolean state) throws IOException {

        Stage stage = null;
        loader = new FXMLLoader(getClass().getResource("../view/"+location+".fxml"));
        Scene scene = new Scene(loader.load());

        if (state) {
            stage = new Stage();
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } else {
            stage = (Stage) context.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        }
    }

    public void loadProductOnAction(ActionEvent actionEvent) {

        ProductDetailsJoinDto joinDto;
        try {
            joinDto = batchBo.findProductDetailsJoin(txtBarcode.getText().trim());

            if (joinDto != null) {
                txtDescription.setText(joinDto.getDescription());
                txtDiscount.setText(String.valueOf(0));
                txtSellingPrice.setText(String.valueOf(joinDto.getBatchDto().getSellingPrice()));
                txtShowPrice.setText(String.valueOf(joinDto.getBatchDto().getShowPrice()));
                txtBuyingPrice.setText(String.valueOf(joinDto.getBatchDto().getBuyingPrice()));
                txtQTYOnHand.setText(String.valueOf(joinDto.getBatchDto().getQtyOnHand()));
                txtQTY.requestFocus();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Product Not Found").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addToCartOnAction(ActionEvent actionEvent) {

        int qty = Integer.parseInt(txtQTY.getText());
        double sellingPrice = Double.parseDouble(txtSellingPrice.getText());
        double total = qty * sellingPrice;


        CartTm selectedItem = isItemExist(txtBarcode.getText());

        if (selectedItem == null) {

            Button button =  new Button("Remove");

            if (Integer.parseInt(txtQTYOnHand.getText()) >= qty ) {

                CartTm cartTm = new CartTm(
                        txtBarcode.getText(),
                        txtDescription.getText(),
                        sellingPrice,
                        Double.parseDouble(txtDiscount.getText()),
                        Double.parseDouble(txtShowPrice.getText()),
                        qty,
                        total,
                        button
                );

                oblist.add(cartTm);
                tblOrder.setItems(oblist);
                tblOrder.refresh();
                clearFields();
                txtBarcode.requestFocus();
                setTotal();

                button.setOnAction(event -> {
                    oblist.remove(cartTm);
                    tblOrder.refresh();
                    setTotal();
                    txtBarcode.requestFocus();
                });

            } else {
                new Alert(Alert.AlertType.ERROR, "Not enough stock").show();
            }

        } else {
            double newQuantity = selectedItem.getQty() + qty;

            if (Integer.parseInt(txtQTYOnHand.getText()) >= newQuantity) {
                selectedItem.setQty(qty + selectedItem.getQty());
                selectedItem.setTotal(total + selectedItem.getTotal());
                tblOrder.refresh();
                clearFields();
                txtBarcode.requestFocus();
                setTotal();
            } else {
                new Alert(Alert.AlertType.ERROR, "Not enough stock").show();
            }
        }
    }

    private CartTm isItemExist(String barcode) {
        for (CartTm cartTm : oblist) {
            if (cartTm.getBarcode().equals(barcode)) {
                return cartTm;
            }
        }
        return null;
    }

    private void clearFields() {
        txtBarcode.clear();
        txtDescription.clear();
        txtSellingPrice.clear();
        txtDiscount.clear();
        txtShowPrice.clear();
        txtBuyingPrice.clear();
        txtQTYOnHand.clear();
        txtQTY.clear();
    }

    private void setTotal () {
        double total = 0.0;
        for (CartTm cartTm : oblist) {
            total += cartTm.getTotal();
        }
        lblTotalCost.setText(total + "/=");
    }

    private void clearAllFields(){
        txtEmail.clear();
        txtName.clear();
        txtContact.clear();
        txtSalary.clear();

        clearFields();
        tblOrder.getItems().clear();
        tblOrder.refresh();
        lblTotalCost.setText("0.0/=");

    }
}
