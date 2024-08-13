package com.project.mmdo.controllers;

import com.project.mmdo.Calculation;
import com.project.mmdo.model.*;
import com.project.mmdo.model.ObjectiveFunction.OptimizationType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.project.mmdo.model.ObjectiveFunction.OptimizationType.MAXIMIZE;
import static com.project.mmdo.model.ObjectiveFunction.OptimizationType.MINIMIZE;
import static com.project.mmdo.model.Rat.ZERO;

public class FirstWindowController {

    @FXML
    private Button generateButton;
    @FXML
    private Button calculateButton;

    @FXML
    private HBox hBoxes;
    @FXML
    private HBox hBoxObjFunc;

    @FXML
    private VBox VBoxWithInequalities;

    @FXML
    private ChoiceBox<String> quantityOfVariablesChBox;

    @FXML
    private ChoiceBox<String> quantityOfInequalitiesChBox;
    private Calculation calculation = new Calculation();


    @FXML
    void initialize() {

        fillChoiceBox(quantityOfVariablesChBox, 2, 20);
        fillChoiceBox(quantityOfInequalitiesChBox, 2, 20);

        //event for generateButton
        generateButton.setOnAction(event -> {
            updateForm();
        });

        //event for calculateButton
        calculateButton.setOnAction(event -> {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/mmdo/TablesView.fxml"));

            try {
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                TablesWindowController controller = (TablesWindowController)loader.getController();
                loader.setController(controller);
                CalculateAll(loader);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }
    private void CalculateAll(FXMLLoader loader) {
       calculation.calculate(generateInequalities(), generateObjFunc(), loader);
    }

    private ObjectiveFunction generateObjFunc() {
        int chosenNumVar = getSelectedValue(quantityOfVariablesChBox);
        ObjectValue[] coefficients = new ObjectValue[chosenNumVar];

        HBox hbox = (HBox) hBoxObjFunc.getChildren().get(0);
        int i = 0;
        OptimizationType optimizationType = null;

        for (Node node : hbox.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                //get from textField
                coefficients[i] = new ObjectValue(ZERO, Rat.doubleToRat(Double.parseDouble(textField.getText())));
                i++;
            }

            if (node instanceof ChoiceBox) {
                ChoiceBox choiceBox = (ChoiceBox) node;

                if (choiceBox.getValue().equals("min")) {
                    optimizationType = MINIMIZE;
                } else if (choiceBox.getValue().equals("max")) {
                    optimizationType = MAXIMIZE;

                }
            }
        }

        return new ObjectiveFunction(coefficients, coefficients.length, optimizationType, false);
    }

    private SystemOfInequalities generateInequalities() {
        int chosenNumInequalities = getSelectedValue(quantityOfInequalitiesChBox);
        int chosenNumVar = getSelectedValue(quantityOfVariablesChBox);
        VBox vbox = (VBox) VBoxWithInequalities.getChildren().get(0);

        //empty list +ekzemplyar klassy
        List<Inequality> inequalities = new ArrayList<>();
        SystemOfInequalities system = new SystemOfInequalities(inequalities);

        //every HBox in VBox
        for (int i = 0; i < chosenNumInequalities; i++) {

            HBox hbox = (HBox) vbox.getChildren().get(i);
            int j = 0;

            double[] coefficients = new double[chosenNumVar];
            ;
            double value = 0;
            Inequality.InequalitySymbol symbol = null;

            //evey child in i HBox
            for (Node node : hbox.getChildren()) {
                if (node instanceof TextField) {
                    TextField textField = (TextField) node;
                    // get from textField to array
                    if (j < chosenNumVar) {
                        coefficients[j] = Double.parseDouble(textField.getText());
                    } else {
                        value = Double.parseDouble(textField.getText());
                    }
                    j++;
                } else if (node instanceof ChoiceBox) {
                    ChoiceBox choiceBox = (ChoiceBox) node;

                    if (choiceBox.getValue().equals("≤")) {
                        symbol = Inequality.InequalitySymbol.LESS_EQUAL_THAN;
                    } else if (choiceBox.getValue().equals("≥")) {
                        symbol = Inequality.InequalitySymbol.GREATER_EQUAL_THAN;

                    } else {
                        symbol = Inequality.InequalitySymbol.EQUAL;
                    }
                    j++;
                }
            }
            Inequality inequality = new Inequality(coefficients, symbol, value);
            system.addInequality(inequality);
        }
        return system;
    }

    private void updateForm() {
        int chosenNumInequalities = getSelectedValue(quantityOfInequalitiesChBox);
        int chosenNumVar = getSelectedValue(quantityOfVariablesChBox);

        hBoxObjFunc.getChildren().clear();
        hBoxes.getChildren().clear();
        VBoxWithInequalities.getChildren().clear();

        //objective func in HBox
        addHBoxObjFunc(chosenNumVar);
        //HBox for inequalities
        createHBoxesInVBox(chosenNumVar, chosenNumInequalities);

    }

    //create HBoxes for inequalities (how much user in choiceBox)
    private void createHBoxesInVBox(int chosenNumVar, int chosenNumInequalities) {
        VBox vBox = new VBox();
        for (int i = 0; i < chosenNumInequalities; i++) {
            HBox hBox = new HBox();
            hBox.setSpacing(3);

            addElementsToHBox(chosenNumVar, hBox);

            //ChoiceBox ><
            ChoiceBox<String> markInInequalities = new ChoiceBox<>();
            markInInequalities.getItems().addAll("≤", "≥");
            hBox.getChildren().add(markInInequalities);

            //TextField for res
            TextField textFieldResult = new TextField();
            textFieldResult.setMaxWidth(45);
            hBox.getChildren().add(textFieldResult);

            hBox.setAlignment(Pos.CENTER);

            //add hBox to vBox
            vBox.getChildren().add(hBox);
        }
        //add vBox to final Vbox
        VBoxWithInequalities.getChildren().add(vBox);
    }

    //fill HBox
    private void addElementsToHBox(int chosenNumber, HBox hbox) {
        for (int i = 0; i < chosenNumber; i++) {
            TextField textFieldForVar = new TextField();
            textFieldForVar.setMaxWidth(45);

            Label newXVariable = new Label("x");
            Label newNumberOfX = new Label(String.valueOf(i + 1));
            hbox.getChildren().addAll(textFieldForVar, newXVariable, newNumberOfX);

            if (i < chosenNumber - 1) {
                Label plusLabel = new Label(" + ");
                hbox.getChildren().add(plusLabel);
            }
        }
    }

    //obj func
    private void addHBoxObjFunc(int chosenNumber) {
        HBox hBox = new HBox();
        hBox.setSpacing(3);
        addElementsToHBox(chosenNumber, hBox);

        hBox.getChildren().addAll(new Label("->"));

        //ChoiceBox min/max
        ChoiceBox<String> markInInequalities = new ChoiceBox<>();
        markInInequalities.getItems().addAll("min", "max");
        hBox.getChildren().add(markInInequalities);

        hBox.setAlignment(Pos.CENTER);
        //add hBox to final hBoxObjFunc
        hBoxObjFunc.getChildren().add(hBox);
    }

    //fill ChoiceBox nums from 2 to 20
    private void fillChoiceBox(ChoiceBox<String> choiceBox, int start, int end) {
        ObservableList<String> numbers = FXCollections.observableArrayList();
        for (int i = start; i <= end; i++) {
            numbers.add(String.valueOf(i));
        }
        choiceBox.setItems(numbers);
        choiceBox.setValue(String.valueOf(start));
    }

    //return num which chose user in choiceBox
    private int getSelectedValue(ChoiceBox<String> choiceBox) {
        return Integer.parseInt(choiceBox.getValue());
    }

}

