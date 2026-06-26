module com.example.javafxui {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.desktop;


	opens com.example.javafxui to javafx.fxml;
	opens com.example.javafxui.toDoList to javafx.fxml, javafx.graphics;
	opens com.example.javafxui.cssStyling to javafx.fxml, javafx.graphics;
	exports com.example.javafxui;
	exports com.example.javafxui.toDoList;
	exports com.example.javafxui.cssStyling;
}