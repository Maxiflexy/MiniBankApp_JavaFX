module com.maxiflexy.minibankapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.maxiflexy.minibankapp to javafx.fxml;
    opens com.maxiflexy.minibankapp.controller to javafx.fxml;
    exports com.maxiflexy.minibankapp;
}