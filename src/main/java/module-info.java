module com.example.javafxui {
	requires javafx.controls;
	requires javafx.fxml;


	opens com.example.javafxui to javafx.fxml;
	opens com.example.javafxui.toDoList to javafx.fxml, javafx.graphics;
	exports com.example.javafxui;
	exports com.example.javafxui.toDoList;
}