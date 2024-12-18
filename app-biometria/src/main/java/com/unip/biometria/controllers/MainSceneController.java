package com.unip.biometria.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.unip.biometria.dao.DaoCompany;
import com.unip.biometria.model.entities.Company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class MainSceneController {

	@FXML
	private Label agrotoxicLabel;

	@FXML
	private Label allowedQuantityLabel;

	@FXML
	private VBox companyBox;

	@FXML
	private Label companyCnpjlabel;

	@FXML
	private Label companyNameLabel;

	@FXML
	private ScrollPane companyScroll;

	@FXML
	private Label dateLabel;

	@FXML
	private Label dateOfPunishedLabel;

	@FXML
	private Label locationLabel;

	@FXML
	private ImageView profileIcon;

	@FXML
	private Label punhisedAmountLabel;

	@FXML
	private Label punishedTypeLabel;

	@FXML
	private Label quantityLabel;

	@FXML
	private Label reasonLabel;

	@FXML
	private ImageView sarchBtn;

	@FXML
	private TextField searchField;

	@FXML
	private Label userNameLabel;
	
	private ObservableList<Company> listCompanys(){
		
		DaoCompany daoCompany = new DaoCompany();
		
		return FXCollections.observableArrayList(daoCompany.findAll());
	}
	
	
	private void initialize(URL location, ResourceBundle resources){
		
	}
	
}






