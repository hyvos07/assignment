package assignments.assignment4.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import assignments.assignment1.OrderGenerator;
import assignments.assignment3.DepeFood;
import assignments.assignment3.items.Menu;
import assignments.assignment3.items.Order;
import assignments.assignment3.items.User;
import assignments.assignment4.MainApp;

public class BillPrinter {
    private Stage stage;
    private MainApp mainApp;

    public BillPrinter(Stage stage, MainApp mainApp) {
        this.stage = stage;
        this.mainApp = mainApp;
    }

    private Scene createBillPrinterForm(){
        VBox layout = new VBox(10);

        return new Scene(layout, layout.getPrefWidth(), layout.getPrefHeight());
    }

    public String printBill(String orderId) {
        User user = DepeFood.getUserLoggedIn();

        String bill = OrderGenerator.generateBill(orderId, user.getLokasi());

        return bill;
    }

    public Scene getScene() {
        return this.createBillPrinterForm();
    }
}
