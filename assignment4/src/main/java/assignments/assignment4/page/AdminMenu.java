package assignments.assignment4.page;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import assignments.assignment3.DepeFood;
import assignments.assignment3.items.Restaurant;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.form.LoginForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AdminMenu extends MemberMenu{
    private Stage stage;
    private Scene scene;
    private Scene addRestaurantScene;
    private Scene addMenuScene;
    private Scene viewRestaurantsScene;
    private List<Restaurant> restoList = super.restoList;
    private MainApp mainApp; // Reference to MainApp instance
    private ObservableList<String> restaurantObvList = super.restaurantObvList; // Refer to the restaurantComboBox in MemberMenu
    private ListView<String> menuItemsListView = new ListView<>();

    public AdminMenu(Stage stage, MainApp mainApp) {
        this.stage = stage;
        this.mainApp = mainApp;
        
        super.refresh(); // Initiation of the page
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public Scene createBaseMenu() {
        // ===== Parent =====
        VBox menuRoot = new VBox();
        super.setPrefSize(menuRoot, 438.0, 515.0);
        
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
        super.setPrefSize(navbarContentRight, 125.6, 39.2);

        BorderPane.setAlignment(navbarContentRight, Pos.CENTER);

        // Page Info
        Label pageInfo = new Label("Admin Page");
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
        super.setPrefSize(menuLayout, 438.4, 444.4);

        menuLayout.setPadding(new Insets(42, 42, 42, 42));

        // Title
        Label title = new Label(
            "Welcome to DepeFood, " + DepeFood.getUserLoggedIn().getNama() + "!"
        );
        title.setFont(new Font("Poppins Bold", 17.0));
        title.setStyle("-fx-alignment: center;");
        title.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        // Box for Buttons
        VBox buttonBox = new VBox(20);
        super.setPrefSize(buttonBox, 353.6, 336.8);

        buttonBox.setAlignment(Pos.CENTER);
        
        // Add Restaurant Button
        Button addRestaurantButton = new Button("Add Restaurant");
        super.setPrefSize(addRestaurantButton, 212.0, 42.4);
        
        addRestaurantButton.setFont(new Font("Poppins Bold", 13.0));
        addRestaurantButton.setAlignment(Pos.CENTER);
        addRestaurantButton.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        addRestaurantButton.mnemonicParsingProperty().set(false);
        addRestaurantButton.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 5em;");
        addRestaurantButton.setTextFill(javafx.scene.paint.Color.WHITE);

        addRestaurantButton.setOnAction(e -> {
            mainApp.setScene(addRestaurantScene);
        });

        // Add Menu Button
        Button addMenuButton = new Button("Add Restaurant's Menu");
        super.setPrefSize(addMenuButton, 212.0, 42.4);
        
        addMenuButton.setFont(new Font("Poppins Bold", 13.0));
        addMenuButton.setAlignment(Pos.CENTER);
        addMenuButton.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        addMenuButton.mnemonicParsingProperty().set(false);
        addMenuButton.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 5em;");
        addMenuButton.setTextFill(javafx.scene.paint.Color.WHITE);

        addMenuButton.setOnAction(e -> {
            if(restoList.isEmpty()){
                mainApp.createAlert(
                    Alert.AlertType.ERROR,
                    "Error", 
                    "Empty Database",
                    "No restaurant has been added yet! Please add a restaurant first."
                ).showAndWait();
                return;
            }
            mainApp.setScene(addMenuScene);
        });

        // View Restaurants Button
        Button viewRestaurantsButton = new Button("View Restaurants");
        super.setPrefSize(viewRestaurantsButton, 212.0, 42.4);
        
        viewRestaurantsButton.setFont(new Font("Poppins Bold", 13.0));
        viewRestaurantsButton.setAlignment(Pos.CENTER);
        viewRestaurantsButton.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        viewRestaurantsButton.mnemonicParsingProperty().set(false);
        viewRestaurantsButton.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 5em;");
        viewRestaurantsButton.setTextFill(javafx.scene.paint.Color.WHITE);
        
        viewRestaurantsButton.setOnAction(e -> {
            if(restoList.isEmpty()){
                mainApp.createAlert(
                    Alert.AlertType.ERROR,
                    "Error", 
                    "Empty Database",
                    "No restaurant has been added yet! Please add a restaurant first."
                ).showAndWait();
                return;
            }
            mainApp.setScene(viewRestaurantsScene);
        });

        buttonBox.getChildren().addAll(addRestaurantButton, addMenuButton, viewRestaurantsButton); // Add all buttons to buttonBox

        // Adding all elements to menuLayout
        menuLayout.getChildren().addAll(title, buttonBox);

        // Adding all elements to menuRoot
        menuRoot.getChildren().addAll(navbar, menuLayout);

        return new Scene(menuRoot, menuRoot.getPrefWidth(), menuRoot.getPrefHeight());
    }

    private Scene createAddRestaurantForm() {
        VBox layout = new VBox();

        layout.getChildren().add(createNavbar("Add Restaurant", 438.4, 353.6));

        // Form
        VBox form = new VBox(30);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(70, 50, 70, 50));

        Label nameLabel = new Label("Input Restaurant Name");
        nameLabel.setFont(new Font("Poppins Bold", 16.0));
        nameLabel.setStyle("-fx-text-fill: #2A2A2A;");
        nameLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        nameLabel.setAlignment(Pos.CENTER);

        // ===== Restaurant Name Input Box =====
        HBox addRestoBox = new HBox();
        addRestoBox.setPadding(new Insets(5, 5, 5, 5));
        addRestoBox.setStyle("-fx-background-color: #e3e3e3; -fx-background-radius: 15px;");
        addRestoBox.setAlignment(Pos.CENTER);
        
        TextField restoName = new TextField();
        super.setPrefSize(restoName, 320.0, 36.0);

        restoName.promptTextProperty().set("Masukkan nama restoran");
        restoName.setFont(new Font(null, 16.0));
        restoName.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        addRestoBox.getChildren().add(restoName);

        // ===== Restaurant Button =====
        Button addRestoButton = new Button("Add Restaurant");
        super.setPrefSize(addRestoButton, 212.0, 42.4);

        addRestoButton.setFont(new Font("Poppins Bold", 13.0));
        addRestoButton.setAlignment(Pos.CENTER);
        addRestoButton.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        addRestoButton.mnemonicParsingProperty().set(false);
        addRestoButton.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 5em;");
        addRestoButton.setTextFill(javafx.scene.paint.Color.WHITE);
        
        addRestoButton.setOnAction(e -> {
            if(restoName.getText().isEmpty()){
                mainApp.createAlert(
                    Alert.AlertType.ERROR,
                    "Error", 
                    "Add Restaurant Failed",
                    "Restaurant name cannot be empty!"
                ).showAndWait();
                return;
            }

            handleTambahRestoran(restoName.getText());
            restoName.clear();
        });

        // Spacer
        Pane spacer = new Pane();
        spacer.setMinHeight(2);

        form.getChildren().addAll(nameLabel, addRestoBox, spacer, addRestoButton);

        layout.getChildren().add(form);

        return new Scene(layout, layout.getPrefWidth(), layout.getPrefHeight());
    }

    private Scene createAddMenuForm() {
        VBox layout = new VBox();

        layout.getChildren().add(createNavbar("Add Restaurant's Menu", 438.4, 353.6));

        VBox fillForm = new VBox(15);
        fillForm.setAlignment(Pos.CENTER);
        fillForm.setPadding(new Insets(70, 50, 70, 50));

        // ===== Choose Restaurant Label =====
        Label chooseLabel = new Label("Choose Restaurant");
        chooseLabel.setFont(new Font("Poppins Bold", 15.0));
        chooseLabel.setStyle("-fx-text-fill: #2A2A2A;");
        chooseLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        chooseLabel.setAlignment(Pos.CENTER);

        // ===== Restaurant ComboBox =====
        ComboBox<String> addMenuComboBox = new ComboBox<String>();
        addMenuComboBox.setItems(restaurantObvList);

        addMenuComboBox.setPromptText("Choose Restaurant Name");
        super.setPrefSize(addMenuComboBox, 269.0, 35.0);

        addMenuComboBox.setVisibleRowCount(5);
        addMenuComboBox.setStyle(
            "-fx-background-color: #e3e3e3; " +
            "-fx-background-radius: 2em; " +
            "-fx-border-color: transparent; " +
            "-fx-padding: 5px; "
        );

        // ===== Menu Name Label =====
        Label menuNameLabel = new Label("Input Menu Name");
        menuNameLabel.setFont(new Font("Poppins Bold", 15.0));
        menuNameLabel.setStyle("-fx-text-fill: #2A2A2A;");
        menuNameLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        menuNameLabel.setAlignment(Pos.CENTER);

        // ===== Menu Name Input Box =====
        HBox addMenuBox = new HBox();
        addMenuBox.setPadding(new Insets(5, 5, 5, 5));
        addMenuBox.setStyle("-fx-background-color: #e3e3e3; -fx-background-radius: 15px;");
        addMenuBox.setAlignment(Pos.CENTER);

        TextField menuName = new TextField();
        super.setPrefSize(menuName, 320.0, 36.0);

        menuName.promptTextProperty().set("Masukkan nama menu");
        menuName.setFont(new Font(null, 16.0));
        menuName.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        addMenuBox.getChildren().add(menuName);

        // ===== Menu Price Label =====
        Label menuPriceLabel = new Label("Input Menu Price");
        menuPriceLabel.setFont(new Font("Poppins Bold", 15.0));
        menuPriceLabel.setStyle("-fx-text-fill: #2A2A2A;");
        menuPriceLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        menuPriceLabel.setAlignment(Pos.CENTER);

        // ===== Menu Price Input Box =====
        HBox addPriceBox = new HBox();
        addPriceBox.setPadding(new Insets(5, 5, 5, 5));
        addPriceBox.setStyle("-fx-background-color: #e3e3e3; -fx-background-radius: 15px;");
        addPriceBox.setAlignment(Pos.CENTER);

        TextField menuPrice = new TextField();
        super.setPrefSize(menuPrice, 320.0, 36.0);

        menuPrice.promptTextProperty().set("Masukkan harga menu");
        menuPrice.setFont(new Font(null, 16.0));
        menuPrice.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        addPriceBox.getChildren().add(menuPrice);

        // ===== Add Menu Button =====
        Button addMenuButton = new Button("Add Menu");
        super.setPrefSize(addMenuButton, 212.0, 42.4);

        addMenuButton.setFont(new Font("Poppins Bold", 13.0));
        addMenuButton.setAlignment(Pos.CENTER);
        addMenuButton.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        addMenuButton.mnemonicParsingProperty().set(false);
        addMenuButton.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 5em;");
        addMenuButton.setTextFill(javafx.scene.paint.Color.WHITE);

        addMenuButton.setOnAction(e -> {
            String selectedRestaurant = addMenuComboBox.getValue();
            Restaurant restaurant = DepeFood.getRestaurantByName(selectedRestaurant);
            if (restaurant == null) {
                mainApp.createAlert(
                    Alert.AlertType.ERROR,
                    "Error", 
                    "Add Menu Failed",
                    "Restaurant not found! Please choose a valid restaurant."
                ).showAndWait();
                return;
            }
            
            handleTambahMenuRestoran(restaurant, menuName.getText(), menuPrice.getText());

            menuName.clear();
            menuPrice.clear();
            
            addMenuComboBox.getSelectionModel().select(selectedRestaurant);
        });

        fillForm.getChildren().addAll(
            chooseLabel, addMenuComboBox, super.createSpacer(10), menuNameLabel,
            addMenuBox, super.createSpacer(10), menuPriceLabel, addPriceBox,
            super.createSpacer(10), super.createSpacer(10), addMenuButton
        );

        layout.getChildren().addAll(fillForm);
    
        return new Scene(layout, layout.getPrefWidth(), layout.getPrefHeight());
    }
    
    
    private Scene createViewRestaurantsForm() {
        VBox layout = new VBox();

        layout.getChildren().add(createNavbar("View Restaurants", 438.4, 353.6));

        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(70, 50, 70, 50));

        // ===== Choose Restaurant Label =====
        Label chooseLabel = new Label("Choose Restaurant");
        chooseLabel.setFont(new Font("Poppins Bold", 15.0));
        chooseLabel.setStyle("-fx-text-fill: #2A2A2A;");
        chooseLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        chooseLabel.setAlignment(Pos.CENTER);

        // ===== Restaurant ComboBox =====
        ComboBox<String> viewMenuComboBox = new ComboBox<String>();
        viewMenuComboBox.setItems(restaurantObvList);

        viewMenuComboBox.setPromptText("Choose Restaurant Name");
        super.setPrefSize(viewMenuComboBox, 269.0, 35.0);

        viewMenuComboBox.setVisibleRowCount(5);
        viewMenuComboBox.setStyle(
            "-fx-background-color: #e3e3e3; " +
            "-fx-background-radius: 2em; " +
            "-fx-border-color: transparent; " +
            "-fx-padding: 5px; "
        );

        // ===== Restaurant List Label =====
        Label restaurantListLabel = new Label("List of Menus");
        restaurantListLabel.setFont(new Font("Poppins Bold", 12.0));
        restaurantListLabel.setStyle("-fx-text-fill: #2A2A2A;");
        restaurantListLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        restaurantListLabel.setAlignment(Pos.CENTER);

        // ===== Menu Items ListView =====
        this.menuItemsListView.setPrefWidth(272.0);
        this.menuItemsListView.setPrefHeight(300.0);
        this.menuItemsListView.setStyle("-fx-background-color: #e3e3e3; -fx-background-radius: 15px;");
        this.menuItemsListView.setPlaceholder(new Label("No menu available"));
        
        // ===== View Menu Button =====
        Button viewMenuButton = new Button("View Menu");
        super.setPrefSize(viewMenuButton, 212.0, 42.4);

        viewMenuButton.setFont(new Font("Poppins Bold", 13.0));
        viewMenuButton.setAlignment(Pos.CENTER);
        viewMenuButton.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        viewMenuButton.mnemonicParsingProperty().set(false);
        viewMenuButton.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 5em;");
        viewMenuButton.setTextFill(javafx.scene.paint.Color.WHITE);

        viewMenuButton.setOnAction(e -> {
            String selectedRestaurant = viewMenuComboBox.getValue();
            Restaurant restaurant = DepeFood.getRestaurantByName(selectedRestaurant);
            if (restaurant == null) {
                mainApp.createAlert(
                    Alert.AlertType.ERROR,
                    "Error", 
                    "View Menu Failed",
                    "Restaurant not found! Please choose a valid restaurant."
                ).showAndWait();
                return;
            }

            List<String> menuNames = new ArrayList<String>();

            restaurant.getMenu().forEach(menu -> {
                menuNames.add(
                    menu.getNamaMakanan() + 
                    " - Rp" + 
                    new DecimalFormat("0.#").format(menu.getHarga())
                );
            });

            this.menuItemsListView.setItems(FXCollections.observableArrayList(menuNames));
        });

        content.getChildren().addAll(
            chooseLabel, viewMenuComboBox, super.createSpacer(10), restaurantListLabel,
            menuItemsListView, super.createSpacer(10), viewMenuButton
        );

        layout.getChildren().add(content);
    
        return new Scene(layout, layout.getPrefWidth(), layout.getPrefHeight());
    }
    
    private void handleTambahRestoran(String nama) {
        // Validate restaurant name
        String validName = DepeFood.getValidRestaurantName(nama);

        if (!validName.equals(nama)){
            mainApp.createAlert(
                Alert.AlertType.ERROR, 
                "Error", 
                "Add Restaurant Failed",
                validName
            ).showAndWait();
            return;
        }

        DepeFood.handleTambahRestoran(nama); // Add restaurant to the system kalau valid

        super.refresh(); // Refresh the restaurant list

        mainApp.createAlert(
            Alert.AlertType.INFORMATION, 
            "Success", 
            "Add Restaurant Success",
            "Restaurant " + nama + " has been added to the system!"
        ).showAndWait();
    }

    private void handleTambahMenuRestoran(Restaurant restaurant, String itemName, String price) {
        // Validate price
        double validPrice;
        try {
            validPrice = Double.parseDouble(price);
            if (validPrice < 0) {
                mainApp.createAlert(
                    Alert.AlertType.ERROR, 
                    "Error", 
                    "Add Menu Failed",
                    "Price must be a positive number!"
                ).showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(price);
            mainApp.createAlert(
                Alert.AlertType.ERROR, 
                "Error", 
                "Add Menu Failed",
                "Price must be a number!"
            ).showAndWait();
            return;
        }

        // Add menu to the restaurant yang dituju
        DepeFood.handleTambahMenuRestoran(restaurant, itemName, validPrice);
        
        mainApp.createAlert(
            Alert.AlertType.INFORMATION, 
            "Success", 
            "Add Menu Success",
            "Menu " + itemName + " has been added to " + restaurant.getNama() + "!"
            ).showAndWait();
            
        super.refresh(); // Refresh the restaurant list
    }

    // Helper Function to create Navbar for AdminMenu feature
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
            mainApp.setScene(mainApp.getScene("AdminPage"));
        });
        
        navbarContent.setLeft(backButton);
        navbarContent.setRight(pageInfo);

        navbar.getChildren().add(navbarContent);

        return navbar;
    }

    @Override
    protected void refreshPage() {
        this.scene = createBaseMenu();
        this.addRestaurantScene = createAddRestaurantForm();
        this.addMenuScene = createAddMenuForm();
        this.viewRestaurantsScene = createViewRestaurantsForm();
    }
}
