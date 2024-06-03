package assignments.assignment4.page;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import assignments.assignment1.OrderGenerator;
import assignments.assignment3.DepeFood;
import assignments.assignment3.items.Menu;
import assignments.assignment3.items.Order;
import assignments.assignment3.items.Restaurant;
import assignments.assignment3.items.User;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;
import assignments.assignment3.payment.DepeFoodPaymentSystem;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.BillPrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import javafx.util.StringConverter;

public class CustomerMenu extends MemberMenu{
    private Stage stage;
    private Scene scene;
    private Scene addOrderScene;
    private Scene printBillScene;
    private Scene payBillScene;
    private Scene cekSaldoScene;
    private BillPrinter billPrinter; // Instance of BillPrinter
    private MainApp mainApp;
    private ObservableList<String> restaurantObvList = super.restaurantObvList; // Refer to the restaurantComboBox in MemberMenu
    private List<Restaurant> restoList = super.restoList; // Refer to the restoList in MemberMenu
    
    private List<String> orderMenu = new ArrayList<>(); // List of selected menu to be ordered

    ImageView creditCardIcon = new ImageView(new Image(getClass().getResourceAsStream("/Credit.png")));
    ImageView debitCardIcon = new ImageView(new Image(getClass().getResourceAsStream("/Debit.png")));

