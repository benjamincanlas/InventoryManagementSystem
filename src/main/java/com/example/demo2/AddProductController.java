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

/**
 * @author Benjamin Lopez Canlas III
 */
/** Controller for adding products to the main screen and contains functions for moving parts to and from the associated table.*/
public class AddProductController implements Initializable {
    /**
     * This list stored the associated parts. */
    private ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();
    public TextField addProdIdTxt;
    public TextField addProdNameTxt;
    public TextField addProdInvTxt;
    public TextField addProdPriceTxt;
    public TextField addProdMaxTxt;
    public TextField addProdMinTxt;


    /** Table view for the parts where the data are based and sorted according to their defined column such as:
     ID, name, stock, and price.  */
    public TableView<Part> addProdTable;
    public TableColumn<Part, Integer> addProdPartIdCol;
    public TableColumn<Part, String> addProdPartNameCol;
    public TableColumn<Part, Integer> addProdInvLvlCol;
    public TableColumn<Part, Double> addProdPPUCol;
    public Button addProdAddBtn;

    /** Table view for the associated parts where the data are based and sorted according to their defined column such as:
     ID, name, stock, and price.  */
    public TableView<Part> addProdAssocTable;
    public TableColumn<Part, Integer> addProdAssocPartIdCol;
    public TableColumn<Part, String> addProdAssocPartNameCol;
    public TableColumn<Part, Integer> addProdAssocInvLvlCol;
    public TableColumn<Part, Double> addProdAssocPPUCol;
    public Button addProdRemoveAssocBtn;
    public Button addProdSaveBtn;
    public Button addProdCancelBtn;
    public Label addProdIdLbl;
    public Label addProdNameLbl;
    public Label addProdInvLbl;
    public Label addProdPriceLbl;
    public Label addProdMaxLbl;
    public Label addProdTitle;
    public Label addProdMinLbl;
    public TextField addProdSearchTxt;
    /** Both the "stage" and "parent" scenes transfer us from our current FXML scene to a new load scene that we set. */
    Stage stage;
    Parent scene;

    //Methods
    /** Searches for any entered part or highlights its ID number. If a part is not found, an error dialog will trigger the validation filter.
     * Pressing enter with empty fields will reset the table results. */
    public void onSearchPart(ActionEvent event) {
        String searchText = addProdSearchTxt.getText();
        System.out.println(searchText);
        try {
            int searchId = Integer.parseInt(searchText);
            Part searchPart = Inventory.lookupPart(searchId);
            if (searchPart != null)
                addProdTable.getSelectionModel().select(searchPart);
        } catch (NumberFormatException e) {
            ObservableList<Part> partsFound = Inventory.lookupPart(searchText);
            if(partsFound.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Not found.");
                alert.showAndWait();
            }
            else {
                addProdTable.setItems(partsFound);
            }
        }
    }
    /** Any selected part will become associated with the product being created. A validation check is in place to ensure a part selection is made. */
    public void onAddPartToProduct(ActionEvent actionEvent) {
        Part p = addProdTable.getSelectionModel().getSelectedItem();
        if (p == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please make a part selection to add to associated table.");
            alert.showAndWait();
        } else {
            tempAssociatedParts.add(p);
            addProdAssocTable.setItems(tempAssociatedParts);
        }
    }
    /** Selecting this button removes any added part from being associated with product being created. */
    public void onRemoveAssocPart(ActionEvent actionEvent) {
        Part assocP = addProdAssocTable.getSelectionModel().getSelectedItem();
        if (assocP == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a part to remove from the associated table.");
            alert.showAndWait();
        } else {
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Press ok to continue removal.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    tempAssociatedParts.remove(assocP);
                    addProdAssocTable.setItems(tempAssociatedParts);
                }
            }
            }
    }
    /**
     * This button saves the data inputted into the text fields.
     * The ID field cannot be edited but will display a custom method call from our Inventory class.
     * For our input and validation checks, we utilize a try/catch method to define a block of code to be executed should an error occurs in the try block. The dialog alerts displayed will be specific to the filters required to proceed.
     * Once all appropriate text fields and parameters have been met, the scene will save and transfer the input data to the products table and returns to "Main Menu" screen.

    /** ======RUNTIME ERROR ======
     * Initially, this button would save a created product with an "associated part" but would not display in the "Modify Product" scene.
     * The issue resulted from the "associated parts" list not storing the added associated pat. An enhanced for loop was created to address this error this way tempAssocParts will be utilized in storing this associatedPart that was added using the add button.
     * The enhanced for loop ensures the associated parts are accounted for as it loops through tempAssocPArts.
     * */
    public void onSave(ActionEvent actionEvent) throws IOException {
        int id = Inventory.getProductAutoId() ;
        String name = addProdNameTxt.getText();
        if(name.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Name value is blank and must be completed.");
            alert.showAndWait();
            return;
        }
        String error = "";
        try {
            error = "Inventory";
            int stock = Integer.parseInt(addProdInvTxt.getText());
            error = "Price";
            double price = Double.parseDouble(addProdPriceTxt.getText());
            error = "Max";
            int max = Integer.parseInt(addProdMaxTxt.getText());
            error = "Min";
            int min = Integer.parseInt(addProdMinTxt.getText());
            if((min > stock && min != stock) || (stock > max && stock != max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Inventory must be within minimum and maximum values.");
                alert.showAndWait();
                return;
            }
            Product pr = new Product(id, name, price, stock, min, max);
                Inventory.addProduct(pr);
                for (Part c: tempAssociatedParts) {
                    pr.addAssociatedPart(c);
                }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(error + " value must be a number.");
            alert.showAndWait();
            return;
        }
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
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
    /** Initializes and sets data for top parts table and bottom associated parts table. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProdTable.setItems(Inventory.getAllParts());
        addProdPartIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        addProdPartNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        addProdInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        addProdPPUCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        /**assoc table*/
        addProdAssocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        addProdAssocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        addProdAssocInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        addProdAssocPPUCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }
}
