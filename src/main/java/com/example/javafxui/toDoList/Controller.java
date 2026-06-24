package com.example.javafxui.toDoList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {

	private List<TodoItem> todoItems;

	@FXML
	private ListView<TodoItem> todoListView;

	@FXML
	private TextArea itemDetailsTextArea;

	@FXML
	private Label deadlineLabel;

	public void initialize() {
//		TodoItem item1 = new TodoItem("Mail birthday card", "Buy 30th birthday card", LocalDate.of(2026, Month.APRIL, 25));
//		TodoItem item2 = new TodoItem("Walk a dog", "Go to the park", LocalDate.of(2026, Month.SEPTEMBER, 1));
//		TodoItem item3 = new TodoItem("See doctor Smith", "Go to the clinic", LocalDate.of(2026, Month.JULY, 12));
//		TodoItem item4 = new TodoItem("Finish work", "Pass the deadline", LocalDate.of(2026, Month.AUGUST, 30));
//		TodoItem item5 = new TodoItem("Fix the car", "Go to the garage on Center street", LocalDate.of(2026, Month.OCTOBER, 9));
//		todoItems = new ArrayList<>();
//		todoItems.add(item1);
//		todoItems.add(item2);
//		todoItems.add(item3);
//		todoItems.add(item4);
//		todoItems.add(item5);
//
//		ToDoData.getInstance().setTodoItems(todoItems);

		todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItem>() {
			@Override
			public void changed(ObservableValue<? extends TodoItem> observableValue, TodoItem todoItem, TodoItem t1) {
				if (t1 != null) {
					TodoItem item = todoListView.getSelectionModel().getSelectedItem();
					itemDetailsTextArea.setText(item.getDetails());
					DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
					deadlineLabel.setText(df.format(item.getDeadline()));
				}
			}
		});

		todoListView.getItems().setAll(ToDoData.getInstance().getTodoItems());
		todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		todoListView.getSelectionModel().selectFirst();
	}

	@FXML
	public void handleClickListView() {
		TodoItem item = todoListView.getSelectionModel().getSelectedItem();
		itemDetailsTextArea.setText(item.getDetails());
		deadlineLabel.setText(item.getDeadline().toString());
//		StringBuilder sb = new StringBuilder(item.getDetails());
//		sb.append("\n\n\n\n");
//		sb.append("Due: ");
//		sb.append(item.getDeadline().toString());
//		itemDetailsTextArea.setText(sb.toString());

	}
}
