package com.example.javafxui.toDoList;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;

public class DialogController {

	@FXML
	private TextField shortDescriptionField;
	@FXML
	private TextArea detailsArea;
	@FXML
	private DatePicker deadlinePicker;


	public TodoItem processResults() {
		String shortDescription = shortDescriptionField.getText().trim();
		String details = detailsArea.getText().trim();
		LocalDate deadlineValue = deadlinePicker.getValue();

		try {
			TodoItem newItem = new TodoItem(shortDescription, details, deadlineValue);
			ToDoData.getInstance().addTodoItem(newItem);
			return newItem;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
