package com.example.demo2;

import javafx.collections.FXCollections;
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

import static com.example.demo2.MainMenuController.getModProduct;
import static model.Inventory.getAllProducts;


/**
 * @author Benjamin Lopez Canlas III
 */

/** Controller for modifying products to/from the main screen and contains functions to moving parts to associated table.*/
public class ModifyProductController implements Initializable {
    /**
     * This list contains the associated parts. */
    private ObservableList<Part> tempAssocParts = FXCollections.observableArrayList();
    public TextField modProdIdTxt;
    public TextField modProdNameTxt;
    public TextField modProdInvTxt;
    public TextField modProdPriceTxt;
    public TextField modProdMaxTxt;
    public TextField modProdMinTxt;
    public TextField modProdSearchTxt;
    /** Table view for the parts where the data are based and sorted according to their defined column such as
     * Id, name, stock, and price.  */
    public TableView<Part> modProdTable;
    public TableColumn<Part, Integer> modProdPartIdCol;
    public TableColumn<Part, String> modProdPartNameCol;
    public TableColumn<Part, Integer> modProdInvLvlCol;
    public TableColumn<Part, Double> modProdPPUCol;
    /** Table view for the parts being associated where the data are based and sorted according to their defined column such as
     * Id, name, stock, and price.  */
    public TableView<Part> modProdAssocTable;
    public TableColumn<Part, Integer> modProdAssocPartIdCol;
    public TableColumn<Part, String> modProdAssocPartNameCol;
    public TableColumn<Part, Integer> modProdAssocInvLvlCol;
    public TableColumn<Part, Double> modProdAssocPPUCol;
    public Button modProdAddBtn;
    public Button modProdRemoveAssocBtn;
    public Button modProdSaveBtn;
    public Button modProdCancelBtn;
    /** Both the "stage" and "parent" scenes transfer us from our current FXML scene to a new load scene that we set. */
    Stage stage;
    Parent scene;

    /** This variable is called when we create our ID. */
    private int productId;
    /** This variable is called when we define our index count for the product ID. */
    private final int index = getModProduct();

    /** Searches for any entered part or highlights its ID number. If a part is not found, an error dialog will trigger the validation filter.
     * Pressing enter with empty fields will reset the table results. */
    public void onSearchPart(ActionEvent event) {
        String searchText = modProdSearchTxt.getText();
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
                modProdTable.getSelectionModel().select(searchPart);
        } catch (NumberFormatException e) {
            ObservableList<Part> partsFound = Inventory.lookupPart(searchText);
            if(partsFound.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Not found.");
                alert.showAndWait();
            }
            else {
                modProdTable.setItems(partsFound);
            }
        }
    }
    /** Any selected part will become associated with the product being created. A validation check is in place to ensure a part selection is made. */
    public void onAddPartToProduct(ActionEvent actionEvent) {
        Part pr = modProdTable.getSelectionModel().getSelectedItem();
        if (pr == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please make a part selection to add to associated table.");
            alert.showAndWait();
        } else {
            tempAssocParts.add(pr);
            modProdAssocTable.setItems(tempAssocParts);
        }
    }
    /** This button removes or de-associates a part from the selectedProduct. */
    public void onRemoveAssocPart(ActionEvent actionEvent) {
        Part assocPr = modProdAssocTable.getSelectionModel().getSelectedItem();
        if (assocPr == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a part to remove from the associated table.");
            alert.showAndWait();
        } else {
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Press ok to continue removal.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    tempAssocParts.remove(assocPr);
                    modProdAssocTable.setItems(tempAssocParts);
                }
            }
        }
    }
    /** This button saves the data inputted into the text fields.
     * The ID field cannot be edited but will display a custom method call from our Inventory class. The rest of the attribute fields can be edited by the user.
     * For our input and validation checks, we utilize a try/catch method to define a block of code to be executed should an error occurs in the try block. The dialog alerts displayed will be specific to the filters required to proceed.
     * Once all appropriate text fields and parameters have been met, the scene will save and transfer the input data to the products table and returns to "Main Menu" screen. */

    /** ======RUNTIME ERROR ======
     * A runtime error was encountered during which any modification of a new associated selected Part was not being saved. This was observed because while the retrieved product created from add product, only the updateProduct was parsed during saving.
     * Similar to the issue in the "Add Product" runtime error, this was addressed by creating an enhanced for loop where a tempAssocPart would store the value that will then be added. */
    public void onSave(ActionEvent event) throws IOException {
        int id = productId;
        String name = modProdNameTxt.getText();
        if (name.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Name value is blank and must be completed.");
            alert.showAndWait();
            return;
        }
        String error = "";
        try {
            error = "Inventory";
            int stock = (Integer.parseInt(modProdInvTxt.getText()));
            error = "Price";
            double price = Double.parseDouble(modProdPriceTxt.getText());
            error = "Max";
            int max = Integer.parseInt(modProdMaxTxt.getText());
            error = "Min";
            int min = Integer.parseInt(modProdMinTxt.getText());
            if ((min > stock && min != stock) || (stock > max && stock != max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Inventory must be within minimum and maximum values.");
                alert.showAndWait();
                return;
            }
            Product pr2 = new Product(id, name, price, stock, min, max);
                System.out.println("Index is "+ index);
                Inventory.updateProduct(index, pr2);
            for (Part d: tempAssocParts) {
                pr2.addAssociatedPart(d);
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(error + " value must be a number.");
            alert.showAndWait();
            return;
        }
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** This cancel button will redirect the current scene back to  the "MainMenu" scene. */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** This initializes and sets data passed/retrieved from main screen parts table to populate the "Modify Product" controller text fields.
     * The associated table will store the received associated parts, if any, while setting any added ones in this scene. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modProdTable.setItems(Inventory.getAllParts());
        modProdPartIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        modProdPartNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        modProdInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        modProdPPUCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        /** Associated table*/
        modProdAssocTable.setItems(tempAssocParts);
        modProdAssocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        modProdAssocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        modProdAssocInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        modProdAssocPPUCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

        Product selectedProduct = getAllProducts().get(index);
        productId = getAllProducts().get(index).getId();
        modProdIdTxt.setText("Auto-Generate: " + Integer.toString(productId));
        modProdNameTxt.setText(selectedProduct.getName());
        modProdInvTxt.setText(Integer.toString(selectedProduct.getStock()));
        modProdPriceTxt.setText(Double.toString(selectedProduct.getPrice()));
        modProdMinTxt.setText(Integer.toString(selectedProduct.getMin()));
        modProdMaxTxt.setText(Integer.toString(selectedProduct.getMax()));
        for (Part d: selectedProduct.getAllAssociatedParts()) {
            tempAssocParts.add(d);
        }
    }
}
