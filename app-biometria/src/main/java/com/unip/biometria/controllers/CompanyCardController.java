package com.unip.biometria.controllers;

	import com.unip.biometria.model.entities.Company;

import jakarta.validation.constraints.Pattern;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

	public class CompanyCardController {

	    @FXML
	    private Label CompanyCnpjLabel;

	    @FXML
	    private Label CompanyNameLabel;

	    @FXML
	    private VBox cardCompany;
	    
	    @Pattern(regexp = "(^\\d{2}.\\d{3}.\\d{3}/\\d{4}-\\d{2}$)")
	    private String cnpjrgx;
	    
	    public void setData(Company company) {
	    	CompanyNameLabel.setText(company.getName());
	    	CompanyCnpjLabel.setText(String.format(cnpjrgx ,company.getCnpj()));
	    }
	}