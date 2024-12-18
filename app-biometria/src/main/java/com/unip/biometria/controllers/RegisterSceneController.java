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
import javafx.stage.Stage;

public class RegisterSceneController {

	@FXML
	private Button confirmBtn;

	@FXML
	private PasswordField confirmPassField;

	@FXML
	private Label emailErrorLabel;

	@FXML
	private Label errorLabel;

	@FXML
	private TextField emailField;

	@FXML
	private TextField nameField;

	@FXML
	private Label passwordErrorLabel;

	@FXML
	private PasswordField passwordField;

	@FXML
	private TextField telephoneField;

	private DaoUsers daoUser = new DaoUsers();

	@FXML
	private void registerUser(ActionEvent event) {
		try {			
			Users user = daoUser.find(emailField.getText());
			if (user == null) {
				throw new ParametersException("Email já cadastrado!", emailErrorLabel);
			}
			if (passwordField.getText() != confirmPassField.getText()) {
				throw new ParametersException("As senhas precisam ser iguais", passwordErrorLabel);
			}
			
			daoUser.save(new Users(emailField.getText(), passwordField.getText()));
			
			registerDigital(event);
		}catch(Exception e) {
			throw new ParametersException("Erro ao salvar dados", errorLabel);
		}

	}

	@FXML
	private void registerDigital(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DigitalScene.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);

			Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Registrar Digital");

			DigitalSceneController parametro = loader.getController();
			parametro.setParametro(2);

			stage.show();

		} catch (IOException e) {
			errorLabel.setText("Erro ao carregar nova Tela");
			e.printStackTrace();
		}

	}

}
