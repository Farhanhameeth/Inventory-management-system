package com.dev.pos.controller;

import com.dev.pos.Enum.BoType;
import com.dev.pos.bo.BoFactory;
import com.dev.pos.bo.custom.UserBo;
import com.dev.pos.dto.UserDto;
import com.dev.pos.util.security.PasswordManager;
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

public class SignupFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public PasswordField txtPassword;

    UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    public void btnSignupOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        UserDto userDto = new UserDto(txtEmail.getText(), new PasswordManager().encrypt(txtPassword.getText()));
        boolean isSaved = userBo.saveUser(userDto);
        if (isSaved) {
            setUI("loginForm");
            new Alert(Alert.AlertType.INFORMATION, "User created...!").show();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "User not created...!").show();
        }
    }

    public void btnAlreadyHaveAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUI("loginForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}
