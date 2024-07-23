module Client_Side {
	requires javafx.controls;
	requires javafx.fxml;
	requires OCSF;
	opens application to javafx.graphics, javafx.fxml;
}
