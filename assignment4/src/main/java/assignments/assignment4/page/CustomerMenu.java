package assignments.assignment4.page;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
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

public class CustomerMenu extends MemberMenu{
    private Stage stage;
    private Scene scene;
    private Scene addOrderScene;
    private Scene printBillScene;
    private Scene payBillScene;
    private Scene cekSaldoScene;
    private BillPrinter billPrinter; // Instance of BillPrinter
    private MainApp mainApp;
    private ComboBox<String> restaurantComboBox = super.restaurantComboBox; // Refer to the restaurantComboBox in MemberMenu
    private List<Restaurant> restoList = super.restoList; // Refer to the restoList in MemberMenu
    
    private List<Menu> orderMenu = new ArrayList<>(); // List of selected menu to be ordered

    ImageView creditCardIcon = new ImageView(new Image(getClass().getResourceAsStream("/Credit.png")));
    ImageView debitCardIcon = new ImageView(new Image(getClass().getResourceAsStream("/Debit.png")));

    public CustomerMenu(Stage stage, MainApp mainApp) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.scene = createBaseMenu();
        this.addOrderScene = createTambahPesananForm();
        this.billPrinter = new BillPrinter(stage, mainApp); // Pass user to BillPrinter constructor
        this.printBillScene = createBillPrinter();
        this.payBillScene = createBayarBillForm();
        this.cekSaldoScene = createCekSaldoScene();
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public Scene createBaseMenu() {
        super.refresh();
        
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
        
        // Adding all Content to NavBar
        navbarContent.setLeft(super.logoView);
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

        // ===== Parent =====
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().add(createNavbar("Create Order"));

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
        chooseComboBox.itemsProperty().bind(restaurantComboBox.itemsProperty());

        chooseComboBox.setPromptText("Choose Restaurant Name");
        super.setPrefSize(chooseComboBox, 269.0, 35.0);

        chooseComboBox.setVisibleRowCount(5);
        chooseComboBox.setStyle(
            "-fx-background-color: #e3e3e3; " +
            "-fx-background-radius: 2em; " +
            "-fx-border-color: transparent; " +
            "-fx-padding: 5px; "
        );

        // ====== Choose Date Label ======
        Label dateLabel = new Label("Select Date");
        dateLabel.setFont(new Font("Poppins Bold", 16.0));
        dateLabel.setStyle("-fx-text-fill: #2A2A2A;");
        dateLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        dateLabel.setAlignment(Pos.CENTER);
        
        // ====== Date Picker ======
        DatePicker datePicker = new DatePicker();
        super.setPrefSize(datePicker, 269.0, 35.0);

        // ====== Choose Menu Label ======
        Label menuLabel = new Label("Select Menu");
        menuLabel.setFont(new Font("Poppins Bold", 16.0));
        menuLabel.setStyle("-fx-text-fill: #2A2A2A;");
        menuLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        menuLabel.setAlignment(Pos.CENTER);

        // ====== Menu CheckBox ======
        HBox menuBox = new HBox(10);
        menuBox.setAlignment(Pos.CENTER);

        VBox menuList = new VBox(10);
        menuList.setAlignment(Pos.CENTER);
        menuList.setPadding(new Insets(10, 10, 10, 10));

        // ====== Menu ListView ======
        ListView<String> menuListView = new ListView<>();
        menuListView.setPrefSize(269.0, 150.0);
        menuListView.setStyle(
            "-fx-background-color: #e3e3e3; " +
            "-fx-background-radius: 2em; " +
            "-fx-border-color: transparent; " +
            "-fx-padding: 5px; "
        );
        
    
        return new Scene(layout, layout.getPrefWidth(), layout.getPrefHeight());
    }

    private Scene createBillPrinter(){
        User user = DepeFood.getUserLoggedIn();

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().add(createNavbar("Print Bill"));

        VBox menuLayout = new VBox(10);

        return new Scene(layout, layout.getPrefWidth(), layout.getPrefHeight());
    }

    private Scene createBayarBillForm() {
        User user = DepeFood.getUserLoggedIn();

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().add(createNavbar("Pay Order"));

        VBox menuLayout = new VBox(10);

        return new Scene(layout, layout.getPrefWidth(), layout.getPrefHeight());
    }


    private Scene createCekSaldoScene() {
        User user = DepeFood.getUserLoggedIn();
        
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().add(createNavbar("Check User Info"));

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
            String namaMenu = menuItem.split(" ")[0];
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

            long orderFee;
            try {
                orderFee = (long) userPayment.processPayment(user.getSaldo(), (long) order.getTotalHarga());
            } catch (Exception e) {
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
                    "Insufficient balance. Blud doesn't even have enough money to pay" + orderFee + " to eat lmao."
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
    public HBox createNavbar(String title) {
        HBox navbar = new HBox();
        super.setPrefSize(navbar, 438.4, 71.2);

        navbar.setStyle("-fx-background-color: #2A2A2A;");
        navbar.setAlignment(Pos.CENTER);
        navbar.setPadding(new Insets(16, 42, 16, 42));
        
        // Inside NavBar
        BorderPane navbarContent = new BorderPane();
        super.setPrefSize(navbarContent, 353.6, 39.2);

        // Page Info
        Label pageInfo = new Label(title);
        pageInfo.setStyle("-fx-text-fill: #FFFFFF;");
        pageInfo.setFont(new Font("Poppins Bold", 12.0));
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
}