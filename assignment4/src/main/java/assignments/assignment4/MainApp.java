package assignments.assignment4;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import assignments.assignment3.DepeFood;
import assignments.assignment3.items.User;
import assignments.assignment4.components.form.LoginForm;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.page.CustomerMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Stage window;
    private Map<String, Scene> allScenes = new HashMap<>();
    private Scene currentScene;
    private static User user;

    private Image alertIcon = new Image(getClass().getResourceAsStream("/AlertRed.png"));
    private Image successIcon = new Image(getClass().getResourceAsStream("/SuccessGreen.png"));

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("DepeFood");
        window.setResizable(false);

        Image icon = new Image(getClass().getResourceAsStream("/Burger.png"));
        window.getIcons().add(icon);

        DepeFood.initUser(); // Initialize users

        // Initialize all scenes
        Scene loginScene;
        try {
            loginScene = new LoginForm(window, this).getScene();
            // Populate all scenes map
            allScenes.put("LoginPage", loginScene);

            // Set the initial scene of the application to the login scene
            setScene(loginScene);
        } catch (IOException e) {
            System.out.println("Failed to load FXMLLoader for login scene!");
        }

        window.show();
    }

    public void setUser(User newUser) {
        user = newUser;
    }

    // Method to set a scene
    public void setScene(Scene scene) {
        window.setScene(scene);
        currentScene = scene;
    }

    // Method to get a scene by name
    public Scene getScene(String sceneName) {
        return allScenes.get(sceneName);
    }

    public void addScene(String sceneName, Scene scene){
        if (!allScenes.containsKey(sceneName)){
            allScenes.put(sceneName, scene);
        }
        // Else? Do nothing.
    }

    public void logout() {
        setScene(getScene("LoginPage")); // Switch to the login scene
        setUser(null); // Clear the current user
        DepeFood.setUserLoggedIn(null); // Clear the logged in user
    }

    // Helper Function: Create a custom Alert Dialog
    public Alert createAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(
            type == Alert.AlertType.ERROR ? alertIcon : successIcon
        );
        
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        return alert;
    }

    // Helper Function: Put new scene to the allScenes map
    public void changeScene(String sceneName, Scene scene) {
        this.allScenes.put(sceneName, scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
