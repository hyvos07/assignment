package assignments.assignment4.page;

import java.util.List;

import assignments.assignment3.DepeFood;
import assignments.assignment3.items.Restaurant;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public abstract class MemberMenu {
    private Scene scene;
    protected ComboBox<String> restaurantComboBox = new ComboBox<>();
    protected List<Restaurant> restoList = DepeFood.getRestoList();

    // Image and ImageView Assets
    protected Image logoIcon = new Image(getClass().getResourceAsStream("/Logo.png"));
    protected ImageView logoView = new ImageView(logoIcon);
    
    protected Image logoutIcon = new Image(getClass().getResourceAsStream("/Logout.png"));
    protected ImageView logoutView = new ImageView(logoutIcon);

    protected Image backIcon = new Image(getClass().getResourceAsStream("/Back.png"));

    // Abstract Method to be implemented
    abstract protected Scene createBaseMenu();
    abstract protected HBox createNavbar(String title);

    public Scene getScene(){
        return this.scene;
    }

    // Helper Function to refresh the App
    protected void refresh(){
        this.restoList = DepeFood.getRestoList(); // Refresh the restaurant list
        this.restaurantComboBox.getItems().clear(); // Clear the restaurantComboBox
        
        // Add the restaurant name to the restaurantComboBox
        this.restoList.forEach(restaurant -> this.restaurantComboBox.getItems().add(restaurant.getNama()));
    }

    // Helper Function to create blank spacer
    protected Pane createSpacer(double height) {
        Pane spacer = new Pane();
        spacer.setMinHeight(height);
        return spacer;
    }

    // Helper Function to refactor repeated code
    protected void setPrefSize(Region item, double width, double height) {
        item.setPrefWidth(width);
        item.setPrefHeight(height);
        item.setMinWidth(width);
        item.setMinHeight(height);
        item.setMaxWidth(width);
        item.setMaxHeight(height);
    }
}
