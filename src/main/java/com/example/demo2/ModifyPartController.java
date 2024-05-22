package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.demo2.MainMenuController.getModPart;
import static model.Inventory.getAllParts;

/**
 * @author Benjamin Lopez Canlas III
 */

/** This controller is for modifying any part selected from Parts Table View from the MainMenu scene. */
public class ModifyPartController implements Initializable {
    public Label modPartIdLbl;
    public Label modPartNameLbl;
    public Label modPartInvLbl;
    public Label modPartPriceLbl;
    public Label modPartMaxLbl;
    public Label modPartMinLbl;

    public TextField modPartIdTxt;
    public TextField modPartNameTxt;
    public TextField modPartInvTxt;
    public TextField modPartPriceTxt;
    public TextField modPartMaxTxt;
    public TextField modPartMinTxt;
    public Button modPartSaveBtn;
    public Button modPartCancelBtn;
    /** Both the "stage" and "parent" scenes transfer us from our current FXML scene to a new load scene that we set. */
    Stage stage;
    Parent scene;
    /** This variable is called when we create our ID. */
    private int partId;
    /** This variable is called when we define our index count for ID. */
    private final int index = getModPart();
    /** This display label changes based on the radio selection of either In-house: "Machine ID" or Outsourced: "Company Name". */
    public Label modPartSharedLabel;
    public TextField modPartSharedTxtField;
    public ToggleGroup modPartToggle;
    public RadioButton inHouseRadioBtn;
    public RadioButton outsourcedRadioBtn;


    /**
     * This button saves the data inputted into the text fields.
     * The ID field cannot be edited but will display a custom created ID .
     * For our input and validation checks, we utilize a try/catch method to define a block of code to be executed should an error occurs in the try block. The dialog alerts displayed will be specific to the filters required to proceed.
     * Once all appropriate text fields and parameters have been met, the scene will save and transfer any modifications made to the parts table and returns to "Main Menu" screen. */
    public void onSave(ActionEvent event) throws IOException {
        int id = partId;
        String name = modPartNameTxt.getText();
        if (name.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Name value is blank and must be completed.");
            alert.showAndWait();
            return;
        }
        String error = "";
        try {
            error = "Inventory";
            int stock = (Integer.parseInt(modPartInvTxt.getText()));

            error = "Price";
            double price = Double.parseDouble(modPartPriceTxt.getText());
            error = "Max";
            int max = Integer.parseInt(modPartMaxTxt.getText());
            error = "Min";
            int min = Integer.parseInt(modPartMinTxt.getText());

            if ((min > stock && min != stock) || (stock > max && stock != max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Inventory must be within minimum and maximum values.");
                alert.showAndWait();
                return;
            }
            if (outsourcedRadioBtn.isSelected()) {
                String companyName = modPartSharedTxtField.getText();
                if (companyName.isBlank()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Company name value is blank and must be completed.");
                    alert.showAndWait();
                    return;
                }
                Outsourced os2 = new Outsourced(id, name, price, stock, min, max, companyName);
                System.out.println("Index is "+ index);
                Inventory.updatePart(index, os2);
            } else {
                error = "Machine ID";
                int machineId = Integer.parseInt(modPartSharedTxtField.getText());
                InHouse ih2 = new InHouse(id, name, price, stock, min, max, machineId);
                Inventory.updatePart(index, ih2);
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

    /** This function sets the "sharedlabel" to display Machine ID when In-House radio button is selected. */
    public void onInHouseRadio(ActionEvent actionEvent) {
            modPartSharedLabel.setText("Machine ID");
    }
    /** This function sets the "sharedlabel" to display Company Name when Outsourced radio button is selected. */
    public void onOutsourcedRadio(ActionEvent actionEvent) {
            modPartSharedLabel.setText("Company Name");
    }
    /** This cancel button will redirect the current scene back to  the "MainMenu" scene.  */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /** =====RUNTIME ERROR ========*/
    /** A logical or runtime error encountered was the min and max values would populate each other's values where min would have a higher value than max.
     * As a result, the filter criteria would not be met as this triggers an error dialog and prevents the user from proceeding.
     * This was corrected when min value code line was placed before the max. */

    /** This initializes and sets data passed/retrieved from main screen parts table to populate the "modify controller" text fields.
      */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Part selectedPart = getAllParts().get(index);
        partId = getAllParts().get(index).getId();
        modPartIdTxt.setText("Auto-Generate: " + Integer.toString(partId));
        modPartNameTxt.setText(selectedPart.getName());
        modPartInvTxt.setText(Integer.toString(selectedPart.getStock()));
        modPartPriceTxt.setText(Double.toString(selectedPart.getPrice()));
        modPartMinTxt.setText(Integer.toString(selectedPart.getMin()));
        modPartMaxTxt.setText(Integer.toString(selectedPart.getMax()));
        /** This determines the last text fields of Machine ID or Company Name depending on which radio button is selected. */
        if (selectedPart instanceof InHouse) {
            modPartSharedLabel.setText("Machine ID");
            modPartSharedTxtField.setText(Integer.toString(((InHouse) getAllParts().get(index)).getMachineId()));
            inHouseRadioBtn.setSelected(true);
        }
        else {
            modPartSharedLabel.setText("Company Name");
            modPartSharedTxtField.setText(((Outsourced) getAllParts().get(index)).getCompanyName());
            outsourcedRadioBtn.setSelected(true);
        }
        }
    }
