package assignments.assignment4.components.form;

import assignments.assignment3.DepeFood;
import assignments.assignment3.items.User;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import assignments.assignment4.MainApp;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.page.CustomerMenu;

import java.io.IOException;
import java.util.function.Consumer;

public class LoginForm {
    private Stage stage;
    private MainApp mainApp; // MainApp instance
    private TextField nameInput;
    private TextField phoneInput;

    Image banner = new Image(getClass().getResourceAsStream("/Platter.png"));

    public LoginForm(Stage stage, MainApp mainApp) { // Pass MainApp instance to constructor
        this.stage = stage;
        this.mainApp = mainApp; // Store MainApp instance
    }

    private Scene createLoginForm() throws IOException {
        // ====== Parent ======
        HBox rootBox = new HBox();
        rootBox.setAlignment(Pos.CENTER);

        // ====== Login Side ======
        VBox loginSection = new VBox();
        loginSection.setAlignment(Pos.CENTER);
        loginSection.prefHeight(626.0);
        loginSection.prefWidth(563.0);
        loginSection.setMinHeight(626.0);
        loginSection.setMaxHeight(626.0);
        loginSection.setMinWidth(563.0);
        loginSection.setMaxWidth(563.0);
        loginSection.setStyle("-fx-background-color: #2A2A2A;");
        
        // ====== Login Widget ======
        VBox loginPane = new VBox();
        loginPane.setAlignment(Pos.TOP_CENTER);
        loginPane.setPrefHeight(438.0);
        loginPane.setPrefWidth(412.0);
        loginPane.setMinHeight(438.0);
        loginPane.setMaxHeight(438.0);
        loginPane.setMinWidth(412.0);
        loginPane.setMaxWidth(412.0);
        VBox.setMargin(loginPane, new Insets(0, 20, 0, 20));
        loginPane.setPadding(new Insets(50, 50, 50, 50));
        loginPane.setStyle("-fx-background-color: FFFFFF; -fx-background-radius: 20px;");

        // ====== Welcome Label ======
        Label title = new Label("Welcome!");
        title.setPrefHeight(20.0);
        title.setPrefWidth(254.0);
        title.setAlignment(Pos.CENTER);
        title.setFont(new Font("Poppins Bold", 23.0));

        // Blank Space Pane
        Pane spacerInput = createSpacer(24.0, 311.0);
        
        // ====== Name Form Labels ======
        Label titleNama = new Label("Nama");
        titleNama.setPrefHeight(15.0);
        titleNama.setPrefWidth(385.0);
        titleNama.setFont(new Font("Poppins SemiBold", 18.0));
        VBox.setMargin(titleNama, new Insets(0, 0, 7, 0));

        // ====== Phone Number Form Labels ======
        Label titleTelp = new Label("No. Telepon");
        titleTelp.setPrefHeight(15.0);
        titleTelp.setPrefWidth(339.0);
        titleTelp.setFont(new Font("Poppins SemiBold", 18.0));
        VBox.setMargin(titleTelp, new Insets(25, 0, 7, 0));

        // Blank Space Pane
        Pane spacerButton = createSpacer(46.0, 311.0);

        // ====== Name Input Text Field ======
        HBox nameInputBox = new HBox();
        nameInputBox.setPadding(new Insets(5, 5, 5, 5));
        nameInputBox.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 15px;");
        nameInputBox.setAlignment(Pos.CENTER);
        
        nameInput = new TextField();
        nameInput.prefHeight(34.0);
        nameInput.prefWidth(290.0);
        nameInput.setMinHeight(34.0);
        nameInput.setMaxHeight(34.0);
        nameInput.setMinWidth(290.0);
        nameInput.setMaxWidth(290.0);
        nameInput.promptTextProperty().set("Masukkan nama Anda");
        nameInput.setFont(new Font(null, 16.0));
        nameInput.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        nameInputBox.getChildren().add(nameInput);
        
        // ====== Phone Number Input Text Field ======
        HBox phoneInputBox = new HBox();
        phoneInputBox.setPadding(new Insets(5, 5, 5, 5));
        phoneInputBox.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 15px;");
        phoneInputBox.setAlignment(Pos.CENTER);

        phoneInput = new TextField();
        phoneInput.prefHeight(36.0);
        phoneInput.prefWidth(290.0);
        phoneInput.setMinHeight(34.0);
        phoneInput.setMaxHeight(34.0);
        phoneInput.setMinWidth(290.0);
        phoneInput.setMaxWidth(290.0);
        phoneInput.promptTextProperty().set("Masukkan nomor telepon");
        phoneInput.setFont(new Font(null, 16.0));
        phoneInput.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        phoneInputBox.getChildren().add(phoneInput);

        // ====== Login Button ======
        Button loginButton = new Button("Login");
        loginButton.setAlignment(Pos.CENTER);
        loginButton.setContentDisplay(ContentDisplay.CENTER);
        loginButton.setMnemonicParsing(false);
        loginButton.prefHeight(44.0);
        loginButton.prefWidth(118.0);
        loginButton.setMinHeight(44.0);
        loginButton.setMaxHeight(44.0);
        loginButton.setMinWidth(118.0);
        loginButton.setMaxWidth(118.0);
        loginButton.setStyle(
            "-fx-background-color: #2A2A2A; " + 
            "-fx-font-size: 16px; " +
            "-fx-font-family: Poppins SemiBold; " +
            "-fx-background-radius: 15em;"
        );
        loginButton.setTextAlignment(TextAlignment.CENTER);
        loginButton.setTextFill(javafx.scene.paint.Color.WHITE);
        loginButton.setOnAction(e -> {
            handleLogin();
        });
        
        // Add all elements to the login pane
        loginPane.getChildren().addAll(
            title, spacerInput, titleNama, nameInputBox, titleTelp, phoneInputBox, spacerButton, loginButton
        );

        // Add the login pane to the login section
        loginSection.getChildren().add(loginPane);

        // ====== Image Section ======
        ImageView loginBanner = new ImageView(banner);
        loginBanner.setFitHeight(626.2);

        rootBox.getChildren().addAll(loginBanner, loginSection);

        return new Scene(rootBox, rootBox.getPrefWidth(), rootBox.getPrefHeight()); // Return the scene
    }


