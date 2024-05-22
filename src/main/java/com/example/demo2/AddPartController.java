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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Benjamin Lopez Canlas III
 */

/** This controller adds parts to the parts table. */
public class AddPartController implements Initializable {
    public TextField addPartSharedTxtField;
    public Label addPartIdLbl;
    public Label addPartNameLbl;
    public Label addPartInvLbl;
    public Label addPartPriceLbl;
    public Label addPartMaxLbl;
    public Label addPartMinLbl;
    public Label addPartTitle;
    public TextField addPartIdTxt;
    public TextField addPartNameTxt;
    public TextField addPartInvTxt;
    public TextField addPartPriceTxt;
    public TextField addPartMaxTxt;
    public TextField addPartMinTxt;
    public Button addPartSaveBtn;
    public Button addPartCancelBtn;
    /** Both the "stage" and "parent" scenes transfer us from our current FXML scene to a new load scene that we set. */
    Stage stage;
    Parent scene;
    /** This display label changes based on the radio selection of either In-house: "Machine ID" or Outsourced: "Company Name". */
    public Label sharedLabel;
    public ToggleGroup addPartToggle;
    public RadioButton inHouseRadioBtn;
    public RadioButton outsourcedRadioBtn;
    /** This function sets the "sharedlabel" to display Machine ID when In-House radio button is selected. */
    public void onInHouseRadio(ActionEvent actionEvent) {
        if(inHouseRadioBtn.isSelected()) {
            sharedLabel.setText("Machine ID");
        }
    }
    /** This function sets the "sharedlabel" to display Company Name when Outsourced radio button is selected. */
    public void onOutsourcedRadio(ActionEvent actionEvent) {
        if(outsourcedRadioBtn.isSelected()) {
            sharedLabel.setText("Company Name");
        }
    }
    /**
     * This button saves the data inputted into the text fields.
     * The ID field cannot be edited but will display a custom method call from our Inventory class.
     * For our input and validation checks, we utilize a try/catch method to define a block of code to be executed should an error occurs in the try block. The dialog alerts displayed will be specific to the filters required to proceed.
     * Once all appropriate text fields and parameters have been met, the scene will save and transfer the input data to the parts table and returns to "Main Menu" screen. */

    public void onSave(ActionEvent actionEvent) throws IOException {
        //must collect data from text fields then query which radio btn is selected and based on that create either an outsource or inhouse part , then addPart
            int id = Inventory.getAutoId();
            String name = addPartNameTxt.getText();
                if(name.isBlank()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Name value is blank and must be completed.");
                    alert.showAndWait();
                    return;
                }
            String error = "";
            try {
                error = "Inventory";
                int stock = Integer.parseInt(addPartInvTxt.getText());
                error = "Price";
                double price = Double.parseDouble(addPartPriceTxt.getText());
                error = "Max";
                int max = Integer.parseInt(addPartMaxTxt.getText());
                error = "Min";
                int min = Integer.parseInt(addPartMinTxt.getText());

                if((min > stock && min != stock) || (stock > max && stock != max)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Inventory must be within minimum and maximum values.");
                    alert.showAndWait();
//                    System.out.println("Inventory must be within minimum and maximum values");
                    return;
                }
                if (outsourcedRadioBtn.isSelected()) {

                    String companyName = addPartSharedTxtField.getText();
                    if (companyName.isBlank()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Company name value is blank and must be completed.");
                        alert.showAndWait();
                        return;
                    }
                    Outsourced os = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(os);
                } else {
                    error = "Machine ID";
                    int machineId = Integer.parseInt(addPartSharedTxtField.getText());
                    InHouse ih = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.addPart(ih);
                    }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(error + " value must be a number.");
                alert.showAndWait();
//                System.out.println(error + " value must be a number.");
                return;
            }
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
