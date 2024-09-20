package com.dev.pos.controller;

import com.dev.pos.Enum.BoType;
import com.dev.pos.bo.BoFactory;
import com.dev.pos.bo.custom.BatchBo;
import com.dev.pos.dto.BatchDto;
import com.dev.pos.dto.tm.BatchTm;
import com.dev.pos.util.security.QR.QRDataGenerator;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.codec.binary.Base64;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class NewBatchFormController {
    public AnchorPane context;
    public ImageView imgQR;
    public TextField txtQTY;
    public TextField txtBuyingPrice;
    public TextField txtSellingPrice;
    public TextField txtShowPrice;
    public ToggleGroup Discount;
    public TextField txtProductCode;
    public TextArea txtDescription;
    public Button btnSaveBatch;
    public RadioButton rdYes;
    public RadioButton rdNo;

    String uniqueData = null;
    BufferedImage bufferedImage = null;

    BatchBo batchBo = BoFactory.getInstance().getBo(BoType.BATCH);

    private ProductMainFormController productMainFormController;

    public void setProductMainFormController(ProductMainFormController controller) {
        this.productMainFormController = controller;
    }

    public void initialize() {

        try {
            setQRCode();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void saveBatchOnAction(ActionEvent actionEvent) {

            try {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                javax.imageio.ImageIO.write(bufferedImage, "png", baos);
                byte[] arr = baos.toByteArray();
                String barcode = Base64.encodeBase64String(arr);
                if (btnSaveBatch.getText().equalsIgnoreCase("Save Batch")) {

                    boolean isSaved = batchBo.saveBatch(new BatchDto(
                            uniqueData,
                            barcode,
                            Integer.parseInt(txtQTY.getText()),
                            Double.parseDouble(txtSellingPrice.getText()),
                            rdYes.isSelected() ? true : false,
                            Double.parseDouble(txtShowPrice.getText()),
                            Double.parseDouble(txtBuyingPrice.getText()),
                            Integer.parseInt(txtProductCode.getText())
                    ));

                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Batch Saved Successfully").show();
                        Thread.sleep(3000);
                        Stage stage = (Stage) context.getScene().getWindow();
                        stage.close();
                        productMainFormController.loadAllProductBatch(Integer.parseInt(txtProductCode.getText()));
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Something went wrong,Try Again...").show();
                    }

                } else {
                    try {
                        boolean isUpdated = batchBo.updateBatch(new BatchDto(
                                uniqueData,
                                barcode,
                                Integer.parseInt(txtQTY.getText()),
                                Double.parseDouble(txtSellingPrice.getText()),
                                rdYes.isSelected() ? true : false,
                                Double.parseDouble(txtShowPrice.getText()),
                                Double.parseDouble(txtBuyingPrice.getText()),
                                Integer.parseInt(txtProductCode.getText())
                        ));


                        if (isUpdated) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Batch Updated Successfully").show();
                            Thread.sleep(3000);
                            Stage stage = (Stage) context.getScene().getWindow();
                            stage.close();
                            productMainFormController.loadAllProductBatch(Integer.parseInt(txtProductCode.getText()));
                            btnSaveBatch.setText("Save Batch");
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Something went wrong,Try Again...").show();
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            } catch (IOException | SQLException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
    }

    private void setQRCode() throws WriterException {

        uniqueData = QRDataGenerator.generate(30);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        bufferedImage = MatrixToImageWriter.toBufferedImage(
                qrCodeWriter.encode(
                        uniqueData,
                        BarcodeFormat.QR_CODE,
                        200,
                        190
                )
        );

        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        imgQR.setImage(image);
    }

    public void setProduct(int code, String description, boolean state, BatchTm batchTm) {

        try {
            if (state) {

                BatchDto dto = batchBo.findBatch(batchTm.getId());

                if (dto!=null) {
                    uniqueData = batchTm.getId();
                    txtQTY.setText(String.valueOf(batchTm.getQty()));
                    txtBuyingPrice.setText(String.valueOf(batchTm.getBuyingPrice()));
                    txtSellingPrice.setText(String.valueOf(batchTm.getSellingPrice()));
                    txtShowPrice.setText(String.valueOf(batchTm.getShowPrice()));
                    Discount.selectToggle(batchTm.isDiscount().equals("Yes") ? rdYes : rdNo);
                    txtProductCode.setText(String.valueOf(code));
                    txtDescription.setText(description);

                    byte[] data = Base64.decodeBase64(dto.getBarcode());
                    imgQR.setImage(new Image(new ByteArrayInputStream(data)));

                    btnSaveBatch.setText("Update Batch");
                } else {
                }
            } else {
                txtProductCode.setText(String.valueOf(code));
                txtDescription.setText(description);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
