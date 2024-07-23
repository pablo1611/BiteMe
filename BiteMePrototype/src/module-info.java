module BiteMePrototype {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
	requires OCSF; 
    exports BMgui;
    opens BMgui to javafx.fxml;
    exports BMclient;
    exports BMserver;
    exports BMlogic;
}