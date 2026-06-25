package com.example.javafxui.toDoList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Controller {

	private List<TodoItem> todoItems;

	@FXML
	private ListView<TodoItem> todoListView;

	@FXML
	private TextArea itemDetailsTextArea;

	@FXML
	private Label deadlineLabel;

	@FXML
	private BorderPane mainBorderPane;

	@FXML
	private ContextMenu listContextMenu;

	@FXML
	private ToggleButton filterToggleButton;

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

		listContextMenu = new ContextMenu();
		MenuItem deleteMenuItem = new MenuItem("Delete");
		deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				TodoItem item = todoListView.getSelectionModel().getSelectedItem();
				deleteItem(item);
			}
		});

		listContextMenu.getItems().add(deleteMenuItem);

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

		SortedList<TodoItem> sortedList = new SortedList<>(ToDoData.getInstance().getTodoItems(), new Comparator<TodoItem>() {
			@Override
			public int compare(TodoItem o1, TodoItem o2) {
				return o1.getDeadline().compareTo(o2.getDeadline());
			}
		});

//		todoListView.setItems(ToDoData.getInstance().getTodoItems());
		todoListView.setItems(sortedList);
		todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		todoListView.getSelectionModel().selectFirst();

		todoListView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
			@Override
			public ListCell<TodoItem> call(ListView<TodoItem> todoItemListView) {
				ListCell<TodoItem> cell = new ListCell<>() {
					@Override
					protected void updateItem(TodoItem todoItem, boolean empty) {
						super.updateItem(todoItem, empty);
						if (empty) {
							setText(null);
						} else {
							setText(todoItem.getShortDescription());
							if (todoItem.getDeadline().isBefore(LocalDate.now())) {
								setTextFill(Color.RED);
							} else if (todoItem.getDeadline().equals(LocalDate.now().plusDays(1))) {
								setTextFill(Color.GREEN);
							}
						}
					}
				};

				cell.emptyProperty().addListener(
						(obs, wasEmpty, isNowEmpty) -> {
							if (isNowEmpty) {
								cell.setContextMenu(null);
							} else {
								cell.setContextMenu(listContextMenu);
							}
						}
				);

				return cell;
			}
		});
	}

	@FXML
	public void showNewItemDialog() {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.initOwner(mainBorderPane.getScene().getWindow());
		dialog.setTitle("Add New Todo Item");
		dialog.setHeaderText("Use this dialog to create a new todo item");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
		try {
			dialog.getDialogPane().setContent(fxmlLoader.load());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			DialogController controller = fxmlLoader.getController();
			TodoItem newItem = controller.processResults();
//			todoListView.getItems().setAll(ToDoData.getInstance().getTodoItems());
			todoListView.getSelectionModel().select(newItem);
			System.out.println("OK pressed");
		} else {
			System.out.println("Cancel pressed");
		}
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

	public void deleteItem(TodoItem item) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete Todo Item");
		alert.setHeaderText("Delete item: " + item.getShortDescription());
		alert.setContentText("Are you sure? Press OK to confirm, or cancel to back out");
		Optional<ButtonType> result = alert.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.OK) {
			ToDoData.getInstance().deleteTodoItem(item);
		}
	}

	@FXML
	public void handleKeyPressed(KeyEvent event) {
		TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			if (event.getCode().equals(KeyCode.DELETE) || event.getCode() == KeyCode.BACK_SPACE ) {
				deleteItem(selectedItem);
			}
		}
	}

	public void handleFilterButton() {
		if(filterToggleButton.isSelected()) {

		} else {

		}
	}
}
