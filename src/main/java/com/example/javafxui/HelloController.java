package com.example.javafxui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {

	@FXML
	private TextField nameField;

	public void onButtonClicked() {
		System.out.println("Hello, " + nameField.getText());
	}
}
