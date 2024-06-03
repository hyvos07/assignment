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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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

    private Scene createBillPrinterForm(String orderId){
        User user = DepeFood.getUserLoggedIn();

        // Find the order by orderId on the user's orderHistory to make sure the order is the order that belong to the user
        Order order = user.getOrderHistory().stream().filter(o -> 
            o.getOrderId().equals(orderId)
        ).findFirst().orElse(null);

        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #EEEEEE;");
        layout.setAlignment(Pos.CENTER);

        // ===== NavBar =====
        HBox navbar = new HBox();
        navbar.setAlignment(Pos.CENTER);
        navbar.setPrefHeight(71.2);
        navbar.setPrefWidth(438.4);
        navbar.setMaxHeight(71.2);
        navbar.setMaxWidth(438.4);
        navbar.setMinHeight(71.2);
        navbar.setMinWidth(438.4);

        navbar.setStyle("-fx-background-color: #2A2A2A;");
        navbar.setPadding(new Insets(16, 42, 16, 42));

        Label title = new Label("Your Bill");
        title.setStyle("-fx-text-fill: #FFFFFF;");
        title.setFont(new Font("Poppins Bold", 14.0));
        navbar.getChildren().add(title);

        VBox billLayout = new VBox(20);
        billLayout.setPadding(new Insets(10, 10, 30, 10));
        billLayout.setAlignment(Pos.CENTER);

        Label bill = new Label(printBill(orderId));
        bill.setStyle("-fx-font-size: 13px; -fx-font-family: 'Poppins';");
        bill.setPadding(new Insets(10, 10, 20, 10));

        Button backButton = new Button("Back");
        backButton.setPrefHeight(35.0);
        backButton.setPrefWidth(150.0);
        backButton.setMaxHeight(35.0);
        backButton.setMaxWidth(150.0);
        backButton.setMinHeight(35.0);
        backButton.setMinWidth(150.0);

        backButton.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 5em;");
        backButton.setTextFill(javafx.scene.paint.Color.WHITE);
        backButton.setFont(new Font("Poppins", 12.0));

        backButton.setOnAction(e -> {
            mainApp.setScene(mainApp.getScene("CustomerPage"));
        });

        billLayout.getChildren().addAll(bill, backButton);

        layout.getChildren().addAll(navbar, billLayout);

        return new Scene(layout, layout.getPrefWidth(), layout.getPrefHeight());
    }

    public String printBill(String orderId) {
        User user = DepeFood.getUserLoggedIn();

        // Find the order by orderId on the user's orderHistory to make sure the order is the order that belong to the user
        Order order = user.getOrderHistory().stream().filter(o -> 
            o.getOrderId().equals(orderId)
        ).findFirst().orElse(null);

        // Since the order is always exist, we can safely generate the bill

        String bill = OrderGenerator.generateBill(orderId, user.getLokasi()).replaceFirst("Bill:\\n", "");

        bill += "\nStatus: " + (order.getOrderFinished() ? "Finished" : "Not Finished") + "\n";

        bill += "\nPesanan: \n";

        for (Menu item : order.getItems()) {
            bill += item.getNamaMakanan() + " - " + item.getHarga() + "\n";
        }

        bill += "Ongkos Kirim: Rp" + order.getOngkir() + "\n";
        bill += "Total: Rp" + order.getTotalHarga();

        System.out.println(bill);

        return bill;
    }

    public Scene getScene(String orderID) {
        return this.createBillPrinterForm(orderID);
    }
}
