package com.unip.biometria.controllers;

import java.io.IOException;

import com.unip.biometria.dao.DaoUsers;
import com.unip.biometria.exceptions.ParametersException;
import com.unip.biometria.model.entities.Users;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LoginSceneController {

    @FXML
    private Button confirmBtn;

    @FXML
    private TextField emailField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label loginErrorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private HBox registerBtn;
	private DaoUsers daoUser = new DaoUsers();

	@FXML
	private void verify(ActionEvent event) {

		try {
			Users user = daoUser.find(emailField.getText());
			if (user == null || user.getSenha() != passwordField.getText()) {
				throw new ParametersException("Email ou senha invalido!", errorLabel);
			} 

				FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DigitalScene.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);

				Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
				stage.setScene(scene);
				stage.setTitle("Verificar Digital");
				
				DigitalSceneController parametro = loader.getController();
				parametro.setParametro(1);
				
				stage.show();
				
		} catch (IOException e) {
			errorLabel.setText("Erro ao carregar nova Tela");
			e.printStackTrace();
		}

	}

	@FXML
	private void register(MouseEvent event) {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/views/RegisterScene.fxml"));
			Scene scene = new Scene(root);

			Stage stage = (Stage) ((HBox) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			errorLabel.setText("Erro ao carregar nova Tela");
			e.printStackTrace();
		}

	}

}
