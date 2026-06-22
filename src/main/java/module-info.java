module com.example.javafxui {
	requires javafx.controls;
	requires javafx.fxml;


	opens com.example.javafxui to javafx.fxml;
	exports com.example.javafxui;
}