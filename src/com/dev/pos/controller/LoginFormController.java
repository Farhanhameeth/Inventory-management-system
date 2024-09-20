package com.dev.pos.controller;

import com.dev.pos.Enum.BoType;
import com.dev.pos.bo.BoFactory;
import com.dev.pos.bo.custom.UserBo;
import com.dev.pos.dto.UserDto;
import com.dev.pos.util.security.PasswordManager;
import com.dev.pos.util.security.UserSessionData;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public PasswordField txtPassword;

    UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    public void btnLoginOnAction(ActionEvent actionEvent) {

        try {
            UserDto user = userBo.findUser(txtEmail.getText());
            if (user != null) {
                if (PasswordManager.checkPassword(txtPassword.getText(), user.getPassword())) {
                    UserSessionData.userEmail = txtEmail.getText().trim();
                    setUI("DashboardForm");
                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid Email or Password...!").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "User Not Found...!").show();
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void btnCreateAnAccountOnAction(ActionEvent actionEvent) throws IOException {

        setUI("SignupForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

}