    public CustomerMenu(Stage stage, MainApp mainApp) {
        this.stage = stage;
        this.mainApp = mainApp;
        
        super.refresh(); // Refresh the page
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public Scene createBaseMenu() {        
        // ===== Parent =====
        VBox menuRoot = new VBox();
        
        menuRoot.setAlignment(Pos.CENTER);
        menuRoot.setStyle("-fx-background-color: #EEEEEE;");

        // ===== NavBar =====
        HBox navbar = new HBox();
        super.setPrefSize(navbar, 438.4, 71.2);

        navbar.setStyle("-fx-background-color: #2A2A2A;");
        navbar.setPadding(new Insets(16, 42, 16, 42));
        
        // Inside NavBar
        BorderPane navbarContent = new BorderPane();
        super.setPrefSize(navbarContent, 353.6, 39.2);
        
        // Right Content
        BorderPane navbarContentRight = new BorderPane();
        super.setPrefSize(navbarContentRight, 140.6, 39.2);

        BorderPane.setAlignment(navbarContentRight, Pos.CENTER);

        // Page Info
        Label pageInfo = new Label("Customer Page");
        pageInfo.setStyle("-fx-text-fill: #FFFFFF;");
        pageInfo.setFont(new Font("Poppins Bold", 12.0));
        BorderPane.setAlignment(pageInfo, Pos.CENTER);

        // Logout Button
        Button logoutButton = new Button();
        logoutButton.setOnAction(e -> {
            mainApp.logout();
        });
        logoutButton.setStyle("-fx-background-color: transparent;");
        logoutButton.setGraphic(super.logoutView);
        BorderPane.setAlignment(logoutButton, Pos.CENTER);

        navbarContentRight.setLeft(pageInfo);
        navbarContentRight.setRight(logoutButton);
        
        // Image and ImageView Assets
        Image logoIcon = new Image(getClass().getResourceAsStream("/Logo.png"));
        ImageView logoView = new ImageView(logoIcon);

        // Adding all Content to NavBar
        navbarContent.setLeft(logoView);
        navbarContent.setRight(navbarContentRight);

        // Adding Navbar Content to Navbar
        navbar.getChildren().add(navbarContent);

        // ====== Body ======
        VBox menuLayout = new VBox();
        menuLayout.setAlignment(Pos.TOP_CENTER);
        menuLayout.setPadding(new Insets(42, 42, 42, 42));

        // Title
        Label title = new Label(
            "Welcome to DepeFood, " + 
            DepeFood.getUserLoggedIn().getNama().split(" ")[0] + // Get the first name
            "!"
        );
        title.setFont(new Font("Poppins Bold", 17.0));
        title.setStyle("-fx-alignment: center;");
        title.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        // Box for Buttons
        VBox buttonBox = new VBox(18);
        super.setPrefSize(buttonBox, 353.6, 336.8);

        buttonBox.setAlignment(Pos.CENTER);

        // Button: Add Order
        Button addOrderButton = new Button("Create Order");
        super.setPrefSize(addOrderButton, 212.0, 42.4);
        
        addOrderButton.setFont(new Font("Poppins Bold", 13.0));
        addOrderButton.setAlignment(Pos.CENTER);
        addOrderButton.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        addOrderButton.mnemonicParsingProperty().set(false);
        addOrderButton.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 5em;");
        addOrderButton.setTextFill(javafx.scene.paint.Color.WHITE);

        addOrderButton.setOnAction(e -> {
            mainApp.setScene(addOrderScene);
        });

        // Button: Print Bill
        Button printBillButton = new Button("Print Bill");
        super.setPrefSize(printBillButton, 212.0, 42.4);

        printBillButton.setFont(new Font("Poppins Bold", 13.0));
        printBillButton.setAlignment(Pos.CENTER);
        printBillButton.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        printBillButton.mnemonicParsingProperty().set(false);
        printBillButton.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 5em;");
        printBillButton.setTextFill(javafx.scene.paint.Color.WHITE);

        printBillButton.setOnAction(e -> {
            mainApp.setScene(printBillScene);
        });

        // Button: Pay Order
        Button payOrderButton = new Button("Pay Order");
        super.setPrefSize(payOrderButton, 212.0, 42.4);

        payOrderButton.setFont(new Font("Poppins Bold", 13.0));
        payOrderButton.setAlignment(Pos.CENTER);
        payOrderButton.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        payOrderButton.mnemonicParsingProperty().set(false);
        payOrderButton.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 5em;");
        payOrderButton.setTextFill(javafx.scene.paint.Color.WHITE);

        payOrderButton.setOnAction(e -> {
            mainApp.setScene(payBillScene);
        });

        // Button: Check Balance
        Button checkBalanceButton = new Button("Check User Info");
        super.setPrefSize(checkBalanceButton, 212.0, 42.4);

        checkBalanceButton.setFont(new Font("Poppins Bold", 13.0));
        checkBalanceButton.setAlignment(Pos.CENTER);
        checkBalanceButton.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        checkBalanceButton.mnemonicParsingProperty().set(false);
        checkBalanceButton.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 5em;");
        checkBalanceButton.setTextFill(javafx.scene.paint.Color.WHITE);

        checkBalanceButton.setOnAction(e -> {
            mainApp.setScene(cekSaldoScene);
        });

        // Adding Buttons to Button Box
        buttonBox.getChildren().addAll(
            addOrderButton,
            printBillButton,
            payOrderButton,
            checkBalanceButton
        );

        // Adding all elements to menuLayout
        menuLayout.getChildren().addAll(
            title,
            super.createSpacer(10),
            buttonBox
        );

        // Adding all elements to menuRoot
        menuRoot.getChildren().addAll(navbar, menuLayout);

        return new Scene(menuRoot, menuRoot.getPrefWidth(), menuRoot.getPrefHeight());
    }

    private Scene createTambahPesananForm() {
        User user = DepeFood.getUserLoggedIn(); // Get the logged in user
        ListView<String> menuListView = new ListView<>(); // ListView for the menu
        ListView<String> orderListView = new ListView<>(); // ListView for the user's order

        // ===== Parent =====
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().add(createNavbar("Create Order", 700, 616));

        // ====== Menu Layout ======
        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setPadding(new Insets(42, 42, 42, 42));

        // ====== Choose Resto Label ======
        Label restoLabel = new Label("Select Restaurant");
        restoLabel.setFont(new Font("Poppins Bold", 16.0));
        restoLabel.setStyle("-fx-text-fill: #2A2A2A;");
        restoLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        restoLabel.setAlignment(Pos.CENTER);

        // ====== Restaurant ComboBox ======
        ComboBox<String> chooseComboBox = new ComboBox<String>();
        chooseComboBox.setItems(restaurantObvList);

        chooseComboBox.setPromptText("Choose Restaurant Name");
        super.setPrefSize(chooseComboBox, 269.0, 35.0);

        chooseComboBox.setVisibleRowCount(5);
        chooseComboBox.setStyle(
            "-fx-background-color: #e3e3e3; " +
            "-fx-background-radius: 2em; " +
            "-fx-border-color: transparent; " +
            "-fx-padding: 5px; "
        );

        chooseComboBox.setOnAction(e -> {
            String selectedResto = chooseComboBox.getValue();
            Restaurant resto = DepeFood.findRestaurant(selectedResto);

            if(resto != null){
                menuListView.getItems().clear();
                menuListView.getItems().addAll(
                    resto.getMenu().stream()
                        .map(menu -> menu.getNamaMakanan() + " - Rp" + menu.getHarga())
                        .collect(Collectors.toList())
                );
            } else {
                // Indicate that the restaurant is not found
                return;
            }

            menuListView.refresh(); // Refresh the ListView
        });

        // ====== Choose Date Label ======
        Label dateLabel = new Label("Select Date");
        dateLabel.setFont(new Font("Poppins Bold", 16.0));
        dateLabel.setStyle("-fx-text-fill: #2A2A2A;");
        dateLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        dateLabel.setAlignment(Pos.CENTER);
        
        // ====== Date Picker ======
        DatePicker datePicker = new DatePicker();
        super.setPrefSize(datePicker, 269.0, 35.0);
        
        // Set the date picker to DD/MM/YYYY format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return formatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatter);
                } else {
                    return null;
                }
            }
        };
        datePicker.setConverter(converter);
        
        // ====== Order Section ======
        HBox orderSection = new HBox(10);
        orderSection.setAlignment(Pos.CENTER);

        // ===== Menu Choose List =====
        VBox menuList = new VBox(10);
        menuList.setAlignment(Pos.CENTER);
        menuList.setPadding(new Insets(10, 10, 10, 10));
        
        // ====== Title Menu Label ======
        Label menuLabel = new Label("Choose Menu");
        menuLabel.setFont(new Font("Poppins Bold", 13.0));
        menuLabel.setStyle("-fx-text-fill: #2A2A2A;");
        menuLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        menuLabel.setAlignment(Pos.CENTER);

        // ====== Menu List ======
        menuListView.setPrefWidth(272.0);
        menuListView.setPrefHeight(300.0);
        menuListView.setStyle("-fx-background-color: #e3e3e3; -fx-background-radius: 15px;");

        menuListView.setCellFactory(fact -> {
            ListCell<String> cell = new ListCell<>();
        
            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty()) {
                    // Get the selected item
                    String selectedItem = cell.getItem();
        
                    // Add the selected item to the orderListView
                    if (selectedItem != null) {
                        orderListView.getItems().add(selectedItem);
                    }
                }
            });
        
            cell.itemProperty().addListener((obs, oldItem, newItem) -> {
                if (newItem != null) {
                    cell.setText(newItem);
                } else {
                    cell.setText(null);
                }
            });
        
            return cell;
        });

        // ====== User's Order List ======
        VBox orderList = new VBox(10);
        orderList.setAlignment(Pos.CENTER);
        orderList.setPadding(new Insets(10, 10, 10, 10));

        // ====== Title Order Label ======
        Label orderLabel = new Label("Your Order");
        orderLabel.setFont(new Font("Poppins Bold", 13.0));
        orderLabel.setStyle("-fx-text-fill: #2A2A2A;");
        orderLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        orderLabel.setAlignment(Pos.CENTER);

        // ====== Order List ======
        orderListView.setPrefWidth(272.0);
        orderListView.setPrefHeight(300.0);
        orderListView.setStyle("-fx-background-color: #e3e3e3; -fx-background-radius: 15px;");

        orderListView.setCellFactory(fact -> {
            ListCell<String> cell = new ListCell<>();
        
            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty()) {
                    // Get the item in the cell
                    String item = cell.getItem();
        
                    // Remove the item from the orderListView
                    if (item != null) {
                        orderListView.getItems().remove(item);
                    }
                }
            });
        
            cell.itemProperty().addListener((obs, oldItem, newItem) -> {
                if (newItem != null) {
                    cell.setText(newItem);
                } else {
                    cell.setText(null);
                }
            });
        
            return cell;
        });

        // ====== Order Button ======
        Button orderButton = new Button("Make Order");
        super.setPrefSize(orderButton, 150.0, 35.0);
        orderButton.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 5em;");
        orderButton.setTextFill(javafx.scene.paint.Color.WHITE);

        orderButton.setOnAction(e -> {
            String selectedResto = chooseComboBox.getValue();
            String selectedDate = null;

            try {
                selectedDate = datePicker.getValue().format(formatter);
            } catch (Exception e1) {
                selectedDate = null;
            }

            for (String item : orderListView.getItems()) {
                System.out.println(item);
                orderMenu.add(item);
            }

            if(selectedResto == null || selectedDate == null || orderMenu.size() == 0){
                mainApp.createAlert(
                    AlertType.ERROR,
                    "Error",
                    "Order Failed",
                    "Please fill all the required fields."
                ).showAndWait();
                return;
            }

            handleBuatPesanan(selectedResto, selectedDate, orderMenu);

            // Clear all the fields
            orderMenu.clear();
            chooseComboBox.getSelectionModel().clearSelection();
            datePicker.getEditor().clear();
            menuListView.getItems().clear();
            orderListView.getItems().clear();
        });

        // Adding all elements to menuList
        menuList.getChildren().addAll(
            menuLabel,
            menuListView
        );

        // Adding all elements to orderList
        orderList.getChildren().addAll(
            orderLabel,
            orderListView
        );

        orderSection.getChildren().addAll(menuList, orderList); // Add menuList and orderList to orderSection

        // Adding all elements to menuLayout
        menuLayout.getChildren().addAll(
            restoLabel,
            chooseComboBox,
            super.createSpacer(10),
            dateLabel,
            datePicker,
            super.createSpacer(10),
            orderSection,
            super.createSpacer(10),
            orderButton
        );

        layout.getChildren().add(menuLayout); // Add menuLayout to the layout

        return new Scene(layout, layout.getPrefWidth(), layout.getPrefHeight());
    }

    private Scene createBillPrinter(){
        User user = DepeFood.getUserLoggedIn();

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().add(createNavbar("Print Bill", 438.4, 353.6));

        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setPadding(new Insets(42, 42, 42, 42));

        // ====== Choose Order Title ======
        Label orderLabel = new Label("Choose Order");
        orderLabel.setFont(new Font("Poppins Bold", 16.0));
        orderLabel.setStyle("-fx-text-fill: #2A2A2A;");
        orderLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        orderLabel.setAlignment(Pos.CENTER);

        // ====== ComboBox of Orders ======
        ComboBox<String> orderComboBox = new ComboBox<String>();
        orderComboBox.setPromptText("Choose Order ID");
        super.setPrefSize(orderComboBox, 269.0, 35.0);

        orderComboBox.getItems().addAll(
            user.getOrderHistory().stream()
                .map(order -> order.getOrderId())
                .collect(Collectors.toList())
        );

        orderComboBox.setStyle(
            "-fx-background-color: #e3e3e3; " +
            "-fx-background-radius: 2em; " +
            "-fx-border-color: transparent; " +
            "-fx-padding: 5px; "
        );

        // ====== Print Button ======
        Button printButton = new Button("Print Bill");
        super.setPrefSize(printButton, 150.0, 35.0);
        printButton.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 5em;");
        printButton.setTextFill(javafx.scene.paint.Color.WHITE);

        printButton.setOnAction(e -> {
            String selectedOrder = orderComboBox.getValue();

            if(selectedOrder == null){
                mainApp.createAlert(
                    AlertType.ERROR,
                    "Error",
                    "Print Bill Failed",
                    "Please select an order to print the bill."
                ).showAndWait();
                return;
            }

            mainApp.setScene(billPrinter.getScene(selectedOrder));
        });

        menuLayout.getChildren().addAll(
            orderLabel,
            orderComboBox,
            super.createSpacer(10),
            printButton
        );

        layout.getChildren().add(menuLayout);

        return new Scene(layout, layout.getPrefWidth(), layout.getPrefHeight());
    }

    private Scene createBayarBillForm() {
        User user = DepeFood.getUserLoggedIn();

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().add(createNavbar("Pay Order", 438.4, 353.6));

        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setPadding(new Insets(42, 42, 42, 42));

        // ====== Choose Order Title ======
        Label orderLabel = new Label("Choose Order");
        orderLabel.setFont(new Font("Poppins Bold", 16.0));
        orderLabel.setStyle("-fx-text-fill: #2A2A2A;");
        orderLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        orderLabel.setAlignment(Pos.CENTER);

        // ====== ComboBox of Orders ======
        ComboBox<String> orderComboBox = new ComboBox<String>();
        orderComboBox.setPromptText("Choose Order ID");
        super.setPrefSize(orderComboBox, 269.0, 35.0);

        orderComboBox.getItems().addAll(
            user.getOrderHistory().stream()
                .map(order -> order.getOrderId())
                .collect(Collectors.toList())
        );

        orderComboBox.setStyle(
            "-fx-background-color: #e3e3e3; " +
            "-fx-background-radius: 2em; " +
            "-fx-border-color: transparent; " +
            "-fx-padding: 5px; "
        );

        // ====== Choose Payment Method Title ======
        Label paymentLabel = new Label("Choose Payment Method");
        paymentLabel.setFont(new Font("Poppins Bold", 16.0));
        paymentLabel.setStyle("-fx-text-fill: #2A2A2A;");
        paymentLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        paymentLabel.setAlignment(Pos.CENTER);

        // ====== ComboBox of Payment Method ======
        ComboBox<String> paymentComboBox = new ComboBox<String>();
        paymentComboBox.setPromptText("Choose Payment Method");
        super.setPrefSize(paymentComboBox, 269.0, 35.0);

        paymentComboBox.getItems().addAll(
            "Credit Card",
            "Debit Card"
        );

        paymentComboBox.setStyle(
            "-fx-background-color: #e3e3e3; " +
            "-fx-background-radius: 2em; " +
            "-fx-border-color: transparent; " +
            "-fx-padding: 5px; "
        );

        // ====== Pay Button ======
        Button payButton = new Button("Pay Bill");
        super.setPrefSize(payButton, 150.0, 35.0);
        payButton.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 5em;");
        payButton.setTextFill(javafx.scene.paint.Color.WHITE);

        payButton.setOnAction(e -> {
            String selectedOrder = orderComboBox.getValue();
            int selectedPayment = paymentComboBox.getSelectionModel().getSelectedIndex();

            if(selectedOrder == null || selectedPayment == -1){
                mainApp.createAlert(
                    AlertType.ERROR,
                    "Error",
                    "Payment Failed",
                    "Please fill all the required fields."
                ).showAndWait();
                return;
            }

            handleBayarBill(selectedOrder, selectedPayment);
        });

        menuLayout.getChildren().addAll(
            orderLabel,
            orderComboBox,
            super.createSpacer(10),
            paymentLabel,
            paymentComboBox,
            super.createSpacer(10),
            payButton
        );

        layout.getChildren().add(menuLayout);

        return new Scene(layout, layout.getPrefWidth(), layout.getPrefHeight());
    }


    private Scene createCekSaldoScene() {
        User user = DepeFood.getUserLoggedIn();
        
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().add(createNavbar("Check User Info", 438.4, 353.6));

        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setPadding(new Insets(42, 42, 42, 42));

        Label saldoLabel = new Label("Hello, " + user.getNama() + "!");
        saldoLabel.setFont(new Font("Poppins Bold", 16.0));
        saldoLabel.setStyle("-fx-text-fill: #2A2A2A;");
        saldoLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        saldoLabel.setAlignment(Pos.CENTER);

        HBox saldoBox = new HBox(12);
        saldoBox.setAlignment(Pos.CENTER);
        saldoBox.setPadding(new Insets(5, 5, 5, 5));

        VBox saldoInfo = new VBox(5);
        saldoInfo.setAlignment(Pos.CENTER_LEFT);
        saldoInfo.setPadding(new Insets(5, 5, 5, 5));

        Label saldo = new Label("Your current balance:");
        saldo.setFont(new Font("Poppins Bold", 13.0));
        saldo.setStyle("-fx-text-fill: #2A2A2A;");
        saldo.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        saldo.setAlignment(Pos.CENTER);

        Label saldoValue = new Label("Rp " + user.getSaldo());
        saldoValue.setFont(new Font("Poppins Bold", 13.0));
        saldoValue.setStyle("-fx-text-fill: #2A2A2A;");
        saldoValue.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        saldoValue.setAlignment(Pos.CENTER);

        saldoInfo.getChildren().addAll(saldo, saldoValue);
        saldoBox.getChildren().addAll(
            user.getPaymentSystem() instanceof CreditCardPayment ? creditCardIcon : debitCardIcon, // Show the icon based on the payment system
            saldoInfo
        );

        menuLayout.getChildren().addAll(
            saldoLabel,
            super.createSpacer(15),
            saldoBox
        );

        layout.getChildren().add(menuLayout);

        return new Scene(layout, layout.getPrefWidth(), layout.getPrefHeight());
    }

    private void handleBuatPesanan(String namaRestoran, String tanggalPemesanan, List<String> menuItems) {
        User user = DepeFood.getUserLoggedIn();
        Restaurant selectedResto = DepeFood.findRestaurant(namaRestoran);

        Menu[] selectedMenus = new Menu[menuItems.size()];

        int counter = 0;

        for (String menuItem : menuItems) {
            String namaMenu = "";

            for (int i = 0; i < menuItem.length(); i++) {
                if (menuItem.charAt(i) == '-') {
                    // Get the menu name until the '-' character
                    namaMenu = menuItem.substring(0, i - 1);
                    break;
                }
            }

            Menu selectedMenu = selectedResto.findMenu(namaMenu);
            
            selectedMenus[counter++] = selectedMenu;
        }

        Order order = new Order(
            OrderGenerator.generateOrderID(namaRestoran, tanggalPemesanan, user.getNomorTelepon()), 
            tanggalPemesanan,
            DepeFood.calculateOngkir(DepeFood.getUserLoggedIn().getLokasi()),
            selectedResto,
            selectedMenus
        );

        user.addOrderHistory(order);

        mainApp.createAlert(
            AlertType.INFORMATION,
            "Success",
            "Order Success",
            "Order dengan ID " + order.getOrderId() + " berhasil ditambahkan!"
        ).showAndWait();

        super.refresh();
    }

    private void handleBayarBill(String orderID, int pilihanPembayaran) {
        Order order = DepeFood.getOrderOrNull(orderID);
        User user = DepeFood.getUserLoggedIn();

        if(order.getOrderFinished()){
            mainApp.createAlert(
                AlertType.ERROR,
                "Error",
                "Payment Failed",
                "Order dengan ID " + orderID + " sudah dibayar!"
            ).showAndWait();
            return;
        }

        DepeFoodPaymentSystem userPayment = user.getPaymentSystem();
        
        if(pilihanPembayaran == 0){

            if(!(userPayment instanceof CreditCardPayment)){
                mainApp.createAlert(
                    AlertType.ERROR,
                    "Error",
                    "Payment Failed",
                    "The User don't have the selected type of payment."
                ).showAndWait();
                return;
            }

            double orderFee;
            try {
                orderFee = userPayment.processPayment(user.getSaldo(), order.getTotalHarga());
            } catch (Exception e) {
                System.out.println(e.getMessage());

                mainApp.createAlert(
                    AlertType.ERROR,
                    "Error",
                    "Payment Failed",
                    e.getMessage()
                ).showAndWait();
                return;
            }
            
            if(user.getSaldo() < orderFee){
                mainApp.createAlert(
                    AlertType.ERROR,
                    "Error",
                    "Payment Failed",
                    "Insufficient balance. Blud doesn't even have enough money to pay " + orderFee + " to eat lmao."
                ).showAndWait();
                return;
            }
            
            mainApp.createAlert(
                AlertType.INFORMATION,
                "Success",
                "Payment Success",
                "Berhasil Membayar Bill sebesar Rp " + 
                order.getTotalHarga() + 
                " dengan biaya transaksi sebesar Rp " + 
                ((CreditCardPayment) userPayment).getTransactionFeePercentage((long) order.getTotalHarga())
            ).showAndWait();
            
            user.setSaldo(user.getSaldo() - orderFee);

            order.setOrderFinished(true);
        } else if(pilihanPembayaran == 1){
            if(!(userPayment instanceof DebitPayment)){
                mainApp.createAlert(
                    AlertType.ERROR,
                    "Error",
                    "Payment Failed",
                    "The User don't have the selected type of payment."
                ).showAndWait();
                return;
            }

            long orderFee;
            String errorMessage = "";

            try {
                orderFee = (long) userPayment.processPayment(user.getSaldo(), (long) order.getTotalHarga());
            } catch (Exception e) {
                errorMessage = e.getMessage();
                mainApp.createAlert(
                    AlertType.ERROR,
                    "Error",
                    "Payment Failed",
                    errorMessage
                ).showAndWait();
                return;
            }

            mainApp.createAlert(
                AlertType.INFORMATION,
                "Success",
                "Payment Success",
                "Berhasil Membayar Bill sebesar Rp " + order.getTotalHarga()
            ).showAndWait();
            
            user.setSaldo(user.getSaldo() - orderFee);

            order.setOrderFinished(true);
        }

        super.refresh();
    }

    // Helper Function to create Navbar for CustomerPage feature
    @Override
    public HBox createNavbar(String title, double widthMain, double widthContent) {
        HBox navbar = new HBox();
        super.setPrefSize(navbar, widthMain, 71.2);

        navbar.setStyle("-fx-background-color: #2A2A2A;");
        navbar.setAlignment(Pos.CENTER);
        navbar.setPadding(new Insets(16, 42, 16, 42));
        
        // Inside NavBar
        BorderPane navbarContent = new BorderPane();
        super.setPrefSize(navbarContent, widthContent, 39.2);

        // Page Info
        Label pageInfo = new Label(title);
        pageInfo.setStyle("-fx-text-fill: #FFFFFF;");
        pageInfo.setFont(new Font("Poppins Bold", 12.0));
        pageInfo.setPadding(new Insets(0, 5, 0, 0));
        BorderPane.setAlignment(pageInfo, Pos.CENTER);

        // Logout Button
        Button backButton = new Button();
        ImageView backView = new ImageView(super.backIcon);
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setGraphic(backView);
        BorderPane.setAlignment(backButton, Pos.CENTER);
        
        backButton.setOnAction(e -> {
            mainApp.setScene(mainApp.getScene("CustomerPage"));
        });
        
        navbarContent.setLeft(backButton);
        navbarContent.setRight(pageInfo);

        navbar.getChildren().add(navbarContent);

        return navbar;
    }

    @Override
    protected void refreshPage() {
        this.scene = createBaseMenu();
        this.addOrderScene = createTambahPesananForm();
        this.billPrinter = new BillPrinter(stage, mainApp); // Pass user to BillPrinter constructor
        this.printBillScene = createBillPrinter();
        this.payBillScene = createBayarBillForm();
        this.cekSaldoScene = createCekSaldoScene();
    }
}