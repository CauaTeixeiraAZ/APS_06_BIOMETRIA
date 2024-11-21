package com.unip.biometria.exceptions;

import javafx.scene.control.Label;

public class ParametersException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ParametersException(String msg, Label label) {
		label.setText(msg);
	}
}
