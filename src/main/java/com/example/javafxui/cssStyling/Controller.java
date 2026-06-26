package com.example.javafxui.cssStyling;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Controller {

	@FXML
	private Label label;

	@FXML
	private Button button4;

	@FXML
	private GridPane gridPane;

	public void initialize() {
		button4.setEffect(new DropShadow());
	}

	@FXML
	public void handleMouseEnter(){
		label.setScaleX(2.0);
		label.setScaleY(2.0);
	}

	@FXML
	public void handleMouseExit(){
		label.setScaleX(1.0);
		label.setScaleY(1.0);
	}

	@FXML
	public void handleClick() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Save Application File");
		chooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Zip", "*.zip"),
				new FileChooser.ExtensionFilter("PDF", "*.pdf"),
				new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png"),
				new FileChooser.ExtensionFilter("All Files", "*.*")
		);
//		DirectoryChooser chooser = new DirectoryChooser();
		File file = chooser.showOpenDialog(gridPane.getScene().getWindow());
		if (file != null) {
			System.out.println(file.getPath());
		} else {
			System.out.println("Cancelled");
		}
	}

	@FXML
	public void handleLinkClick() {
		try {
			Desktop.getDesktop().browse(new URI("https://www.javafx.com"));
		} catch (IOException | URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
