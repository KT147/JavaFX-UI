package com.example.javafxui.toDoList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ToDoApp extends Application {

	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(ToDoApp.class.getResource("mainwindow.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 900, 500);
		stage.setTitle("Todo List");
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
