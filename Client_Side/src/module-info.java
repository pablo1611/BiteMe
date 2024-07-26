module Client_Side {
	requires javafx.controls;
	requires javafx.fxml;
	requires Common;
	requires OCSF;
	opens application to javafx.graphics, javafx.fxml;
}