    private void handleLogin(){
        String name = nameInput.getText();
        String phone = phoneInput.getText();

        if (name.isEmpty() || phone.isEmpty()) {
            Alert alert = mainApp.createAlert(
                Alert.AlertType.ERROR, 
                "Error", 
                "Login Gagal", 
                "Nama dan nomor telepon tidak boleh kosong!"
            );
            alert.showAndWait();
        } else {
            User user = DepeFood.getUser(name, phone);
            if (user == null) {
                Alert alert = mainApp.createAlert(
                    Alert.AlertType.ERROR, 
                    "Error", 
                    "Login Gagal", 
                    "Akun tidak terdaftar di sistem!"
                );
                alert.showAndWait();
            } else {
                // Set the logged in user to the main app
                mainApp.setUser(user);
                DepeFood.setUserLoggedIn(user);
                
                if (user.getRole().equals("Admin")) {
                    if (mainApp.getScene("AdminPage") != null) {
                        // Replace the previous stored scene with the new scene
                        mainApp.changeScene("AdminPage", new AdminMenu(this.stage, this.mainApp).getScene());
                    } else {
                        // If the scene is not created yet, add the scene to the allScenes map
                        mainApp.addScene("AdminPage", new AdminMenu(this.stage, this.mainApp).getScene());
                    }

                    mainApp.setScene(mainApp.getScene("AdminPage"));
                    
                    clearFields();
                } else {
                    if (mainApp.getScene("CustomerPage") != null) {
                        // Replace the previous stored scene with the new scene
                        mainApp.changeScene("CustomerPage", new CustomerMenu(this.stage, this.mainApp).getScene());
                    } else {
                        // If the scene is not created yet, add the scene to the allScenes map
                        mainApp.addScene("CustomerPage", new CustomerMenu(this.stage, this.mainApp).getScene());
                    }
                    
                    mainApp.setScene(mainApp.getScene("CustomerPage"));
                    
                    clearFields();
                }
            }
        }
    }

    public Scene getScene() throws IOException{
        return this.createLoginForm();
    }

    // Helper Function: Create a custom blank space pane
    public Pane createSpacer(double height, double width) {
        Pane spacer = new Pane();
        spacer.setPrefHeight(height);
        spacer.setPrefWidth(width);
        return spacer;
    }

    // Helper Function: Clear the input fields
    public void clearFields() {
        nameInput.clear();
        phoneInput.clear();
    }
}
