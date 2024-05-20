package assignments.assignment4.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.HBox;

// Class ini opsional
public class MenuItem extends HBox{
    private final StringProperty itemName;
    private final StringProperty price;

    public MenuItem(String itemName, String price) {
        this.itemName = new SimpleStringProperty(itemName);
        this.price = new SimpleStringProperty(price);
    }

    public StringProperty itemNameProperty() {
        return itemName;
    }

    public StringProperty priceProperty() {
        return price;
    }

    public String getItemName() {
        return itemName.get();
    }

    public String getPrice() {
        return price.get();
    }
}
