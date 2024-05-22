package com.example.demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

/**
 * @author Benjamin Lopez Canlas III
 */
/** This class launches and initializes the application.
 * The launcher redirects to the Main Menu scene. */
public class ApplicationLauncher extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationLauncher.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load() );
        stage.setTitle("Mountain Biking Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /** These are the test data that will populate the "Main Menu" part and product tables when the application is launched. */
    public static void main(String[] args) {
        /** The test data here is created and stored for the parts table. */
        Part p1 = new InHouse(Inventory.getAutoId(), "brakes", 89.00, 3, 1, 8, 22);
        Part p2 = new Outsourced(Inventory.getAutoId(), "rotors", 79.99, 3, 1, 8, "Shimano");
        Part p3 = new InHouse(Inventory.getAutoId(), "fork", 299.00, 3, 1, 8,33);
        Part p4 = new InHouse(Inventory.getAutoId(), "derailleur", 49.99, 5, 1, 8,77);
        Part p5 = new Outsourced(Inventory.getAutoId(), "chain", 10.00, 2, 1, 8, "KMC");
        Inventory.addPart(p1);
        Inventory.addPart(p2);
        Inventory.addPart(p3);
        Inventory.addPart(p4);
        Inventory.addPart(p5);

        /** The test data here is created and stored in the products table. */
        Product pr1 = new Product(Inventory.getProductAutoId(), "Fox 34 fork", 799.99, 2, 1, 7);
        Product pr2 = new Product(Inventory.getProductAutoId(), "Shimano M6100 Brake Set", 139.99, 2, 1, 7);
        Product pr3 = new Product(Inventory.getProductAutoId(), "RockShox Pike Ultimate fork", 899.99, 3, 1, 6);
        Inventory.addProduct(pr1);
        Inventory.addProduct(pr2);
        Inventory.addProduct(pr3);

        launch();
    }
}