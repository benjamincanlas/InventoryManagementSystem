package com.example.demo2;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Benjamin Lopez Canlas III
 */

/** FUTURE ENHANCEMENT
 * A future enhancement I would like to improve in the project is to reduce coding redundancies.
 * I would do this by defining a variable into a function and then later calling that variable as needed to save line space.
 */


/** The Main Menu screen controller is the main hub and centerpiece of the inventory project.
 * This main scene contains the buttons, tables, and functions that transfers data to our four other controllers.
 * */
public class MainMenuController implements Initializable {
    public TextField partSearchTxt2;
    public TextField productSearchTxt2;
    /** Both the "stage" and "parent" scenes transfer us from our current FXML scene to a new load scene that we set.  */
    Stage stage;
    Parent scene;
    /** This variable is called when we define our search part function. */
    private static int modPart;
    /** This variable is called when we define our search product function. */
    private static int modProduct;
    /**
     * @return modify Part selection when searching
     * This variable is later used for index id creation and passed in our "modifyPart" controller scene.
     */
    public static int getModPart() {
        return modPart;
    }
    /**
     * @return modify product selection during search
     * This variable is later used for index id creation and passed in our "modifyProduct" controller scene.
     */
    public static int getModProduct() {
        return modProduct;
    }
    /** Table view for the parts where the data are based and sorted according to their defined column such as:
     ID, name, stock, and price.  */
    //Parts
    public TableView<Part> partsTable;
    public TableColumn<Part, Integer> partIdCol;
    public TableColumn<Part, String> partNameCol;
    public TableColumn<Part, Integer> partInvCol;
    public TableColumn<Part, Double> partPPUCol;
    public Button partAddBtn;
    public Button partModBtn;
    public Button partDelBtn;
    /** Table view for the products where the data are based and sorted according to their defined column such as:
     ID, name, stock, and price.  */
    //Products
    public TableView<Product> productsTable;
    public TableColumn<Product, Integer> productIdCol;
    public TableColumn<Product, String> productNameCol;
    public TableColumn<Product, Integer> productInvLvlCol;
    public TableColumn<Product, Double> productPPUCol;
    public Button productAddBtn;
    public Button productModBtn;
    public Button productDelBtn;
    public Button exitBtn;

    /** Searches for any entered part or highlights its ID number.
     * Pressing enter with empty fields will reset the table results. */
    public void onSearchPart(ActionEvent event) {
        String searchText = partSearchTxt2.getText();
        System.out.println(searchText);
        try {
            int searchId = Integer.parseInt(searchText);
            Part searchPart = Inventory.lookupPart(searchId);
            if (searchPart == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Not found.");
                alert.showAndWait();
            }
                else
                partsTable.getSelectionModel().select(searchPart);
        } catch (NumberFormatException e) {
            ObservableList<Part> partsFound = Inventory.lookupPart(searchText);
            if(partsFound.isEmpty() ) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Not found.");
                alert.showAndWait();
            }
            else {
                partsTable.setItems(partsFound);
            }
        }
    }
    /** This event transfers current scene to "AddPart" fxml scene. */
    public void onAddPart(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** This event transfers to "ModifyPart" fxml scene. A validation check is in place to ensure a part selection is made.*/
    public void onModifyPart(ActionEvent actionEvent) throws IOException {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        modPart = Inventory.getAllParts().indexOf(selectedPart);
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please make a part selection to modify.");
            alert.showAndWait();
        } else {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    /** Removes the selected part from the parts table. Beforehand, a validation check is made to ensure confirmation of part selection is made. */
    public void onDeletePart(ActionEvent actionEvent) {
        if (partsTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a part for deletion.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure? Press ok to continue deleting part.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
                Inventory.deletePart(selectedPart);
            }
        }
    }
    /** Searches for any entered product or highlights its ID number if entered.
     * Pressing enter with empty fields will reset the table results. */
    public void onSearchProduct(ActionEvent event) {
        String searchText2 = productSearchTxt2.getText();
        System.out.println(searchText2);
        try {
            int searchId = Integer.parseInt(searchText2);
            //on searching products, will call and use lookupProduct from Inventory which has the filter and loop
            // parameters for products
            Product searchProduct = Inventory.lookupProduct(searchId);
            //here we will search id with selectionModel to do a single selection based on ID
            if (searchProduct == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Not found.");
                alert.showAndWait();
            }
            else
                productsTable.getSelectionModel().select(searchProduct);
        } catch (NumberFormatException e) {
            ObservableList<Product> productsFound = Inventory.lookupProduct(searchText2);
            if(productsFound.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Not found.");
                alert.showAndWait();
            }
            else {
                productsTable.setItems(productsFound);
            }
        }
    }
    /** Event leads to "AddProduct" fxml scene. */
    public void onAddProduct(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Event leads to "ModifyProduct" fxml scene after verification of a selected product. */
    public void onModifyProduct(ActionEvent actionEvent) throws IOException {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        modProduct = Inventory.getAllProducts().indexOf(selectedProduct);
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please make a product selection to modify.");
            alert.showAndWait();
        } else {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    /** This removes a selected product from the product table. An error alert dialog was added to ensure the product
     * did not have an associated product attached. This condition prevents a product deletion until the validation check has been satisfied.  */
    public void onDeleteProduct(ActionEvent actionEvent) {

        Product selectAssociatedPart = (Product) productsTable.getSelectionModel().getSelectedItem();

        if (productsTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a product for deletion.");
            alert.showAndWait();
        }
        if (selectAssociatedPart.getAllAssociatedParts().size() >0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Selected product must not have an associated part. Please remove associated part before product deletion. ");
            alert.showAndWait();

        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure? Press ok to continue deleting product.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
                Inventory.deleteProduct(selectedProduct);
            }
        }
    }

    /** This event will exit and close our project. A confirmation check is made before proceeding. */
    public void onExit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure? Press ok to continue exiting the application.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
    /** This will initialize and set data placement for the parts and products table.
     The sample and test data from the Application menu will be retrieved and preloaded into this scene's fields. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //parts section
        partsTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partPPUCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        //products section
        productsTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        productPPUCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }
}
