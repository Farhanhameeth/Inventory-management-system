package com.dev.pos.controller;

import com.dev.pos.Enum.BoType;
import com.dev.pos.bo.BoFactory;
import com.dev.pos.bo.custom.BatchBo;
import com.dev.pos.bo.custom.ProductBo;
import com.dev.pos.dto.BatchDto;
import com.dev.pos.dto.ProductDto;
import com.dev.pos.dto.tm.BatchTm;
import com.dev.pos.dto.tm.ProductTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

public class ProductMainFormController {
    public AnchorPane context;
    public TextField txtProductCode;
    public TextArea txtDescription;
    public Button btnSave;
    public TextField txtSearch;

    public TableView<ProductTm> tblProduct;
    public TableColumn<ProductTm, Integer> colProductID;
    public TableColumn<ProductTm, String> colDescription;
    public TableColumn<ProductTm, Button> colShowMore;
    public TableColumn<ProductTm, Button> colDelete;

    public TextField txtSelectedProductCode;
    public TextArea txtSelectedProductDescription;
    public Button btnNewBatch;

    public TableView<BatchTm> tblProductMain;
    public TableColumn<BatchTm, String> colCode;
    public TableColumn<BatchTm, Button> colQTY;
    public TableColumn<BatchTm, Double> colBuyingPrice;
    public TableColumn<BatchTm, Boolean> colDiscountAvailable;
    public TableColumn<BatchTm, Double> colShowPrice;
    public TableColumn<BatchTm, Double> colSellingPrice;
    public TableColumn<BatchTm, Button> colDeleteMain;
    public Button btnBacktoHome;

    ProductBo productBo = BoFactory.getInstance().getBo(BoType.PRODUCT);
    BatchBo batchBo = BoFactory.getInstance().getBo(BoType.BATCH);

    String searchText = "";
    public Button btnBackToDash;

    public void initialize() {
        generateProductID();

        colProductID.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colShowMore.setCellValueFactory(new PropertyValueFactory<>("showMoreBtn"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));

        colCode.setCellValueFactory(new PropertyValueFactory<>("id"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colBuyingPrice.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        colDiscountAvailable.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colShowPrice.setCellValueFactory(new PropertyValueFactory<>("showPrice"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colDeleteMain.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));

        loadAllProducts(searchText);

        tblProduct.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setData(newValue);
            }
        });

        tblProductMain.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                try {
                    loadExternalUI(true,newValue);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            loadAllProducts(newValue);
        });
    }

    public void AddNewProductOnAction(ActionEvent actionEvent) {
        clear();
        tblProductMain.getItems().clear();
        tblProduct.getSelectionModel().clearSelection();
        generateProductID();
        btnNewBatch.setDisable(true);
        btnSave.setText("Save Product");
    }

    public void SaveOnAction(ActionEvent actionEvent) {

        try {
            if (btnSave.getText().equalsIgnoreCase("save product")) {
                boolean isSaved = productBo.saveProduct(new ProductDto(
                        Integer.parseInt(txtProductCode.getText()),
                        txtDescription.getText()
                ));

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Product Saved Successfully").show();
                    loadAllProducts(searchText);
                    generateProductID();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save the product").show();
                }
            } else {
                boolean isUpdated = productBo.updateProduct(new ProductDto(
                        Integer.parseInt(txtProductCode.getText()),
                        txtDescription.getText()
                ));

                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Product Updated Successfully").show();
                    loadAllProducts(searchText);
                    generateProductID();
                    btnSave.setText("Save Product");
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update the product").show();
                }
            }
            clear();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addNewBatchOnAction(ActionEvent actionEvent) throws IOException {

        loadExternalUI(false, null);

    }

    public void setOpenedFromPlaceOrder(boolean openedFromPlaceOrder) {
        if (openedFromPlaceOrder) {
            btnBacktoHome.setVisible(false);
        }
    }

    public void btnBackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUI("DashboardForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.show();
        stage.centerOnScreen();
    }

    private void generateProductID() {
        try{
            txtProductCode.setText(String.valueOf(productBo.getLastProductId()));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllProducts(String searchText) {

        ObservableList<ProductTm> oblist = FXCollections.observableArrayList();

        try {
            for (ProductDto product : (txtSearch.getLength()>0? productBo.searchByDescription(searchText) : productBo.findAllProducts())) {
                Button showMore = new Button("Show More");
                Button delete = new Button("Delete");

                ProductTm productTm = new ProductTm(
                        product.getCode(),
                        product.getDescription(),
                        showMore,
                        delete
                );
                oblist.add(productTm);

                //delete button action
                delete.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you sure...?",
                            ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if (buttonType.get().equals(ButtonType.YES)) {
                        try {
                            if (productBo.deleteProduct(productTm.getCode())) {
                                new Alert(Alert.AlertType.INFORMATION, "Product Deleted Successfully...").show();
                                clear();
                                btnSave.setText("Save Product");
                                generateProductID();
                                loadAllProducts(searchText);
                            } else {
                                new Alert(Alert.AlertType.ERROR, "Product Delete Failure...!").show();
                            }
                        } catch (SQLIntegrityConstraintViolationException e) {
                            new Alert(Alert.AlertType.ERROR, "Product is in use...Cannot be Deleted...!").show();

                        } catch (SQLException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            tblProduct.setItems(oblist);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setData(ProductTm newValue) {

        btnNewBatch.setDisable(false);
        btnSave.setText("Update Product");
        txtProductCode.setText(String.valueOf(newValue.getCode()));
        txtDescription.setText(newValue.getDescription());

        txtSelectedProductCode.setText(String.valueOf(newValue.getCode()));
        txtSelectedProductDescription.setText(newValue.getDescription());

        loadAllProductBatch(newValue.getCode());
    }

    private void clear() {
        txtDescription.clear();
        txtSelectedProductCode.clear();
        txtSelectedProductDescription.clear();

    }

    public void loadAllProductBatch(int productCode) {

        ObservableList<BatchTm> oblist = FXCollections.observableArrayList();

        try {
            for (BatchDto batch : batchBo.findAllBatch(productCode)) {
                Button delete = new Button("Delete");

                BatchTm batchTm = new BatchTm(
                        batch.getCode(),
                        batch.getQtyOnHand(),
                        batch.getBuyingPrice(),
                        batch.isDiscount() ? "Yes" : "No",
                        batch.getShowPrice(),
                        batch.getSellingPrice(),
                        delete
                );
                oblist.add(batchTm);

                delete.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure...?",
                            ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)) {
                        try {
                            if (batchBo.deleteBatch(batchTm.getId())) {
                                new Alert(Alert.AlertType.INFORMATION, "Batch Deleted Successfully...").show();
                                loadAllProductBatch(productCode);
                            } else {
                                new Alert(Alert.AlertType.ERROR, "Batch Delete Failure...!").show();
                            }

                        } catch (SQLException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            tblProductMain.setItems(oblist);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadExternalUI(boolean state, BatchTm batchTm) throws IOException {

        if (!txtSelectedProductCode.getText().trim().isEmpty()) {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/NewBatchForm.fxml"));
            Parent parent = fxmlLoader.load();
            NewBatchFormController controller = fxmlLoader.getController();
            controller.setProduct(
                    Integer.parseInt(txtSelectedProductCode.getText()),
                    txtSelectedProductDescription.getText(),
                    state,
                    batchTm
                    );

            controller.setProductMainFormController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();

        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a valid Product.").show();
        }
    }
}
